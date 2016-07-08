package com.allcom.service;

import org.springframework.stereotype.Service;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Properties;

//import org.apache.directory.shared.ldap.entry.ServerEntry;
//import org.apache.directory.shared.ldap.name.DN;

/**
 * Created by ljy on 16/7/5.
 * ok
 */
@Service
public class Test {

//    public static void main(String[] args) throws Exception {
////        runServer();
//        testClient();
//    }

//    static void runServer() throws Exception {
//        DefaultDirectoryService service = new DefaultDirectoryService();
//        service.getChangeLog().setEnabled(false);
//
//        Partition partition = new JdbmPartition();
//        partition.setId("apache");
//        partition.setSuffix("dc=apache,dc=org");
//        service.addPartition(partition);
//
//        LdapServer ldapService = new LdapServer();
//        ldapService.setTransports(new TcpTransport(1400));
//        ldapService.setDirectoryService(service);
//
//        service.startup();
//
//        // Inject the apache root entry if it does not already exist
//        try {
//            service.getAdminSession().lookup(partition.getSuffixDn());
//        } catch (Exception lnnfe) {
//            DN dnApache = new DN("dc=Apache,dc=Org");
//            ServerEntry entryApache = service.newEntry(dnApache);
//            entryApache.add("objectClass", "top", "domain", "extensibleObject");
//            entryApache.add("dc", "Apache");
//            service.getAdminSession().add(entryApache);
//        }
//
//        DN dnApache = new DN("dc=Apache,dc=Org");
//        ServerEntry entryApache = service.newEntry(dnApache);
//        entryApache.add("objectClass", "top", "domain", "extensibleObject");
//        entryApache.add("dc", "Apache");
//        service.getAdminSession().add(entryApache);
//
//        ldapService.start();
//    }


    public void testClient() throws NamingException {
        Properties p = new Properties();
        p.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        p.setProperty(Context.PROVIDER_URL, "ldap://localhost:10389/");
//        p.setProperty(Context.SECURITY_PRINCIPAL, "uid=admin,ou=system");
        p.setProperty(Context.SECURITY_PRINCIPAL, "uid=ljy@allcomchina.com");
        p.setProperty(Context.SECURITY_CREDENTIALS, "secret1");
        p.setProperty(Context.SECURITY_AUTHENTICATION, "simple");

        DirContext rootCtx = new InitialDirContext(p);
        DirContext ctx = (DirContext) rootCtx.lookup("dc=keycloak,dc=org");
        SearchControls sc = new SearchControls();
        sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

        NamingEnumeration<SearchResult> searchResults = ctx.search("", "(objectclass=*)", sc);

        while (searchResults.hasMoreElements()) {
            SearchResult searchResult = searchResults.next();
            Attributes attributes = searchResult.getAttributes();
            System.out.println("searchResult.attributes: " + attributes) ;
        }
    }
}
