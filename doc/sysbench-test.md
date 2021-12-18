# sysbench

followings are the sysbench related test script

1.scenario test

```
# init the database
DB_TYPE=SSP
DATABASE=sbtest_sharding
TABLES=1
HOST=10.0.0.1
PORT=3307
PASSWORD="YOUR_PASSWORD"
TABLE_SIZE=40000000
TIME=300
THREADS=200


sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off cleanup
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=3600 --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off prepare

# for warm up
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run

sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_read_only.${DB_TYPE}.txt
sysbench oltp_point_select --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_point_select.${DB_TYPE}.txt
sysbench oltp_read_write --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_read_write.${DB_TYPE}.txt
sysbench oltp_write_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_write_only.${DB_TYPE}.txt
sysbench oltp_update_index --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_update_index.${DB_TYPE}.txt
sysbench oltp_update_non_index --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_update_non_index.${DB_TYPE}.txt
sysbench oltp_delete --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_delete.${DB_TYPE}.txt
```

2.amout of data test

in this scenario, change the `TABLE_SIZE` to 20000000,40000000,60000000,80000000 and 100000000 for every single test

```
# init the database
DB_TYPE=SSP
DATABASE=sbtest_sharding
TABLES=1
HOST=10.0.0.1
PORT=3307
PASSWORD="YOUR_PASSWORD"
TABLE_SIZE=20000000
TIME=300
THREADS=200


sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off cleanup
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=3600 --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off prepare

# for warm up
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run

sysbench oltp_read_write --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_read_write.${DB_TYPE}.txt
``` 

3.concurrency test

in this scenario, change the `THREADS` to 1,20,100,200,500 and 1000 for every single test.Init data one time is enough.

```
# init the database
DB_TYPE=SSP
DATABASE=sbtest_sharding
TABLES=1
HOST=10.0.0.1
PORT=3307
PASSWORD="YOUR_PASSWORD"
TABLE_SIZE=40000000
TIME=300
THREADS=1


sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off cleanup
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=3600 --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off prepare

# for warm up
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run

sysbench oltp_read_write --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_read_write.${DB_TYPE}.txt
``` 

4.data node test

in this scenario, change the sharding data node of testing target to 1,3,5,7,9 and 11 for every single test. 

```
# init the database
DB_TYPE=SSP
DATABASE=sbtest_sharding
TABLES=1
HOST=10.0.0.1
PORT=3307
PASSWORD="YOUR_PASSWORD"
TABLE_SIZE=40000000
TIME=300
THREADS=200


sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off cleanup
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=3600 --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off prepare

# for warm up
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run

sysbench oltp_read_write --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run | tee oltp_read_write.${DB_TYPE}.txt
``` 

5. merge sorting test

first of all, change the lua script of sysbench oltp_read_only to `SELECT c FROM sbtest1 WHERE ? > 0 AND id BETWEEN 1 AND 100` and then test by following script

```
# init the database
DB_TYPE=SSP
DATABASE=sbtest_sharding
TABLES=1
HOST=10.0.0.1
PORT=3307
PASSWORD="YOUR_PASSWORD"
TABLE_SIZE=10000000
TIME=300
THREADS=1


sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=100 --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off cleanup
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=3600 --threads=100 --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off prepare

# for warm up
sysbench oltp_read_only --mysql-host=${HOST} --mysql-port=${PORT} --mysql-user=root --mysql-password=${PASSWORD} --mysql-db=${DATABASE} --tables=${TABLES} --table-size=${TABLE_SIZE} --report-interval=5 --time=${TIME} --threads=${THREADS} --max-requests=0 --percentile=99 --mysql-ignore-errors="all" --range_selects=off --rand-type=uniform --auto_inc=off run
```
