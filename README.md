# ICDE Test

## Stress Testing Tool
The stress test for ICDE by two authoritative database testing tools， sysbench and BenchmarkSQL

followings are the documents for the script and configuration of these tools

[sysbench script](doc/sysbench-test.md)

[BenchmarkSQL configuration](doc/benchmarksql-test.md)

### TiDB Installation
[TiDB Install Config](doc/tidb-install.md)


## Usage of test suit tool
[Test Suit Usage](doc/how-to-use-jdbc-suit.md)


## Optimization
Linux MultiQueue Networking technology will optimize the stress test tool, you could enable that by following steps:
1. download the script : 
```shell
wget https://ecs-instance-driver.obs.cn-north-1.myhuaweicloud.com/multi-queue-hw 
```

2. add permission for this script :
```shell
chmod +x multi-queue-hw
```

3. start the script:
```shell
./multi-queue-hw start
```

after the script executes, this will take effect immediately.  
but remember, this will be disabled after linux system reboot. Please execute this script again after system rebooted.
