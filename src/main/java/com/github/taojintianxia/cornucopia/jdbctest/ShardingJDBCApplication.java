package com.github.taojintianxia.cornucopia.jdbctest;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;
import com.github.taojintianxia.cornucopia.jdbctest.executor.BenchmarkExecutor;
import com.github.taojintianxia.cornucopia.jdbctest.factory.BenchmarkFactory;
import com.github.taojintianxia.cornucopia.jdbctest.validation.SysbenchParamValidator;
import org.apache.shardingsphere.driver.api.yaml.YamlShardingSphereDataSourceFactory;

import javax.sql.DataSource;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
    
    private static final String prefix = "ShardingSphere-JDBC-";
    
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
        System.out.println("Average time is : " + BigDecimal.valueOf(getAverageTime()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        System.out.println("Min time is : " + BigDecimal.valueOf(getMinTime()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        System.out.println("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        System.out.println("TPS is : " + concurrentLinkedQueue.size() / SysbenchConstant.time);
        System.out.println(SysbenchConstant.percentile + " percentile is : " + BigDecimal.valueOf(getPercentileTime()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        try {
            fileOutput();
        } catch (Exception e){
            System.out.println("got an error when writing log : ");
            e.printStackTrace();
        }
    }

    private static double getAverageTime() {
        long timeTotal = 0;
        for (long each : ShardingJDBCApplication.concurrentLinkedQueue) {
            timeTotal += each;
        }
        return timeTotal * 1.0 / ShardingJDBCApplication.concurrentLinkedQueue.size();
    }
    
    private static double getMaxime() {
        double maxTime = 0;
        for (long each : ShardingJDBCApplication.concurrentLinkedQueue) {
            if (each > maxTime) {
                maxTime = each;
            }
        }
        return maxTime;
    }
    
    private static double getMinTime() {
        double minTime = Integer.MAX_VALUE;
        for (long each : ShardingJDBCApplication.concurrentLinkedQueue) {
            if (each < minTime) {
                minTime = each;
            }
        }
        return minTime;
    }
    
    private static double getPercentileTime() {
        int percentilePosition = ShardingJDBCApplication.concurrentLinkedQueue.size() * SysbenchConstant.percentile / 100;
        if (percentilePosition >= ShardingJDBCApplication.concurrentLinkedQueue.size()) {
            percentilePosition = ShardingJDBCApplication.concurrentLinkedQueue.size() - 1;
        }
        Long[] longArray = new Long[ShardingJDBCApplication.concurrentLinkedQueue.size()];
        ShardingJDBCApplication.concurrentLinkedQueue.toArray(longArray);
        return longArray[percentilePosition];
    }
    
    private static void fileOutput() throws IOException {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(prefix+System.currentTimeMillis()+".log"));
        bufferedWriter.write("Total execution count : " + concurrentLinkedQueue.size()+"\n");
        bufferedWriter.write("Average time is : " + BigDecimal.valueOf(getAverageTime()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"\n");
        bufferedWriter.write("Min time is :"  + BigDecimal.valueOf(getMinTime()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"\n");
        bufferedWriter.write("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"\n");
        bufferedWriter.write("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"\n");
        bufferedWriter.write("TPS is : " + concurrentLinkedQueue.size() / SysbenchConstant.time+"\n");
        bufferedWriter.write(SysbenchConstant.percentile + " percentile is : " + BigDecimal.valueOf(getPercentileTime()).setScale(2, RoundingMode.HALF_UP).doubleValue()+"\n");
    }
}
