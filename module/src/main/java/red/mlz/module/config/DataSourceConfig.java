package red.mlz.module.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    // 配置主数据源
    @Bean(name = "dataSource")
    @Primary
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://47.113.121.13:3306/xiaobai_Mall?allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useAffectedRows=true")
                .username("replica")
                .password("Aa258369")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();

    }

    // 配置从数据源
    @Bean(name = "secondaryDataSource")
    public DataSource replicaDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:mysql://47.113.121.13:3307/xiaobai_Mall?allowPublicKeyRetrieval=true&characterEncoding=utf8&useSSL=false&serverTimezone=UTC&rewriteBatchedStatements=true&useAffectedRows=true")
                .username("root")
                .password("Aa258369")
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .build();
    }
}