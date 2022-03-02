# ICDE Test

## Stress Testing Tool
The stress test for ICDE to be performed by two authoritative database testing tools, sysbench and BenchmarkSQL

The following are the documents for the tools' script and configuration:

[sysbench script](doc/sysbench-test.md)

[BenchmarkSQL configuration](doc/benchmarksql-test.md)

### TiDB Installation
[TiDB Install Config](doc/tidb-install.md)


## Usage of the test suit tool
[Test Suit Usage](doc/how-to-use-jdbc-suit.md)


## Optimization
Linux MultiQueue Networking technology will optimize the stress test tool, you could enable that with the following steps:
1. Download the script: 
```shell
wget https://ecs-instance-driver.obs.cn-north-1.myhuaweicloud.com/multi-queue-hw 
```

2. Add permissions for this script :
```shell
chmod +x multi-queue-hw
```

3. Start the script:
```shell
./multi-queue-hw start
```

After the script executes, this will take effect immediately.  
Please note, this will be disabled after a Linux system reboot. Please execute this script again after system is rebooted.
