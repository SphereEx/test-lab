package com.github.taojintianxia.cornucopia.jdbctest.factory;

import com.github.taojintianxia.cornucopia.jdbctest.cases.Deletes;
import com.github.taojintianxia.cornucopia.jdbctest.cases.IndexUpdates;
import com.github.taojintianxia.cornucopia.jdbctest.cases.NonIndexUpdates;
import com.github.taojintianxia.cornucopia.jdbctest.cases.PointSelect;
import com.github.taojintianxia.cornucopia.jdbctest.cases.PointSelectOrderBy;
import com.github.taojintianxia.cornucopia.jdbctest.cases.ReadOnly;
import com.github.taojintianxia.cornucopia.jdbctest.cases.ReadWrite;
import com.github.taojintianxia.cornucopia.jdbctest.cases.SysbenchBenchmark;
import com.github.taojintianxia.cornucopia.jdbctest.cases.WriteOnly;

import java.sql.Connection;
import java.sql.SQLException;

public class BenchmarkFactory {

    public static SysbenchBenchmark getBenchmarkByName( String benchmarkName, Connection connection ) throws SQLException {
        if ("oltp_read_write".equals(benchmarkName)) {
            return new ReadWrite(connection);
        }
        if ("oltp_read_only".equals(benchmarkName)) {
            return new ReadOnly(connection);
        }
        if ("oltp_point_select".equals(benchmarkName)) {
            return new PointSelect(connection);
        }
        if ("oltp_order_by".equals(benchmarkName)) {
            return new PointSelectOrderBy(connection);
        }
        if ("oltp_update_index".equals(benchmarkName)) {
            return new IndexUpdates(connection);
        }
        if ("oltp_update_non_index".equals(benchmarkName)) {
            return new NonIndexUpdates(connection);
        }
        if ("oltp_write_only".equals(benchmarkName)) {
            return new WriteOnly(connection);
        }
        if ("oltp_delete".equals(benchmarkName)) {
            return new Deletes(connection);
        }

        throw new UnsupportedOperationException("not support your benchmark");
    }
}
