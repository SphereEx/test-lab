package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class Deletes implements SysbenchBenchmark {
    
    private final PreparedStatement[] deleteStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public Deletes(Connection connection ) throws SQLException {
        deleteStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            deleteStatements[i] = connection.prepareStatement("DELETE FROM sbtest" +(i+1)+" WHERE id = ?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        deleteStatements[i].setInt(1, random.nextInt(BenchmarkEnvConstant.tableSize));
        deleteStatements[i].execute();
    }
}
