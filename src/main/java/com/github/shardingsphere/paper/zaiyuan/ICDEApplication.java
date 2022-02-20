package com.github.shardingsphere.paper.zaiyuan;

import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.atomic.AtomicLong;

public class ICDEApplication {

    public static AtomicLong totalTransaction = new AtomicLong();
    public static AtomicLong totalTime = new AtomicLong();

    public static void main(String[] args) throws Exception {
        String mode = args[0];
        String confPath = args[1];

        Connection connection;
        if(mode.equals("ss_jdbc")) {
            DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(new File(confPath));
            connection = dataSource.getConnection();
        } else {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(confPath, "root", "root");
        }

        for (int i = 0; i < 200; i++) {
            System.out.println(String.format("线程 %s 已经开始执行", i));
            JoinSelectRunnable joinSelectRunnable = new JoinSelectRunnable(connection);
            joinSelectRunnable.run();
        }

        System.out.println(String.format("totalTransaction=%s", totalTransaction));
        System.out.println(String.format("totalTime=%s", totalTime));
    }

}
