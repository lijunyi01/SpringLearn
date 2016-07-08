package com.allcom.handler;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.exception.LdapUnwillingToPerformException;
import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.server.core.api.CoreSession;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.OperationEnum;
import org.apache.directory.server.core.api.entry.ClonedServerEntry;
import org.apache.directory.server.core.api.interceptor.context.BindOperationContext;
import org.apache.directory.server.core.shared.DefaultCoreSession;
import org.apache.directory.server.i18n.I18n;
import org.apache.directory.server.ldap.LdapProtocolUtils;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapRequestHandler;
import org.apache.directory.server.ldap.handlers.sasl.MechanismHandler;
import org.apache.directory.server.ldap.handlers.sasl.SaslConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class BindReqHandler extends LdapRequestHandler<BindRequest> {
    private static final Logger log = LoggerFactory.getLogger(LdapRequestHandler.class);

    public BindReqHandler(){}

//    public void handle(LdapSession ldapSession,BindRequest bindRequest){
////        String s1 = ldapSession.getCurrentMechanism();
////        byte[] bytes = bindRequest.getCredentials();
////        String s2 = bindRequest.toString();
////        bindRequest.setCredentials("password");
////        System.out.print(bindRequest.toString());
//        //客户端的密码
//        byte[] bytes = bindRequest.getCredentials();
//        String cred = new String(bytes);
////        if(bindRequest.isSimple()){
////            System.out.print("aaa");
////        }
//        try {
//            LdapResult ldapResult = bindRequest.getResultResponse().getLdapResult();
//            ldapResult.setResultCode(ResultCodeEnum.SUCCESS);
//            ldapSession.setAuthenticated();
//
//            ldapSession.getIoSession().write(bindRequest.getResultResponse());
//        }catch (Exception e){
//
//        }
//    }

    public void handle( LdapSession ldapSession, BindRequest bindRequest ) throws Exception
    {
        log.debug("Received: {}", bindRequest);

        // Guard clause:  LDAP version 3
        if ( !bindRequest.getVersion3() )
        {
            log.error( I18n.err( I18n.ERR_162 ) );
            LdapResult bindResult = bindRequest.getResultResponse().getLdapResult();
            bindResult.setResultCode( ResultCodeEnum.PROTOCOL_ERROR );
            bindResult.setDiagnosticMessage( I18n.err( I18n.ERR_163 ) );
            ldapSession.getIoSession().write( bindRequest.getResultResponse() );
            return;
        }

        // Deal with the two kinds of authentication : Simple and SASL
        if ( bindRequest.isSimple() )
        {
            handleSimpleAuth( ldapSession, bindRequest );
        }
        else
        {
//            handleSaslAuth( ldapSession, bindRequest );
            log.error("simpleauth only!");
        }
    }

    /**
     * Handle the Simple authentication.
     *
     * @param ldapSession The associated Session
     * @param bindRequest The BindRequest received
     * @throws Exception If the authentication cannot be done
     */
    // This will suppress PMD.EmptyCatchBlock warnings in this method
    private void handleSimpleAuth( LdapSession ldapSession, BindRequest bindRequest ) throws Exception
    {
        DirectoryService directoryService = ldapServer.getDirectoryService();

        // if the user is already bound, we have to unbind him
        if ( ldapSession.isAuthenticated() )
        {
            // We already have a bound session for this user. We have to
            // abandon it first.
            ldapSession.getCoreSession().unbind();
        }

        // Set the status to SimpleAuthPending
        ldapSession.setSimpleAuthPending();

        // Now, bind the user

        // create a new Bind context, with a null session, as we don't have
        // any context yet.
        BindOperationContext bindContext = new BindOperationContext( null );

        // Stores the Dn of the user to check, and its password
        Dn userDn1 = bindRequest.getDn();
        String user = userDn1.toString();
        byte[] credentials1 = bindRequest.getCredentials();
        String pass = new String(credentials1);
        log.info("user and pass:"+ user + ":"+pass);

        byte[] credentials;
        Dn userDn;
        //这里可以加入额外的验证，验证通过就填入正确的用户名密码
        if(true){
            userDn = new Dn("uid=admin,ou=system");
            String user2 = userDn.toString();
            credentials = "secret".getBytes();
            bindRequest.setDn(userDn);
        }

        bindContext.setDn( userDn );
        bindContext.setCredentials( credentials );
        bindContext.setIoSession( ldapSession.getIoSession() );
        bindContext.setInterceptors( directoryService.getInterceptors( OperationEnum.BIND ) );

        // Stores the request controls into the operation context
        LdapProtocolUtils.setRequestControls(bindContext, bindRequest);

        try
        {
            /*
             * Referral handling as specified by RFC 3296 here:
             *
             *      http://www.faqs.org/rfcs/rfc3296.html
             *
             * See section 5.6.1 where if the bind principal Dn is a referral
             * we return an invalidCredentials result response.  Optionally we
             * could support delegated authentication in the future with this
             * potential.  See the following JIRA for more on this possibility:
             *
             *      https://issues.apache.org/jira/browse/DIRSERVER-1217
             *
             * NOTE: if this is done then this handler should extend the
             * a modified form of the ReferralAwareRequestHandler so it can
             * detect conditions where ancestors of the Dn are referrals
             * and delegate appropriately.
             */
            Entry principalEntry = null;

            try
            {
                principalEntry = directoryService.getAdminSession().lookup( bindRequest.getDn() );
            }
            catch ( LdapException le )
            {
                // this is OK
            }

            if ( principalEntry == null ) {
                log.info("The {} principalDN cannot be found in the server : bind failure.", bindRequest.getName() );
            }
            else if ( ( (ClonedServerEntry) principalEntry ).getOriginalEntry().contains(
                    SchemaConstants.OBJECT_CLASS_AT,
                    SchemaConstants.REFERRAL_OC ) )
            {
                log.info( "Bind principalDn points to referral." );
                LdapResult result = bindRequest.getResultResponse().getLdapResult();
                result.setDiagnosticMessage( "Bind principalDn points to referral." );
                result.setResultCode( ResultCodeEnum.INVALID_CREDENTIALS );

                // Reset the session now
                ldapSession.setAnonymous();

                // Write the response
                ldapSession.getIoSession().write( bindRequest.getResultResponse() );

                return;
            }

            // TODO - might cause issues since lookups are not returning all
            // attributes right now - this is an optimization that can be
            // enabled later after determining whether or not this will cause
            // issues.
            // reuse the looked up entry so we don't incur another lookup
            // opContext.setEntry( principalEntry );

            // And call the OperationManager bind operation.
            bindContext.setInterceptors( directoryService.getInterceptors( OperationEnum.BIND ) );
            //这一步实际进行密码验证，如果失败会进入Exception
            directoryService.getOperationManager().bind( bindContext );

            // As a result, store the created session in the Core Session
            CoreSession coreSession = bindContext.getSession();
            ldapSession.setCoreSession( coreSession );

            // Store the IoSession in the coreSession
            ( (DefaultCoreSession) coreSession ).setIoSession( bindContext.getIoSession() );

            // And set the current state accordingly
            if ( !ldapSession.getCoreSession().isAnonymous() )
            {
                ldapSession.setAuthenticated();
            }
            else
            {
                ldapSession.setAnonymous();
            }

            // Return the successful response
            bindRequest.getResultResponse().addAllControls( bindContext.getResponseControls() );
            sendBindSuccess( ldapSession, bindRequest, null );
        }
        catch ( Exception e )
        {
            // Something went wrong. Write back an error message
            // For BindRequest, it should be an InvalidCredentials,
            // no matter what kind of exception we got.
            ResultCodeEnum code = null;
            LdapResult result = bindRequest.getResultResponse().getLdapResult();

            if ( e instanceof LdapUnwillingToPerformException)
            {
                code = ResultCodeEnum.UNWILLING_TO_PERFORM;
                result.setResultCode( code );
            }
            else if ( e instanceof LdapInvalidDnException)
            {
                code = ResultCodeEnum.INVALID_DN_SYNTAX;
                result.setResultCode( code );
            }
            else
            {
                code = ResultCodeEnum.INVALID_CREDENTIALS;
                result.setResultCode( code );
            }

            String msg = code.toString() + ": Bind failed: " + e.getLocalizedMessage();

            if ( log.isDebugEnabled() )
            {
                msg += ":\n" + ExceptionUtils.getStackTrace(e);
                msg += "\n\nBindRequest = \n" + bindRequest.toString();
            }

            Dn dn = null;

            if ( e instanceof LdapAuthenticationException )
            {
                dn = ( (LdapAuthenticationException) e ).getResolvedDn();
            }

            if ( ( dn != null )
                    && ( ( code == ResultCodeEnum.NO_SUCH_OBJECT ) || ( code == ResultCodeEnum.ALIAS_PROBLEM )
                    || ( code == ResultCodeEnum.INVALID_DN_SYNTAX ) || ( code == ResultCodeEnum.ALIAS_DEREFERENCING_PROBLEM ) ) )
            {
                result.setMatchedDn( dn );
            }

            result.setDiagnosticMessage( msg );
            bindRequest.getResultResponse().addAllControls( bindContext.getResponseControls() );

            // Before writing the response, be sure the session is set to anonymous
            ldapSession.setAnonymous();

            // Write the response
            ldapSession.getIoSession().write( bindRequest.getResultResponse() );
        }
        finally
        {
            // Reset LDAP session bind status to anonymous if authentication failed
            if ( !ldapSession.isAuthenticated() )
            {
                ldapSession.setAnonymous();
            }
        }
    }

    /**
     * Send a SUCCESS message back to the client.
     */
    private void sendBindSuccess( LdapSession ldapSession, BindRequest bindRequest, byte[] tokenBytes )
    {
        // Return the successful response
        BindResponse response = (BindResponse) bindRequest.getResultResponse();
        response.getLdapResult().setResultCode( ResultCodeEnum.SUCCESS );
        response.setServerSaslCreds( tokenBytes );

        if ( !ldapSession.getCoreSession().isAnonymous() )
        {
            // If we have not been asked to authenticate as Anonymous, authenticate the user
            ldapSession.setAuthenticated();
        }
        else
        {
            // Otherwise, switch back to Anonymous
            ldapSession.setAnonymous();
        }

        // Clean the SaslProperties, we don't need them anymore
        MechanismHandler handler = (MechanismHandler) ldapSession.getSaslProperty( SaslConstants.SASL_MECH_HANDLER );

        if ( handler != null )
        {
            handler.cleanup( ldapSession );
        }

        ldapSession.getIoSession().write( response);

        log.info("Returned SUCCESS message: {}.", response );
    }


}
