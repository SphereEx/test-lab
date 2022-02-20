package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import java.sql.SQLException;

public interface SysbenchBenchmark {

    void execute() throws SQLException;
}
