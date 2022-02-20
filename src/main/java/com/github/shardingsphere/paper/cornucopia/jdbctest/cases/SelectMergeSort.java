package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.SysbenchConstant;

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
        selectCStatements = new PreparedStatement[SysbenchConstant.tables];
        selectSumStatements = new PreparedStatement[SysbenchConstant.tables];
        selectCOrderStatements = new PreparedStatement[SysbenchConstant.tables];
        selectDistinctCOrderStatements = new PreparedStatement[SysbenchConstant.tables];
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            selectCStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ?");
        }
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            selectSumStatements[i] = connection.prepareStatement("SELECT SUM(k) FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ?");
        }
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            selectCOrderStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ? order by c");
        }
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            selectDistinctCOrderStatements[i] = connection.prepareStatement("SELECT DISTINCT c FROM sbtest" +(i+1)+" WHERE id BETWEEN ? AND ? order by c");
        }
    }
    
    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(SysbenchConstant.tables);
        connection.setAutoCommit(false);
        selectCStatements[i].setInt(1,SysbenchConstant.mergeSortFrom);
        selectCStatements[i].setInt(2,SysbenchConstant.mergeSortTo);
        selectCStatements[i].execute();
        selectSumStatements[i].setInt(1,SysbenchConstant.mergeSortFrom);
        selectSumStatements[i].setInt(2,SysbenchConstant.mergeSortTo);
        selectSumStatements[i].execute();
        selectCOrderStatements[i].setInt(1,SysbenchConstant.mergeSortFrom);
        selectCOrderStatements[i].setInt(2,SysbenchConstant.mergeSortTo);
        selectCOrderStatements[i].execute();
        selectDistinctCOrderStatements[i].setInt(1,SysbenchConstant.mergeSortFrom);
        selectDistinctCOrderStatements[i].setInt(2,SysbenchConstant.mergeSortTo);
        selectDistinctCOrderStatements[i].execute();
        connection.commit();
    }
}
