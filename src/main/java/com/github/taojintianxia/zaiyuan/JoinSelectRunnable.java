package com.github.taojintianxia.zaiyuan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JoinSelectRunnable implements Runnable {

    private Connection connection;

    public JoinSelectRunnable(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        try {
            PreparedStatement statement =
                    connection.prepareStatement("SELECT t1.c, t2.pad FROM sbtest1 t1 JOIN sbtest2 t2 ON t1.k = t2.k WHERE t1.k > 10000000 AND t1.k < 10001000");
            for(int i = 0; i < 100; i++) {
                runItem(statement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void runItem(PreparedStatement statement) {
        long startTime = System.currentTimeMillis();
        try {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        long latency = System.currentTimeMillis() - startTime;
        ICDEApplication.totalTime.addAndGet(latency);
        ICDEApplication.totalTransaction.addAndGet(1);
    }
}
