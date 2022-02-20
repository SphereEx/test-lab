package com.github.taojintianxia.cornucopia.jdbctest.cases;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;

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
        nonIndexUpdatesStatements = new PreparedStatement[SysbenchConstant.tables];
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            nonIndexUpdatesStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET c=? WHERE id=?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(SysbenchConstant.tables);
        nonIndexUpdatesStatements[i].setString(1, random.nextInt(SysbenchConstant.tableSize)+"");
        nonIndexUpdatesStatements[i].setInt(2, random.nextInt(SysbenchConstant.tableSize));
        nonIndexUpdatesStatements[i].execute();
    }
}
