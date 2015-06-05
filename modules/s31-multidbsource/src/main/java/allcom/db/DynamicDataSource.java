package allcom.db;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by ljy on 15/6/4.
 * ok
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

        @Override
        protected Object determineCurrentLookupKey() {
            return DatabaseContextHolder.getDbType();
        }

}

