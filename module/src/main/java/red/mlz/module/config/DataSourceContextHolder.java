package red.mlz.module.config;

public class DataSourceContextHolder {

    // 线程本地存储当前的数据源类型（主数据源/副数据源）
    private static ThreadLocal<String> contextHolder = new ThreadLocal<>();

    // 设置数据源类型
    public static void setDbType(String dbType) {
        contextHolder.set(dbType);
    }

    // 获取数据源类型
    public static String getDbType() {
        return contextHolder.get();
    }

    // 清除数据源类型
    public static void clearDbType() {
        contextHolder.remove();
    }
}
