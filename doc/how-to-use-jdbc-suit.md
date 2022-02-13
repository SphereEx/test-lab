# How to use JDBC test suit

since sysbench is coded by lua+c , it could not test the ShardingSphere-JDBC directly

and the tester code a Java version sysbench and following is the usage of this test suit:

there are some parameters have to specify for this tool:

conf : the path of ShardingSphere JDBC configuration
time : the executing time for test
thread : concurrent thread for test
script : same script name as sysbench, like oltp_point_select etc.
table-size : the record for every table
percentile : percentile for response time, default is 99

1. execute maven command to create a fat jar for test suit like :
```shell
mvn clean install
```

2. copy all content under target to the machine you want to make stress test(all dependencies are in lib)

3. launch the test suit like following :

```shell
java -Dconf=/opt/test/sharding-databases-tables.yaml -Dtime=10 -Dthread=2 -Dscript=oltp_read_write -Dtable-size=1000 -Dpercentile=90 -jar jdbc-pressure-test-1.0.0-SNAPSHOT.jar 
```
