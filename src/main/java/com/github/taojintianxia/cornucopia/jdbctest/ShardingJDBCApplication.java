package com.github.taojintianxia.cornucopia.jdbctest;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;
import com.github.taojintianxia.cornucopia.jdbctest.executor.BenchmarkExecutor;
import com.github.taojintianxia.cornucopia.jdbctest.factory.BenchmarkFactory;
import com.github.taojintianxia.cornucopia.jdbctest.validation.SysbenchParamValidator;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShardingJDBCApplication {

    private static final ConcurrentLinkedQueue<Long> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();

    public static void main( String... args ) throws SQLException, IOException {
        SysbenchParamValidator.validateSysbenchParam();
        SysbenchConstant.initConstants();
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(new File(SysbenchConstant.configFilePath));
        ExecutorService service = Executors.newFixedThreadPool(SysbenchConstant.thread);
        for (int i = 0; i < SysbenchConstant.thread; i++) {
            BenchmarkExecutor benchmarkExecutor = new BenchmarkExecutor(BenchmarkFactory.getBenchmarkByName(SysbenchConstant.scriptName, dataSource.getConnection()), concurrentLinkedQueue);
            service.submit(benchmarkExecutor);
        }
        Timer timer = new Timer();
        ThreadPoolTimerTask threadPoolTimerTask = new ThreadPoolTimerTask();
        threadPoolTimerTask.setExecutorService(service);
        timer.schedule(threadPoolTimerTask, SysbenchConstant.time * 1000L);
    }

    private static class ThreadPoolTimerTask extends TimerTask {

        private ExecutorService executorService;

        public void setExecutorService( ExecutorService executorService ) {
            this.executorService = executorService;
        }

        @Override
        public void run() {
            System.out.println("----------------------------------------------------------");
            System.out.println("all tests finished");
            System.out.println("----------------------------------------------------------");
            try {
                executorService.shutdownNow();
            }catch (Exception e){
                e.printStackTrace();
            }
            analyze();
        }
    }

    private static void analyze() {
        System.out.println("Total execution count : " + concurrentLinkedQueue.size());
        System.out.println("Average time is : " + BigDecimal.valueOf(getAverageTime(concurrentLinkedQueue)).setScale(2, RoundingMode.HALF_UP).doubleValue());
        System.out.println("TPS is : " + concurrentLinkedQueue.size() / SysbenchConstant.time);
    }

    private static double getAverageTime(ConcurrentLinkedQueue<Long> concurrentLinkedQueue) {
        long timeTotal = 0;
        for (long each : concurrentLinkedQueue) {
            timeTotal += each;
        }
        return timeTotal * 1.0 / concurrentLinkedQueue.size();
    }
}
