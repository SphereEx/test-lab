package com.github.taojintianxia.cornucopia.jdbctest.cases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class JoinSelect implements SysbenchBenchmark{
    
    private final PreparedStatement joinSelectStatement;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    
    public JoinSelect(Connection connection) throws SQLException {
        joinSelectStatement = connection.prepareStatement("SELECT t1.c, t2.pad FROM sbtest1 t1 JOIN sbtest2 t2 ON t1.k = t2.k;");
    }
    
    @Override
    public void execute() throws SQLException {
        joinSelectStatement.execute();
    }
}