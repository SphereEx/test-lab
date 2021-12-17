package com.github.taojintianxia.cornucopia.jdbctest.cases;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class NonIndexUpdates implements SysbenchBenchmark {

    private final Connection connection;

    private final PreparedStatement pointSelectStatement;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public NonIndexUpdates(Connection connection ) throws SQLException {
        this.connection = connection;
        pointSelectStatement = connection.prepareStatement("UPDATE sbtest1 SET c=? WHERE id=?");
    }

    @Override
    public void execute() throws SQLException {
        pointSelectStatement.setString(1, random.nextInt(SysbenchConstant.tableSize)+"");
        pointSelectStatement.setInt(2, random.nextInt(SysbenchConstant.tableSize));
        pointSelectStatement.execute();
    }
}
