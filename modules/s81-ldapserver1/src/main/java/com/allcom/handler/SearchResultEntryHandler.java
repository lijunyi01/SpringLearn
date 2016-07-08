package com.allcom.handler;

import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapResponseHandler;
import org.springframework.stereotype.Component;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class SearchResultEntryHandler extends LdapResponseHandler<SearchResultEntry> {

    public SearchResultEntryHandler(){}

    public void handle(LdapSession ldapSession,SearchResultEntry searchResultEntry){

    }

}
