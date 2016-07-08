package com.allcom.ldaputil;

/**
 * Created by ljy on 16/7/6.
 * ok
 */
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.allcom.handler.*;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.directory.api.ldap.model.entry.DefaultEntry;
import org.apache.directory.api.ldap.model.exception.LdapEntryAlreadyExistsException;
import org.apache.directory.api.ldap.model.ldif.LdifEntry;
import org.apache.directory.api.ldap.model.ldif.LdifReader;
import org.apache.directory.api.ldap.model.schema.SchemaManager;
import org.apache.directory.server.core.DefaultDirectoryService;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.partition.Partition;
import org.apache.directory.server.core.factory.DirectoryServiceFactory;
import org.apache.directory.server.core.factory.PartitionFactory;
import org.apache.directory.server.ldap.LdapServer;
import org.apache.directory.server.protocol.shared.transport.TcpTransport;
import org.apache.directory.server.protocol.shared.transport.Transport;

//import org.keycloak.common.util.FindFile;
//import org.keycloak.common.util.StreamUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:mposolda@redhat.com">Marek Posolda</a>
 */
public class LDAPEmbeddedServer {

    private static final Logger log = LoggerFactory.getLogger(LDAPEmbeddedServer.class);


    @Value("${ldap.basedn}")
    protected String baseDN;
    @Value("${ldap.host}")
    protected String bindHost;
    @Value("${ldap.port}")
    protected int bindPort;
    @Value("${ldap.ldif}")
    protected String ldifFile;
    @Value("${ldap.saslprincipal}")
    protected String ldapSaslPrincipal;
    @Value("${ldap.dsf}")
    protected String directoryServiceFactory;
    @Value("${ldap.enablessl}")
    protected boolean enableSSL;
    @Value("${ldap.keystorefile}")
    protected String keystoreFile;
    @Value("${ldap.certpass}")
    protected String certPassword;

    protected DirectoryService directoryService;
    protected LdapServer ldapServer;

    @Autowired
    protected FileDirectoryServiceFactory fileDirectoryServiceFactory;

    @Autowired
    protected BindReqHandler bindReqHandler;
    @Autowired
    protected BindRespHandler bindRespHandler;
    @Autowired
    protected SearchReqHandler searchReqHandler;
    @Autowired
    protected SearchResultEntryHandler searchResultEntryHandler;
    @Autowired
    protected SearchResultReferenceHandler searchResultReferenceHandler;
    @Autowired
    protected SearchResultDoneHandler searchResultDoneHandler;


//    public static void main(String[] args) throws Exception {
////        Properties defaultProperties = new Properties();
////        defaultProperties.put(PROPERTY_DSF, DSF_FILE);
//
////        execute(args, defaultProperties);
//        execute();
//    }

//    public static void execute(String[] args, Properties defaultProperties) throws Exception {
//        final LDAPEmbeddedServer ldapEmbeddedServer = new LDAPEmbeddedServer(defaultProperties);
//        ldapEmbeddedServer.init();
//        ldapEmbeddedServer.start();
//
//        Runtime.getRuntime().addShutdownHook(new Thread() {
//
//            @Override
//            public void run() {
//                try {
//                    ldapEmbeddedServer.stop();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//        });
//    }

    public void execute(){
//        final LDAPEmbeddedServer ldapEmbeddedServer = new LDAPEmbeddedServer();
        try {
//            ldapEmbeddedServer.init();
//            ldapEmbeddedServer.start();
            init();
            start();
        } catch (Exception e) {
            log.error("exception:"+e.toString());
        }

//        Runtime.getRuntime().addShutdownHook(new Thread() {
//
//            @Override
//            public void run() {
//                try {
////                    ldapEmbeddedServer.stop();
//                    stop();
//                } catch (Exception e) {
//                    log.error("exception:"+e.toString());
//                }
//            }
//
//        });
    }

    public LDAPEmbeddedServer(){
    }


    public void init() throws Exception {
        log.info("Creating LDAP Directory Service. Config: baseDN=" + baseDN + ", bindHost=" + bindHost + ", bindPort=" + bindPort +
                ", ldapSaslPrincipal=" + ldapSaslPrincipal + ", directoryServiceFactory=" + directoryServiceFactory + ", ldif=" + ldifFile);

        this.directoryService = createDirectoryService();

        log.info("Importing LDIF: " + ldifFile);
        importLdif();

        log.info("Creating LDAP Server");
        this.ldapServer = createLdapServer();
    }


    public void start() throws Exception {
        log.info("Starting LDAP Server");
        ldapServer.start();
        log.info("LDAP Server started");
    }


    protected DirectoryService createDirectoryService() throws Exception {
        // Parse "keycloak" from "dc=keycloak,dc=org"
        String dcName = baseDN.split(",")[0];
        dcName = dcName.substring(dcName.indexOf("=") + 1);

//        DirectoryServiceFactory dsf;
//        if (this.directoryServiceFactory.equals("mem")) {
////            dsf = new InMemoryDirectoryServiceFactory();
////            dsf = new FileDirectoryServiceFactory();
//        } else if (this.directoryServiceFactory.equals("file")) {
////            dsf = new FileDirectoryServiceFactory();
//        } else {
//            throw new IllegalStateException("Unknown value of directoryServiceFactory: " + this.directoryServiceFactory);
//        }

//        DirectoryService service = dsf.getDirectoryService();
        DirectoryService service = fileDirectoryServiceFactory.getDirectoryService();
        service.setAccessControlEnabled(false);
        service.setAllowAnonymousAccess(false);
        service.getChangeLog().setEnabled(false);

//        dsf.init(dcName + "DS");
        fileDirectoryServiceFactory.init(dcName + "DS");

        SchemaManager schemaManager = service.getSchemaManager();

//        PartitionFactory partitionFactory = dsf.getPartitionFactory();
        PartitionFactory partitionFactory = fileDirectoryServiceFactory.getPartitionFactory();
        Partition partition = partitionFactory.createPartition(
                schemaManager,
                service.getDnFactory(),
                dcName,
                this.baseDN,
                1000,
                new File(service.getInstanceLayout().getPartitionsDirectory(), dcName));
        partition.setCacheService(service.getCacheService());
        partition.initialize();

        partition.setSchemaManager(schemaManager);

        // Inject the partition into the DirectoryService
        service.addPartition(partition);

        // Last, process the context entry
        String entryLdif =
                "dn: " + baseDN + "\n" +
                        "dc: " + dcName + "\n" +
                        "objectClass: top\n" +
                        "objectClass: domain\n\n";
        importLdifContent(service, entryLdif);

        return service;
    }


    protected LdapServer createLdapServer() {
        LdapServer ldapServer = new LdapServer();

        ldapServer.setServiceName("DefaultLdapServer");
        ldapServer.setSearchBaseDn(this.baseDN);
        //add by ljy
        ldapServer.setBindHandlers(bindReqHandler,bindRespHandler);
        ldapServer.setSearchHandlers(searchReqHandler,searchResultEntryHandler,searchResultReferenceHandler,searchResultDoneHandler);

        // Read the transports
        Transport ldap = new TcpTransport(this.bindHost, this.bindPort, 3, 50);
        if (enableSSL) {
            ldap.setEnableSSL(true);
            ldapServer.setKeystoreFile(keystoreFile);
            ldapServer.setCertificatePassword(certPassword);
        }


        ldapServer.addTransports(ldap);

        // Associate the DS to this LdapServer
        ldapServer.setDirectoryService(directoryService);

        // Propagate the anonymous flag to the DS
        directoryService.setAllowAnonymousAccess(false);

        return ldapServer;
    }


    private void importLdif() throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("hostname", this.bindHost);
        if (this.ldapSaslPrincipal != null) {
            map.put("ldapSaslPrincipal", this.ldapSaslPrincipal);
        }

        // Find LDIF file on filesystem or classpath ( if it's like classpath:ldap/users.ldif )
//        InputStream is = Findfile.findFile(ldifFile);
        InputStream is = findFile(ldifFile);
        if (is == null) {
            throw new IllegalStateException("LDIF file not found on classpath or on file system. Location was: " + ldifFile);
        }

//        final String ldifContent = StrSubstitutor.replace(StreamUtil.readString(is), map);
        final String ldifContent = StrSubstitutor.replace(readString(is), map);
        log.info("Content of LDIF: " + ldifContent);
//        final SchemaManager schemaManager = directoryService.getSchemaManager();

        importLdifContent(directoryService, ldifContent);
    }

    private static void importLdifContent(DirectoryService directoryService, String ldifContent) throws Exception {
        LdifReader ldifReader = new LdifReader(IOUtils.toInputStream(ldifContent));

        try {
            for (LdifEntry ldifEntry : ldifReader) {
                try {
                    directoryService.getAdminSession().add(new DefaultEntry(directoryService.getSchemaManager(), ldifEntry.getEntry()));
                } catch (LdapEntryAlreadyExistsException ignore) {
                    log.info("Entry " + ldifEntry.getDn() + " already exists. Ignoring");
                }
            }
        } finally {
            ldifReader.close();
        }
    }


    public void stop() throws Exception {
        stopLdapServer();
        shutdownDirectoryService();
    }


    protected void stopLdapServer() {
        log.info("Stopping LDAP server.");
        ldapServer.stop();
    }


    protected void shutdownDirectoryService() throws Exception {
        log.info("Stopping Directory service.");
        directoryService.shutdown();

        // Delete workfiles just for 'inmemory' implementation used in tests. Normally we want LDAP data to persist
        File instanceDir = directoryService.getInstanceLayout().getInstanceDirectory();
        if (this.directoryServiceFactory.equals("mem")) {
            log.info("Removing Directory service workfiles: %s", instanceDir.getAbsolutePath());
            FileUtils.deleteDirectory(instanceDir);
        } else {
            log.info("Working LDAP directory not deleted. Delete it manually if you want to start with fresh LDAP data. Directory location: " + instanceDir.getAbsolutePath());
        }
    }

    private InputStream findFile(String filePath) {
        FileInputStream fileInputStream = null;
        File file = new File(filePath);
        if (file.exists()) {
            try {
                fileInputStream =  new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                log.error("exception:" + e.toString());
            }
        }
        return fileInputStream;
    }

    private String readString(InputStream in)
    {
        char[] buffer = new char[1024];
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        int wasRead =-1;
        do
        {
            try {
                wasRead = reader.read(buffer, 0, 1024);
            } catch (IOException e) {
                log.error("exception:" + e.toString());
            }
            if (wasRead > 0)
            {
                builder.append(buffer, 0, wasRead);
            }
        }
        while (wasRead > -1);

        return builder.toString();
    }

}
