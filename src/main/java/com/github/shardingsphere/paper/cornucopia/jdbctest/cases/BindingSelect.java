package com.github.taojintianxia.cornucopia.jdbctest.cases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class BindingSelect implements SysbenchBenchmark{

    private final Connection connection;

    private final PreparedStatement bindingSelectStatement;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public BindingSelect(Connection connection) throws SQLException {
        this.connection = connection;
        bindingSelectStatement = connection.prepareStatement("SELECT t1.c, t2.pad FROM sbtest1 t1 JOIN sbtest2 t2 ON t1.k = t2.k WHERE t1.k > 10000000 AND t1.k < 10001000;");
    }
    
    @Override
    public void execute() throws SQLException {
        bindingSelectStatement.execute();
    }
}
