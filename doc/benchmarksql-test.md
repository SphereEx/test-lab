# BenchmarkSQL

followings are the benchmarksql test configuration

## 1.MySQL test
first, create a test config file `prop.mysql` for MySQL like followings

```
# use pg type to skip benchmarksql database type check
db=postgres
driver=com.mysql.jdbc.Driver
conn=jdbc:mysql://10.0.0.1:3306/tpcc_mysql?useSSL=false
user=root
password=YOUR_PASSWORD_FOR_DATABASE

warehouses=200
loadWorkers=200

terminals=200
//To run specified transactions per terminal- runMins must equal zero
runTxnsPerTerminal=0
//To run for specified minutes- runTxnsPerTerminal must equal zero
runMins=10
//Number of total transactions per minute
limitTxnsPerMin=0

//Set to true to run in 4.x compatible mode. Set to false to use the
//entire configured database evenly.
terminalWarehouseFixed=true

//The following five values must add up to 100
//The default percentages of 45, 43, 4, 4 & 4 match the TPC-C spec
newOrderWeight=45
paymentWeight=43
orderStatusWeight=4
deliveryWeight=4
stockLevelWeight=4
```
and then executing the following script in order :

```
./runDatabaseDestroy.sh prop.mysql
./runDatabaseCreateBuild.sh prop.mysql
./runDatabaseBuild.sh prop.mysql
```

## 2.PostgreSQL test
first, create a test config file `prop.pgsql` for PostgreSQL like followings

```
db=postgres
driver=org.postgresql.Driver
conn=jdbc:postgresql://10.0.0.1:5432/tpcc_pgsql
user=test
password=YOUR_PASSWORD_FOR_DATABASE

warehouses=200
loadWorkers=200

terminals=200
//To run specified transactions per terminal- runMins must equal zero
runTxnsPerTerminal=0
//To run for specified minutes- runTxnsPerTerminal must equal zero
runMins=10
//Number of total transactions per minute
limitTxnsPerMin=0

//Set to true to run in 4.x compatible mode. Set to false to use the
//entire configured database evenly.
terminalWarehouseFixed=true

//The following five values must add up to 100
//The default percentages of 45, 43, 4, 4 & 4 match the TPC-C spec
newOrderWeight=45
paymentWeight=43
orderStatusWeight=4
deliveryWeight=4
stockLevelWeight=4
```
and then executing the following script in order :

```
./runDatabaseDestroy.sh prop.pgsql
./runDatabaseCreateBuild.sh prop.pgsql
./runDatabaseBuild.sh prop.pgsql
```

## 3.ShardingSphere JDBC + MySQL
first, create a test config file `prop.jdbc-mysql` for MySQL like followings

```
# use pg type to skip benchmarksql database type check
db=postgres
driver=com.mysql.jdbc.Driver
conn=jdbc:mysql://10.0.0.100:3307/tpcc_ssj
user=root
password=YOUR_PASSWORD
ssJdbcYamlLocation=/opt/tpcc/test-lab/run/config-sharding-mysql.yaml

warehouses=200
loadWorkers=200

terminals=200
//To run specified transactions per terminal- runMins must equal zero
runTxnsPerTerminal=0
//To run for specified minutes- runTxnsPerTerminal must equal zero
runMins=10
//Number of total transactions per minute
limitTxnsPerMin=0

//Set to true to run in 4.x compatible mode. Set to false to use the
//entire configured database evenly.
terminalWarehouseFixed=true

//The following five values must add up to 100
//The default percentages of 45, 43, 4, 4 & 4 match the TPC-C spec
newOrderWeight=45
paymentWeight=43
orderStatusWeight=4
deliveryWeight=4
stockLevelWeight=4
```

and then create a yaml file `config-sharding-mysql.yaml` for ShardingSphere JDBC like following : 

```
schemaName: tpcc
dataSources:
  ds_0:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:mysql://10.0.0.1:3306/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_1:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:mysql://10.0.0.2:3306/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_2:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:mysql://10.0.0.3:3306/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_3:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:mysql://10.0.0.4:3306/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_4:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:mysql://10.0.0.5:3306/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1

rules:
  - !SHARDING
    bindingTables:
      - bmsql_warehouse, bmsql_customer
      - bmsql_stock, bmsql_district, bmsql_order_line
    defaultDatabaseStrategy:
      none:
    defaultTableStrategy:
      none:
    keyGenerators:
      snowflake:
        props:
          worker-id: 123
        type: SNOWFLAKE
    tables:
      bmsql_config:
        actualDataNodes: ds_0.bmsql_config

      bmsql_warehouse:
        actualDataNodes: ds_${0..4}.bmsql_warehouse
        databaseStrategy:
          standard:
            shardingColumn: w_id
            shardingAlgorithmName: bmsql_warehouse_database_inline

      bmsql_district:
        actualDataNodes: ds_${0..4}.bmsql_district
        databaseStrategy:
          standard:
            shardingColumn: d_w_id
            shardingAlgorithmName: bmsql_district_database_inline

      bmsql_customer:
        actualDataNodes: ds_${0..4}.bmsql_customer
        databaseStrategy:
          standard:
            shardingColumn: c_w_id
            shardingAlgorithmName: bmsql_customer_database_inline

      bmsql_item:
        actualDataNodes: ds_${0..4}.bmsql_item
        databaseStrategy:
          standard:
            shardingColumn: i_id
            shardingAlgorithmName: bmsql_item_database_inline

      bmsql_history:
        actualDataNodes: ds_${0..4}.bmsql_history
        databaseStrategy:
          standard:
            shardingColumn: h_w_id
            shardingAlgorithmName: bmsql_history_database_inline

      bmsql_oorder:
        actualDataNodes: ds_${0..4}.bmsql_oorder
        databaseStrategy:
          standard:
            shardingColumn: o_w_id
            shardingAlgorithmName: bmsql_oorder_database_inline

      bmsql_stock:
        actualDataNodes: ds_${0..4}.bmsql_stock
        databaseStrategy:
          standard:
            shardingColumn: s_w_id
            shardingAlgorithmName: bmsql_stock_database_inline

      bmsql_new_order:
        actualDataNodes: ds_${0..4}.bmsql_new_order
        databaseStrategy:
          standard:
            shardingColumn: no_w_id
            shardingAlgorithmName: bmsql_new_order_database_inline

      bmsql_order_line:
        actualDataNodes: ds_${0..4}.bmsql_order_line
        databaseStrategy:
          standard:
            shardingColumn: ol_w_id
            shardingAlgorithmName: bmsql_order_line_database_inline

    shardingAlgorithms:
      bmsql_warehouse_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${w_id % 5}

      bmsql_district_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${d_w_id % 5}

      bmsql_customer_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${c_w_id % 5}

      bmsql_item_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${i_id % 5}

      bmsql_history_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${h_w_id % 5}

      bmsql_oorder_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${o_w_id % 5}

      bmsql_stock_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${s_w_id % 5}

      bmsql_new_order_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${no_w_id % 5}

      bmsql_order_line_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${ol_w_id % 5}
```

and then executing the following script in order :

```
./runDatabaseDestroy.sh prop.jdbc-mysql
./runDatabaseCreateBuild.sh prop.jdbc-mysql
./runDatabaseBuild.sh prop.jdbc-mysql
```

## 4.ShardingSphere JDBC + PostgreSQL
first, create a test config file `prop.jdbc-pgsql` for PostgreSQL like followings

```
# use pg type to skip benchmarksql database type check
db=postgres
driver=org.postgresql.Driver
conn=jdbc:postgresql://10.0.0.100:3307/tpcc_ssj
user=root
password=YOUR_PASSWORD
ssJdbcYamlLocation=/opt/tpcc/test-lab/run/config-sharding-pgsql.yaml

warehouses=200
loadWorkers=200

terminals=200
//To run specified transactions per terminal- runMins must equal zero
runTxnsPerTerminal=0
//To run for specified minutes- runTxnsPerTerminal must equal zero
runMins=10
//Number of total transactions per minute
limitTxnsPerMin=0

//Set to true to run in 4.x compatible mode. Set to false to use the
//entire configured database evenly.
terminalWarehouseFixed=true

//The following five values must add up to 100
//The default percentages of 45, 43, 4, 4 & 4 match the TPC-C spec
newOrderWeight=45
paymentWeight=43
orderStatusWeight=4
deliveryWeight=4
stockLevelWeight=4
```

and then create a yaml file `config-sharding-pgsql.yaml` for ShardingSphere JDBC like following : 

```
schemaName: tpcc
dataSources:
  ds_0:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:postgresql://10.0.0.1:5432/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_1:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:postgresql://10.0.0.2:5432/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_2:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:postgresql://10.0.0.3:5432/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_3:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:postgresql://10.0.0.4:5432/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1
  ds_4:
    dataSourceClassName: com.zaxxer.hikari.HikariDataSource
    jdbcUrl: jdbc:postgresql://10.0.0.5:5432/tpcc
    username: root
    password: YOUR_DB_PASSWORD
    connectionTimeoutMilliseconds: 10000
    idleTimeoutMilliseconds: 60000
    maxLifetimeMilliseconds: 1800000
    maxPoolSize: 256
    minPoolSize: 1

rules:
  - !SHARDING
    bindingTables:
      - bmsql_warehouse, bmsql_customer
      - bmsql_stock, bmsql_district, bmsql_order_line
    defaultDatabaseStrategy:
      none:
    defaultTableStrategy:
      none:
    keyGenerators:
      snowflake:
        props:
          worker-id: 123
        type: SNOWFLAKE
    tables:
      bmsql_config:
        actualDataNodes: ds_0.bmsql_config

      bmsql_warehouse:
        actualDataNodes: ds_${0..4}.bmsql_warehouse
        databaseStrategy:
          standard:
            shardingColumn: w_id
            shardingAlgorithmName: bmsql_warehouse_database_inline

      bmsql_district:
        actualDataNodes: ds_${0..4}.bmsql_district
        databaseStrategy:
          standard:
            shardingColumn: d_w_id
            shardingAlgorithmName: bmsql_district_database_inline

      bmsql_customer:
        actualDataNodes: ds_${0..4}.bmsql_customer
        databaseStrategy:
          standard:
            shardingColumn: c_w_id
            shardingAlgorithmName: bmsql_customer_database_inline

      bmsql_item:
        actualDataNodes: ds_${0..4}.bmsql_item
        databaseStrategy:
          standard:
            shardingColumn: i_id
            shardingAlgorithmName: bmsql_item_database_inline

      bmsql_history:
        actualDataNodes: ds_${0..4}.bmsql_history
        databaseStrategy:
          standard:
            shardingColumn: h_w_id
            shardingAlgorithmName: bmsql_history_database_inline

      bmsql_oorder:
        actualDataNodes: ds_${0..4}.bmsql_oorder
        databaseStrategy:
          standard:
            shardingColumn: o_w_id
            shardingAlgorithmName: bmsql_oorder_database_inline

      bmsql_stock:
        actualDataNodes: ds_${0..4}.bmsql_stock
        databaseStrategy:
          standard:
            shardingColumn: s_w_id
            shardingAlgorithmName: bmsql_stock_database_inline

      bmsql_new_order:
        actualDataNodes: ds_${0..4}.bmsql_new_order
        databaseStrategy:
          standard:
            shardingColumn: no_w_id
            shardingAlgorithmName: bmsql_new_order_database_inline

      bmsql_order_line:
        actualDataNodes: ds_${0..4}.bmsql_order_line
        databaseStrategy:
          standard:
            shardingColumn: ol_w_id
            shardingAlgorithmName: bmsql_order_line_database_inline

    shardingAlgorithms:
      bmsql_warehouse_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${w_id % 5}

      bmsql_district_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${d_w_id % 5}

      bmsql_customer_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${c_w_id % 5}

      bmsql_item_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${i_id % 5}

      bmsql_history_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${h_w_id % 5}

      bmsql_oorder_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${o_w_id % 5}

      bmsql_stock_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${s_w_id % 5}

      bmsql_new_order_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${no_w_id % 5}

      bmsql_order_line_database_inline:
        type: INLINE
        props:
          algorithm-expression: ds_${ol_w_id % 5}
```

and then executing the following script in order :

```
./runDatabaseDestroy.sh prop.jdbc-pgsql
./runDatabaseCreateBuild.sh prop.jdbc-pgsql
./runDatabaseBuild.sh prop.jdbc-pgsql
```
