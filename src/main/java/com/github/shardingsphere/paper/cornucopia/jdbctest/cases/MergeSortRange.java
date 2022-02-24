package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class MergeSortRange implements SysbenchBenchmark{
    
    private final Connection connection;
    
    private final PreparedStatement mergeRangeStatement;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    public MergeSortRange(Connection connection) throws SQLException {
        this.connection = connection;
        String sql = "SELECT c FROM sbtest1 WHERE ? > 0 AND id BETWEEN 1 AND 100";
        mergeRangeStatement = connection.prepareStatement(sql);
//        if ("mysql".equalsIgnoreCase(BenchmarkEnvConstant.dbType)) {
//            mergeRangeStatement.setFetchSize(Integer.MIN_VALUE);
//        } else if ("pgsql".equalsIgnoreCase(BenchmarkEnvConstant.dbType)){
//            mergeRangeStatement.setFetchSize(1);
//        }
    }
    
    @Override
    public void execute() throws SQLException {
        int id = random.nextInt(BenchmarkEnvConstant.tableSize);
        mergeRangeStatement.setInt(1, id);
        mergeRangeStatement.execute();
    }
}
