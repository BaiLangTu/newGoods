package red.mlz.module.config.dynamic.datasource;

import org.springframework.context.annotation.Import;
import red.mlz.module.config.dynamic.datasource.config.DynamicDataSourceConfig;

import java.lang.annotation.*;

/**
 * EnableDataSource
 *
 **/

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({DynamicDataSourceConfig.class})
public @interface EnableDataSource {
}



