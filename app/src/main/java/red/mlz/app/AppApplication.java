package red.mlz.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import red.mlz.module.config.dynamic.datasource.EnableDataSource;

@EnableScheduling
@SpringBootApplication(scanBasePackages={"red.mlz","red.mlz.module.utils"})
@MapperScan({"red.mlz.module.module.*.mapper","red.mlz.module.utils"})
@EnableDataSource
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
