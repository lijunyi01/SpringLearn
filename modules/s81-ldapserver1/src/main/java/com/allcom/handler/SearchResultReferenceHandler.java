package com.allcom.handler;

import org.apache.directory.api.ldap.model.message.SearchResultReference;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapResponseHandler;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class SearchResultReferenceHandler extends LdapResponseHandler<SearchResultReference> {

    public SearchResultReferenceHandler(){}

    public void handle(LdapSession ldapSession,SearchResultReference searchResultReference){

    }

}
