package com.github.taojintianxia.cornucopia.jdbctest.cases;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class IndexUpdates implements SysbenchBenchmark {
    
    private final PreparedStatement[] indexUpdatesStatements;

    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public IndexUpdates(Connection connection ) throws SQLException {
        indexUpdatesStatements = new PreparedStatement[SysbenchConstant.tables];
        for (int i = 0; i < SysbenchConstant.tables; i++) {
            indexUpdatesStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET k=k+1 WHERE id=?");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(SysbenchConstant.tables);
        indexUpdatesStatements[i].setInt(1, random.nextInt(SysbenchConstant.tableSize));
        indexUpdatesStatements[i].execute();
    }
}
