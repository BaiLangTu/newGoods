package red.mlz.module.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // 判断当前操作是读操作还是写操作

        return DataSourceContextHolder.getDbType();
    }
}
