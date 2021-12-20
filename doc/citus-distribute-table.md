
TPCC

```sql
SELECT create_distributed_table('bmsql_customer', 'c_w_id');
SELECT create_distributed_table('bmsql_district', 'd_w_id');
SELECT create_distributed_table('bmsql_history', 'h_w_id');
SELECT create_distributed_table('bmsql_warehouse', 'w_id');
SELECT create_distributed_table('bmsql_stock', 's_w_id');
SELECT create_distributed_table('bmsql_new_order', 'no_w_id');
SELECT create_distributed_table('bmsql_orders', 'o_w_id');
SELECT create_distributed_table('bmsql_order_line', 'ol_w_id');
SELECT create_reference_table('bmsql_item');
```
