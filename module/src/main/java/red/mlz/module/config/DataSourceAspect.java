package red.mlz.module.config;

import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
public class DataSourceAspect {
    // 设置读操作的数据源为从库
    @Before("@annotation(ReadOnly)")
    public void setReadDataSourceType() {
        DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
    }

}


