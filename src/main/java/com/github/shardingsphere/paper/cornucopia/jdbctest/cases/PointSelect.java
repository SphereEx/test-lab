package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.SysbenchConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class PointSelect implements SysbenchBenchmark {
    
    private final PreparedStatement[] pointSelectStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public PointSelect( Connection connection ) throws SQLException {
        pointSelectStatements = new PreparedStatement[SysbenchConstant.tables];
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            pointSelectStatements[i] = connection.prepareStatement("SELECT c FROM sbtest" +(i+1)+" WHERE id = ?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(SysbenchConstant.tables);
        pointSelectStatements[i].setInt(1, random.nextInt(SysbenchConstant.tableSize));
        pointSelectStatements[i].execute();
    }
}
