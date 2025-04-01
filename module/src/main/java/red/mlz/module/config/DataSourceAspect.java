package red.mlz.module.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataSourceAspect {
    // 设置读操作的数据源为从库
    @Before("@annotation(ReadOnly)")
    public void setReadDataSourceType() {
        DbContextHolder.setDbType(DbContextHolder.DbType.SLAVE);
        log.info("切换读操作");

    }

}


