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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShardingJDBCApplication {

    private static final ConcurrentLinkedQueue<Long> responseTimeLinkedQueue = new ConcurrentLinkedQueue<>();
    
    private static final String prefix = "ShardingSphere-JDBC-";
    
    public static void main( String... args ) throws SQLException, IOException {
        SysbenchParamValidator.validateSysbenchParam();
        SysbenchConstant.initConstants();
        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(new File(SysbenchConstant.configFilePath));
        ExecutorService service = Executors.newFixedThreadPool(SysbenchConstant.thread);
        responseTimeLinkedQueue.clear();
        for (int i = 0; i < SysbenchConstant.thread; i++) {
            BenchmarkExecutor benchmarkExecutor = new BenchmarkExecutor(BenchmarkFactory.getBenchmarkByName(SysbenchConstant.scriptName, dataSource.getConnection()), responseTimeLinkedQueue);
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
        System.out.println("Total execution count : " + responseTimeLinkedQueue.size());
        List<Long> responseTimeList = new ArrayList<>(responseTimeLinkedQueue.size());
        responseTimeList.addAll(responseTimeLinkedQueue);
        Collections.sort(responseTimeList);
        System.out.println("Average time is : " + BigDecimal.valueOf(getAverageTime()).setScale(4, RoundingMode.HALF_UP).doubleValue());
        System.out.println("Min time is : " + BigDecimal.valueOf(getMinTime()).setScale(4, RoundingMode.HALF_UP).doubleValue());
        System.out.println("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(4, RoundingMode.HALF_UP).doubleValue());
        System.out.println("TPS is : " + responseTimeLinkedQueue.size() / SysbenchConstant.time);
        System.out.println(SysbenchConstant.percentile + " percentile is : " + BigDecimal.valueOf(getPercentileTime(responseTimeList)).setScale(4, RoundingMode.HALF_UP).doubleValue());
        try {
            fileOutput();
        } catch (Exception e){
            System.out.println("got an error when writing log : ");
            e.printStackTrace();
        }
    }

    private static double getAverageTime() {
        long timeTotal = 0;
        for (long each : ShardingJDBCApplication.responseTimeLinkedQueue) {
            timeTotal += each;
        }
        return timeTotal * 1.0 / ShardingJDBCApplication.responseTimeLinkedQueue.size();
    }
    
    private static double getMaxime() {
        double maxTime = 0;
        for (long each : ShardingJDBCApplication.responseTimeLinkedQueue) {
            if (each > maxTime) {
                maxTime = each;
            }
        }
        return maxTime;
    }
    
    private static double getMinTime() {
        double minTime = Integer.MAX_VALUE;
        for (long each : ShardingJDBCApplication.responseTimeLinkedQueue) {
            if (each < minTime) {
                minTime = each;
            }
        }
        return minTime;
    }
    
    private static double getPercentileTime(List<Long> responseTimeList) {
        int percentilePosition = responseTimeList.size() * SysbenchConstant.percentile / 100;
        if (percentilePosition >= responseTimeList.size()) {
            percentilePosition = responseTimeList.size() - 1;
        }
        return responseTimeList.get(percentilePosition);
    }
    
    private static void fileOutput() throws IOException {
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(prefix+System.currentTimeMillis()+".log"));
//        bufferedWriter.write("Total execution count : " + responseTimeLinkedQueue.size()+"\n");
//        bufferedWriter.write("Average time is : " + BigDecimal.valueOf(getAverageTime()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"\n");
//        bufferedWriter.write("Min time is :"  + BigDecimal.valueOf(getMinTime()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"\n");
//        bufferedWriter.write("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"\n");
//        bufferedWriter.write("Max time is : " + BigDecimal.valueOf(getMaxime()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"\n");
//        bufferedWriter.write("TPS is : " + responseTimeLinkedQueue.size() / SysbenchConstant.time+"\n");
//        bufferedWriter.write(SysbenchConstant.percentile + " percentile is : " + BigDecimal.valueOf(getPercentileTime()).setScale(4, RoundingMode.HALF_UP).doubleValue()+"\n");
//        bufferedWriter.flush();
//        bufferedWriter.close();
    }
}
