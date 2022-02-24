package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class ReadOnly implements SysbenchBenchmark {

    private final Connection connection;

    private final PreparedStatement[] readOnlyStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public ReadOnly(Connection connection ) throws SQLException {
        this.connection = connection;
        readOnlyStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            readOnlyStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id = ?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        connection.setAutoCommit(false);
        readOnlyStatements[i].setInt(1, random.nextInt(BenchmarkEnvConstant.tableSize));
        readOnlyStatements[i].execute();
        connection.commit();
    }
}
