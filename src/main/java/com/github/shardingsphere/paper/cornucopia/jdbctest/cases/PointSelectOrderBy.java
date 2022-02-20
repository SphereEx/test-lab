package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PointSelectOrderBy implements SysbenchBenchmark {
    
    private final PreparedStatement pointSelectStatement;

    public PointSelectOrderBy(Connection connection) throws SQLException {
        pointSelectStatement = connection.prepareStatement("SELECT c FROM sbtest1 WHERE id BETWEEN 1 AND 100");
    }

    @Override
    public void execute() throws SQLException {
        pointSelectStatement.execute();
    }
}
