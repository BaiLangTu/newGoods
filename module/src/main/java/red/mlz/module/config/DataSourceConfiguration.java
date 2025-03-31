package red.mlz.module.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
public class DataSourceConfiguration {

    @Bean(name = "dynamicDataSource")
    public DataSource dataSource(@Qualifier("dataSource") DataSource primaryDataSource,
                                 @Qualifier("secondaryDataSource") DataSource secondaryDataSource) {
        DynamicDataSource dynamicDataSource = new DynamicDataSource();

        // 使用 HashMap 创建目标数据源
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("PRIMARY", primaryDataSource);
        targetDataSources.put("SECONDARY", secondaryDataSource);

        dynamicDataSource.setTargetDataSources(targetDataSources);
        dynamicDataSource.setDefaultTargetDataSource(primaryDataSource);  // 默认使用主数据源

        return dynamicDataSource;
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
