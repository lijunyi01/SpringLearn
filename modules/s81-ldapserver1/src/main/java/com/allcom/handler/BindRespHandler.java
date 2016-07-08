package com.allcom.handler;

import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapRequestHandler;
import org.apache.directory.server.ldap.handlers.LdapResponseHandler;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class BindRespHandler extends LdapResponseHandler<BindResponse> {

    public BindRespHandler(){}

    public void handle(LdapSession ldapSession,BindResponse bindResponse){

    }

}
