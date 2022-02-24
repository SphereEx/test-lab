package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class SelectMergeSort implements SysbenchBenchmark{
    
    private final Connection connection;
    
    private final PreparedStatement[] selectCStatements;
    
    private final PreparedStatement[] selectSumStatements;
    
    private final PreparedStatement[] selectCOrderStatements;
    
    private final PreparedStatement[] selectDistinctCOrderStatements;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    public SelectMergeSort(Connection connection) throws SQLException {
        this.connection = connection;
        selectCStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        selectSumStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        selectCOrderStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        selectDistinctCOrderStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            selectCStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ?");
            selectCStatements[i].setFetchSize(Integer.MIN_VALUE);
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            selectSumStatements[i] = connection.prepareStatement("SELECT SUM(k) FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ?");
            selectSumStatements[i].setFetchSize(Integer.MIN_VALUE);
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            selectCOrderStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ? order by c");
            selectCOrderStatements[i].setFetchSize(Integer.MIN_VALUE);
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            selectDistinctCOrderStatements[i] = connection.prepareStatement("SELECT DISTINCT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ? order by c");
            selectDistinctCOrderStatements[i].setFetchSize(Integer.MIN_VALUE);
        }
    }
    
    @Override
    public void execute() throws SQLException {
        int tableIndex = random.nextInt(BenchmarkEnvConstant.tables);
        if (BenchmarkEnvConstant.useTransaction) {
            connection.setAutoCommit(false);
            execute0(tableIndex);
            connection.commit();
        } else {
            execute0(tableIndex);
        }
    }
    
    private void execute0(final int tableIndex) throws SQLException {
        selectCStatements[tableIndex].setInt(1, BenchmarkEnvConstant.mergeSortFrom);
        selectCStatements[tableIndex].setInt(2, BenchmarkEnvConstant.mergeSortTo);
        selectCStatements[tableIndex].execute();
        selectSumStatements[tableIndex].setInt(1, BenchmarkEnvConstant.mergeSortFrom);
        selectSumStatements[tableIndex].setInt(2, BenchmarkEnvConstant.mergeSortTo);
        selectSumStatements[tableIndex].execute();
        selectCOrderStatements[tableIndex].setInt(1, BenchmarkEnvConstant.mergeSortFrom);
        selectCOrderStatements[tableIndex].setInt(2, BenchmarkEnvConstant.mergeSortTo);
        selectCOrderStatements[tableIndex].execute();
        selectDistinctCOrderStatements[tableIndex].setInt(1, BenchmarkEnvConstant.mergeSortFrom);
        selectDistinctCOrderStatements[tableIndex].setInt(2, BenchmarkEnvConstant.mergeSortTo);
        selectDistinctCOrderStatements[tableIndex].execute();
    }
}
