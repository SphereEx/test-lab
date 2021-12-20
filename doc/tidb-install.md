# TiDB Install Config
```yaml
global:
  user: "root"
  ssh_port: 22
  deploy_dir: "/extra/tidb-deploy-cluster"
  data_dir: "/extra/tidb-data-cluster"
server_configs: {}
pd_servers:
  - host: 10.0.0.224
  - host: 10.0.0.183
  - host: 10.0.0.241
tidb_servers:
  - host: 10.0.0.168
tikv_servers:
  - host: 10.0.0.188
  - host: 10.0.0.241
  - host: 10.0.0.183
  - host: 10.0.0.224
  - host: 10.0.0.200

monitoring_servers:
  - host: 10.0.0.224
grafana_servers:
  - host: 10.0.0.224
```