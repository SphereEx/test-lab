# BenchmarkSQL

followings are the benchmarksql test configuration

1.MySQL test
first, create a test config file `prop.mysql` for MySQL like followings

```
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

2.PostgreSQL test
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
