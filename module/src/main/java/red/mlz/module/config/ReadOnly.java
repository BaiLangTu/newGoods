package red.mlz.module.config;

import red.mlz.module.config.dynamic.datasource.constant.DataSourceName;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ReadOnly {
    String value() default DataSourceName.SECONDARY;

}
