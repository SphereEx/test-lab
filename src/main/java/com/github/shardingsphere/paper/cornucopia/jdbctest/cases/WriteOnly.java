package com.github.shardingsphere.paper.cornucopia.jdbctest.cases;

import com.github.shardingsphere.paper.cornucopia.jdbctest.constants.BenchmarkEnvConstant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ThreadLocalRandom;

public class WriteOnly implements SysbenchBenchmark {

    private final Connection connection;
    
    private final PreparedStatement[] updateIndexStatements;
    
    private final PreparedStatement[] updateNonIndexStatements;
    
    private final PreparedStatement[] deleteStatements;
    
    private final PreparedStatement[] insertStatements;
    
    private final ThreadLocalRandom random = ThreadLocalRandom.current();

    public WriteOnly(Connection connection ) throws SQLException {
        this.connection = connection;
        updateIndexStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        updateNonIndexStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        deleteStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        insertStatements = new PreparedStatement[BenchmarkEnvConstant.tables];
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            updateIndexStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET k=k+1 WHERE id=?");
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            updateNonIndexStatements[i] = connection.prepareStatement("UPDATE sbtest" +(i+1)+" SET c=? WHERE id=?");
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            deleteStatements[i] = connection.prepareStatement("DELETE FROM sbtest" +(i+1)+" WHERE id=?");
        }
        for (int i = 0; i < BenchmarkEnvConstant.tables; i++) {
            insertStatements[i] = connection.prepareStatement("INSERT INTO sbtest" +(i+1)+"  (id, k, c, pad) VALUES (?, ?, ?, ?)");
        }
    }

    @Override
    public void execute() throws SQLException {
        int i = random.nextInt(BenchmarkEnvConstant.tables);
        connection.setAutoCommit(false);
        int randomId = ThreadLocalRandom.current().nextInt(BenchmarkEnvConstant.tableSize);
        updateIndexStatements[i].setInt(1, randomId);
        updateIndexStatements[i].execute();
        updateNonIndexStatements[i].setString(1, String.valueOf(randomId));
        updateNonIndexStatements[i].setInt(2, randomId);
        updateNonIndexStatements[i].execute();
        deleteStatements[i].setInt(1, randomId);
        deleteStatements[i].execute();
        insertStatements[i].setInt(1, randomId);
        insertStatements[i].setInt(2, ThreadLocalRandom.current().nextInt());
        insertStatements[i].setString(3, String.valueOf(randomId));
        insertStatements[i].setString(4, String.valueOf(randomId));
        insertStatements[i].execute();
        connection.commit();
    }
}
