package com.allcom.handler;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.exception.LdapAuthenticationException;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.exception.LdapUnwillingToPerformException;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.message.*;
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
import java.util.List;

import static java.lang.Math.min;
import static org.apache.directory.server.ldap.LdapServer.NO_SIZE_LIMIT;
import static org.apache.directory.server.ldap.LdapServer.NO_TIME_LIMIT;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.directory.api.ldap.codec.controls.search.pagedSearch.PagedResultsDecorator;
import org.apache.directory.api.ldap.extras.controls.syncrepl.syncInfoValue.SyncRequestValue;
import org.apache.directory.api.ldap.model.constants.SchemaConstants;
import org.apache.directory.api.ldap.model.cursor.Cursor;
import org.apache.directory.api.ldap.model.cursor.CursorClosedException;
import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Value;
import org.apache.directory.api.ldap.model.exception.LdapException;
import org.apache.directory.api.ldap.model.exception.LdapOperationException;
import org.apache.directory.api.ldap.model.exception.LdapURLEncodingException;
import org.apache.directory.api.ldap.model.exception.OperationAbandonedException;
import org.apache.directory.api.ldap.model.filter.EqualityNode;
import org.apache.directory.api.ldap.model.filter.ExprNode;
import org.apache.directory.api.ldap.model.filter.OrNode;
import org.apache.directory.api.ldap.model.filter.PresenceNode;
import org.apache.directory.api.ldap.model.message.Control;
import org.apache.directory.api.ldap.model.message.LdapResult;
import org.apache.directory.api.ldap.model.message.Referral;
import org.apache.directory.api.ldap.model.message.ReferralImpl;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.message.ResultResponseRequest;
import org.apache.directory.api.ldap.model.message.SearchRequest;
import org.apache.directory.api.ldap.model.message.SearchResultDone;
import org.apache.directory.api.ldap.model.message.SearchResultEntry;
import org.apache.directory.api.ldap.model.message.SearchResultEntryImpl;
import org.apache.directory.api.ldap.model.message.SearchResultReference;
import org.apache.directory.api.ldap.model.message.SearchResultReferenceImpl;
import org.apache.directory.api.ldap.model.message.SearchScope;
import org.apache.directory.api.ldap.model.message.controls.ManageDsaIT;
import org.apache.directory.api.ldap.model.message.controls.PagedResults;
import org.apache.directory.api.ldap.model.message.controls.PersistentSearch;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.schema.AttributeType;
import org.apache.directory.api.ldap.model.url.LdapUrl;
import org.apache.directory.api.util.Strings;
import org.apache.directory.server.core.api.DirectoryService;
import org.apache.directory.server.core.api.ReferralManager;
import org.apache.directory.server.core.api.entry.ClonedServerEntry;
import org.apache.directory.server.core.api.event.EventType;
import org.apache.directory.server.core.api.event.NotificationCriteria;
import org.apache.directory.server.core.api.partition.PartitionNexus;
import org.apache.directory.server.i18n.I18n;
import org.apache.directory.server.ldap.LdapSession;
import org.apache.directory.server.ldap.handlers.LdapRequestHandler;
import org.apache.directory.server.ldap.handlers.PersistentSearchListener;
import org.apache.directory.server.ldap.handlers.SearchAbandonListener;
import org.apache.directory.server.ldap.handlers.SearchTimeLimitingMonitor;
import org.apache.directory.server.ldap.handlers.controls.PagedSearchContext;
import org.apache.directory.server.ldap.replication.provider.ReplicationRequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ljy on 16/7/7.
 * ok
 */
@Component
public class SearchReqHandler extends LdapRequestHandler<SearchRequest> {
    private static final Logger log = LoggerFactory.getLogger(SearchReqHandler.class);

    public SearchReqHandler(){}

    public void handle( LdapSession session, SearchRequest req ) throws Exception {

//        String baseDnS = searchRequest.getBase().toString();
//        List<String> list = searchRequest.getAttributes();
//        String scope = searchRequest.getScope().toString();
//        ExprNode filter= searchRequest.getFilter();

        log.debug("Handling single reply request: {}", req);

        Map<String,Control> map = req.getControls();

        // check first for the syncrepl search request decorator
        if ( req.getControls().containsKey( SyncRequestValue.OID ) )
        {
//            handleReplication( session, req );
            log.info("a");
        }
        // if we have the ManageDSAIt decorator, go directly
        // to the handling without pre-processing the request
        else if ( req.getControls().containsKey( ManageDsaIT.OID ) )
        {
            //Test进入该流程

            // If the ManageDsaIT decorator is present, we will
            // consider that the user wants to get entry which
            // are referrals as plain entry. We have to return
            // SearchResponseEntry elements instead of
            // SearchResponseReference elements.
            log.debug( "ManageDsaITControl detected." );
            handleIgnoringReferrals( session, req );
        }
        else
        {
            //outlook进入该流程

            // No ManageDsaIT decorator. If the found entries is a referral,
            // we will return SearchResponseReference elements.
            log.debug( "ManageDsaITControl NOT detected." );

            switch ( req.getType() )
            {
                case SEARCH_REQUEST:
                    handleWithReferrals( session, req );
                    break;

                default:
                    throw new IllegalStateException( I18n.err( I18n.ERR_685, req ) );
            }
        }

    }


}
