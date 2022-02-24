package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class IndexUpdates implements SysbenchBenchmark {
    
    private final PreparedStatement[] indexUpdatesStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public IndexUpdates(Connection connection ) throws SQLException {
        indexUpdatesStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            indexUpdatesStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET k=k+1 WHERE id=?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        indexUpdatesStatements[i].setInt(1, random.nextInt(BenchmarkEnvConstant.tableSize));
        indexUpdatesStatements[i].execute();
    }
}
