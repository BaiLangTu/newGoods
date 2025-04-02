package red.mlz.console;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import red.mlz.module.config.dynamic.datasource.EnableDataSource;

@SpringBootApplication(scanBasePackages="red.mlz")
@MapperScan("red.mlz.module.module.*.mapper")
@EnableDataSource
public class ConsoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsoleApplication.class, args);
    }

}
