package com.github.taojintianxia.cornucopia.jdbctest.constants;

public class SysbenchConstant {

    public static String configFilePath;

    public static int time;

    public static int thread;

    public static String scriptName;

    public static int tableSize;
    
    public static int percentile;

    public static String distributionTransaction;
    
    public static void initConstants() {
        configFilePath = System.getProperty("conf");
        time = Integer.parseInt(System.getProperty("time"));
        thread = Integer.parseInt(System.getProperty("thread"));
        scriptName = System.getProperty("script");
        tableSize = Integer.parseInt(System.getProperty("table-size"));
        distributionTransaction = System.getProperty("transaction-mode");
        if (null == System.getProperty("percentile") || "".equals(System.getProperty("percentile"))){
            percentile = 99;
        } else {
            percentile = Integer.parseInt(System.getProperty("percentile"));
        }
    }
}
