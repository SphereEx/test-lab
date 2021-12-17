package com.github.taojintianxia.cornucopia.jdbctest.cases;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class IndexUpdates implements SysbenchBenchmark {

    private final Connection connection;

    private final PreparedStatement pointSelectStatement;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public IndexUpdates(Connection connection ) throws SQLException {
        this.connection = connection;
        pointSelectStatement = connection.prepareStatement("UPDATE sbtest1 SET k=k+1 WHERE id=?");
    }

    @Override
    public void execute() throws SQLException {
        pointSelectStatement.setInt(1, random.nextInt(SysbenchConstant.tableSize));
        pointSelectStatement.execute();
    }
}
