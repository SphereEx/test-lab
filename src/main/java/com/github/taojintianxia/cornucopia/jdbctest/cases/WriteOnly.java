package com.github.taojintianxia.cornucopia.jdbctest.cases;

import com.github.taojintianxia.cornucopia.jdbctest.constants.SysbenchConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class WriteOnly implements SysbenchBenchmark {

    private final Connection connection;
    
    private final PreparedStatement updateIndexStatement;
    
    private final PreparedStatement updateNonIndexStatement;
    
    private final PreparedStatement deleteStatement;
    
    private final PreparedStatement insertStatement;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public WriteOnly(Connection connection ) throws SQLException {
        this.connection = connection;
        updateIndexStatement = connection.prepareStatement("UPDATE sbtest1 SET k=k+1 WHERE id=?");
        updateNonIndexStatement = connection.prepareStatement("UPDATE sbtest1 SET c=? WHERE id=?");
        deleteStatement = connection.prepareStatement("DELETE FROM sbtest1 WHERE id=?");
        insertStatement = connection.prepareStatement("INSERT INTO sbtest1 (id, k, c, pad) VALUES (?, ?, ?, ?)");    }

    @Override
    public void execute() throws SQLException {
        connection.setAutoCommit(false);
        int randomId = ThreadLocalRandom.current().nextInt(SysbenchConstant.tableSize);
        updateIndexStatement.setInt(1, randomId);
        updateIndexStatement.execute();
        updateNonIndexStatement.setString(1, String.valueOf(randomId));
        updateNonIndexStatement.setInt(2, randomId);
        updateNonIndexStatement.execute();
        deleteStatement.setInt(1, randomId);
        deleteStatement.execute();
        insertStatement.setInt(1, randomId);
        insertStatement.setInt(2, ThreadLocalRandom.current().nextInt());
        insertStatement.setString(3, String.valueOf(randomId));
        insertStatement.setString(4, String.valueOf(randomId));
        insertStatement.execute();
        connection.commit();
    }
}
