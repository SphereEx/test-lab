
deploy script

```shell
docker run -d \
-p 2379:2379 \
-p 2380:2380 \
--name etcd-gcr-v3.4.0 \
quay.io/coreos/etcd:v3.4.0 \
/usr/local/bin/etcd \
--name s1 \
--data-dir /etcd-data \
--listen-client-urls http://0.0.0.0:2379 \
--advertise-client-urls http://0.0.0.0:2379 \
--listen-peer-urls http://0.0.0.0:2380 \
--initial-advertise-peer-urls http://0.0.0.0:2380 \
--initial-cluster s1=http://0.0.0.0:2380 \
--initial-cluster-token tkn \
--initial-cluster-state new \
--log-level info \
--logger zap \
--log-outputs stderr



vtctld -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -port=15000 \
  -grpc_port=15999 \
  -service_map='grpc-vtctl' \
  -pid_file $VTDATAROOT/tmp/vtctld.pid \
  > $VTDATAROOT/tmp/vtctld.out 2>&1 &


vtctlclient AddCellInfo \
  -root /vitess/cell1 \
  -server_address $TOPO_SERVER \
  cell1


mysqlctl \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet_uid=500 \
  -mysql_port=17100 \
  init





vttablet -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet-path=cell1-100 \
  -tablet_hostname=node01 \
  -init_keyspace=dbtpcc \
  -init_shard=-3333 \
  -init_tablet_type=replica \
  -port=15100 \
  -grpc_port=16100 \
  -service_map 'grpc-queryservice,grpc-tabletmanager' \
  -enable_semi_sync=false \
  -enable_replication_reporter=false \
  -restore_from_backup=false \
  -queryserver-config-pool-size=16 \
  -queryserver-config-transaction-cap=300 \
  -queryserver-config-stream-pool-size=16 \
  -queryserver-config-transaction-timeout=50 \
 -pid_file $VTDATAROOT/tmp/vttablet.pid \
 > $VTDATAROOT/tmp/vttablet.out 2>&1 &


vttablet -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet-path=cell1-200 \
  -tablet_hostname=node02 \
  -init_keyspace=dbtpcc \
  -init_shard=3333-6666 \
  -init_tablet_type=replica \
  -port=15100 \
  -grpc_port=16100 \
  -service_map 'grpc-queryservice,grpc-tabletmanager' \
  -enable_semi_sync=false \
  -enable_replication_reporter=false \
  -restore_from_backup=false \
  -queryserver-config-pool-size=16 \
  -queryserver-config-transaction-cap=300 \
  -queryserver-config-stream-pool-size=16 \
    -queryserver-config-transaction-timeout=50 \
 -pid_file $VTDATAROOT/tmp/vttablet.pid \
 > $VTDATAROOT/tmp/vttablet.out 2>&1 &

vttablet -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet-path=cell1-300 \
  -tablet_hostname=node03 \
  -init_keyspace=dbtpcc \
  -init_shard=6666-9999 \
  -init_tablet_type=replica \
  -port=15100 \
  -grpc_port=16100 \
  -service_map 'grpc-queryservice,grpc-tabletmanager' \
  -enable_semi_sync=false \
  -enable_replication_reporter=false \
  -restore_from_backup=false \
  -queryserver-config-pool-size=16 \
  -queryserver-config-transaction-cap=300 \
  -queryserver-config-stream-pool-size=16 \
      -queryserver-config-transaction-timeout=50 \
 -pid_file $VTDATAROOT/tmp/vttablet.pid \
 > $VTDATAROOT/tmp/vttablet.out 2>&1 &

vttablet -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet-path=cell1-400 \
  -tablet_hostname=node04 \
  -init_keyspace=dbtpcc \
  -init_shard=9999-cccc \
  -init_tablet_type=replica \
  -port=15100 \
  -grpc_port=16100 \
  -service_map 'grpc-queryservice,grpc-tabletmanager' \
  -enable_semi_sync=false \
  -enable_replication_reporter=false \
  -restore_from_backup=false \
  -queryserver-config-pool-size=16 \
  -queryserver-config-transaction-cap=300 \
  -queryserver-config-stream-pool-size=16 \
      -queryserver-config-transaction-timeout=50 \
 -pid_file $VTDATAROOT/tmp/vttablet.pid \
 > $VTDATAROOT/tmp/vttablet.out 2>&1 &

vttablet -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -tablet-path=cell1-500 \
  -tablet_hostname=node05 \
  -init_keyspace=dbtpcc \
  -init_shard=cccc- \
  -init_tablet_type=replica \
  -port=15100 \
  -grpc_port=16100 \
  -service_map 'grpc-queryservice,grpc-tabletmanager' \
  -enable_semi_sync=false \
  -enable_replication_reporter=false \
  -restore_from_backup=false \
  -queryserver-config-pool-size=16 \
  -queryserver-config-transaction-cap=300 \
  -queryserver-config-stream-pool-size=16 \
      -queryserver-config-transaction-timeout=50 \
 -pid_file $VTDATAROOT/tmp/vttablet.pid \
 > $VTDATAROOT/tmp/vttablet.out 2>&1 & 


vtctlclient InitShardPrimary -force dbtpcc/-3333 cell1-100
vtctlclient InitShardPrimary -force dbtpcc/3333-6666 cell1-200
vtctlclient InitShardPrimary -force dbtpcc/6666-9999 cell1-300
vtctlclient InitShardPrimary -force dbtpcc/9999-cccc cell1-400
vtctlclient InitShardPrimary -force dbtpcc/cccc- cell1-500


vtgate -topo_implementation=etcd2 -topo_global_server_address=$TOPO_SERVER -topo_global_root=/vitess/global \
  -log_dir=${VTDATAROOT}/tmp \
  -cell=cell1 \
  -cells_to_watch=cell1 \
  -port=15001 \
  -mysql_server_port=15306 \
  -tablet_types_to_wait PRIMARY,REPLICA \
  -mysql_auth_server_impl=static \
  -mysql_auth_server_static_file=mysql_creds.json \
  -grpc_port=15991 \
  -service_map='grpc-vtgateservice' \
  -vschema_ddl_authorized_users='%' \
  -pid_file $VTDATAROOT/tmp/vtgate.pid \
   > $VTDATAROOT/tmp/vtgate.out 2>&1 &


mysql -h 127.0.0.1 -P 15306 -utest -pVitessDB2021

vtctlclient ApplyVSchema -vschema_file /extra/server/vitess/vschema_tpcc.json dbtpcc
{
  "sharded": true,
  "vindexes": {
    "hash": {
      "type": "hash"
    },
    "look": {
      "type": "null"
    }
  },
  "tables": {
    "bmsql_config": {
      "columnVindexes": [
        {
          "column": "cfg_name",
          "name": "look"
        }
      ]
    },
    "bmsql_customer": {
      "columnVindexes": [
        {
          "column": "c_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_district": {
      "columnVindexes": [
        {
          "column": "d_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_history": {
      "columnVindexes": [
        {
          "column": "h_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_item": {
      "columnVindexes": [
        {
          "column": "i_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_new_order": {
      "columnVindexes": [
        {
          "column": "no_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_oorder": {
      "columnVindexes": [
        {
          "column": "o_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_order_line": {
      "columnVindexes": [
        {
          "column": "ol_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_stock": {
      "columnVindexes": [
        {
          "column": "s_w_id",
          "name": "hash"
        }
      ]
    },
    "bmsql_warehouse": {
      "columnVindexes": [
        {
          "column": "w_id",
          "name": "hash"
        }
      ]
    },
    "order_line": {
      "columnVindexes": [
        {
          "column": "ol_w_id",
          "name": "hash"
        }
      ]
    }
  }
}
```


