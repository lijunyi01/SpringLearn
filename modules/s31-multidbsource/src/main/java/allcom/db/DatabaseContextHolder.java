package allcom.db;

/**
 * Created by ljy on 15/6/4.
 * ok
 */
public class DatabaseContextHolder {

    //线程安全的类型ThreadLocal<String>
    private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();

    public static void setDbType(String dataSourceType) {
        contextHolder.set(dataSourceType);
    }

    public static String getDbType() {
        return contextHolder.get();
    }

    public static void clearDbType() {
        contextHolder.remove();
    }
}
