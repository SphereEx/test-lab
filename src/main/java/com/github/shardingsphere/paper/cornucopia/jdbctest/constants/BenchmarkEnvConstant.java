package com.github.shardingsphere.paper.cornucopia.jdbctest.constants;

public class BenchmarkEnvConstant {
    
    public static String jdbcType = "ss-jdbc";
    
    public static String dbType = "db-type";
    
    public static String jdbcUrl= "jdbc-url";
    
    public static String username = "username";
    
    public static String password = "password";

    public static String configFilePath;

    public static int time;

    public static int thread;

    public static String scriptName;

    public static int tableSize;
    
    public static int tables;
    
    public static int percentile;

    public static String distributionTransaction;
    
    public static int mergeSortFrom;
    
    public static int mergeSortTo;
    
    public static boolean useTransaction;
    
    public static void initConstants() {
        jdbcType = System.getProperty("jdbc-type");
        configFilePath = System.getProperty("conf");
        time = Integer.parseInt(System.getProperty("time"));
        thread = Integer.parseInt(System.getProperty("thread"));
        scriptName = System.getProperty("script");
        tableSize = Integer.parseInt(System.getProperty("table-size"));
        tables = Integer.parseInt(System.getProperty("tables"));
        distributionTransaction = System.getProperty("transaction-mode");
        percentile = getIntProperty("percentile", 99);
        jdbcType = getStringProperty("jdbc-type", "ss-jdbc");
        jdbcUrl = getStringProperty("jdbc-url", null);
        username = getStringProperty("username", null);
        password = getStringProperty("password", null);
        mergeSortFrom = getIntProperty("merge-sort-from", 0);
        mergeSortTo = getIntProperty("merge-sort-to", 0);
        dbType = getStringProperty("db-type", "mysql");
        if (null != System.getProperty("use-transaction")) {
            useTransaction = Boolean.parseBoolean(System.getProperty("use-transaction"));
        }
    }
    
    private static String getStringProperty(String key, String defaultProperty) {
        String property = System.getProperty(key);
        String result; 
        if (null != property) {
            result = property;
        } else {
            result = defaultProperty;
        }
        return result;
    }
    
    private static int getIntProperty(String key, int defaultValue) {
        String property = System.getProperty(key);
        int result = defaultValue;
        if (null != property) {
            result = Integer.parseInt(property);
        }
        return result;
    }
}
