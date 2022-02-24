package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class PointSelect implements SysbenchBenchmark {
    
    private final PreparedStatement[] pointSelectStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public PointSelect( Connection connection ) throws SQLException {
        pointSelectStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            pointSelectStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id = ?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        pointSelectStatements[i].setInt(1, random.nextInt(BenchmarkEnvConstant.tableSize));
        pointSelectStatements[i].execute();
    }
}
