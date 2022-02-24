package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class NonIndexUpdates implements SysbenchBenchmark {

    private final Connection connection;

    private final PreparedStatement[] nonIndexUpdatesStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public NonIndexUpdates(Connection connection ) throws SQLException {
        this.connection = connection;
        nonIndexUpdatesStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            nonIndexUpdatesStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET c=? WHERE id=?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        nonIndexUpdatesStatements[i].setString(1, random.nextInt(BenchmarkEnvConstant.tableSize)+"");
        nonIndexUpdatesStatements[i].setInt(2, random.nextInt(BenchmarkEnvConstant.tableSize));
        nonIndexUpdatesStatements[i].execute();
    }
}
