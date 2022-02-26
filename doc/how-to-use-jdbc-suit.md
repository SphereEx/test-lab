# How to use JDBC test suit

since sysbench is coded by lua+c , it could not test the ShardingSphere-JDBC directly

and the tester code a Java version sysbench and following is the usage of this test suit:

there are some parameters have to specify for this tool:

conf : the path of ShardingSphere JDBC configuration
time : the executing time for test
thread : concurrent thread for test
script : same script name as sysbench, like oltp_point_select etc.
table-size : the record for every table
tables : how many tables to switch
percentile : percentile for response time, default is 99

jdbc-type : choose the datasource, jdbc or ss-jdbc
username : when test jdbc, the username of database
password : when test jdbc, the password of database
jdbc-url : when test jdbc, the jdbcUrl of database

1. execute maven command to create a fat jar for test suit like :
```shell
mvn clean install
```

2. copy all content under target to the machine you want to make stress test(all dependencies are in lib)

3. launch the test suit like following :

```shell
java -Dconf=/opt/test/sharding-databases-tables.yaml -Dtime=10 -Dthread=2 -Dscript=oltp_read_write -Dtable-size=1000 -Dtables=10 -Dpercentile=99 -jar jdbc-pressure-test-1.0.0-SNAPSHOT.jar 
```

if test merge sort, like followings :
```shell
java -Dconf=/opt/test/sharding-databases-tables.yaml -Dtime=60 -Dthread=1 -Dscript=oltp_merge_sort_range -Dtable-size=10000000 -Dtables=1   -Dpercentile=99 -Dmerge-sort-from=1 -Dmerge-sort-to=100 -jar jdbc-pressure-test-1.0.0-SNAPSHOT.jar

```
if test jdbc, like followings :
```shell
java -Djdbc-type=jdbc -Dusername=root -Dpassword=YOUR_PASSWORD -Djdbc-url="jdbc:mysql://127.0.0.1:3306/sbtest?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=UTF-8" -Dtables=5 -Dtime=10 -Dthread=2 -Dscript=oltp_read_write -Dtable-size=1000 -Dpercentile=90 -jar target/jdbc-pressure-test-1.0.0-SNAPSHOT.jar
```
