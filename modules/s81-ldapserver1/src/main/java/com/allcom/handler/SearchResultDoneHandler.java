package com.allcom.handler;

import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.directory.api.ldap.model.message.SearchResultReference;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapResponseHandler;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class SearchResultDoneHandler extends LdapResponseHandler<SearchResultDone> {

    public SearchResultDoneHandler(){}

    public void handle(LdapSession ldapSession,SearchResultDone searchResultDone){

    }

}
