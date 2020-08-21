Rabbitmq默认端口: 15672

rabbitmqctl stop_app： 关闭应用
rabbitmqctl start_app： 启动应用
rabbitmqctl status: 节点状态
rabbitmqctl add_user username password: 添加用户
rabbitmqctl list_users： 列出所有用户
rabbitmqctl delete_user username： 删除用户
rabbitmqctl clear_permissions -p vhostpath username: 清除用户权限
rabbitmqctl list_user_permissions username： 列出用户权限
rabbitmqctl change_password username newpassword：修改密码
rabbitmqctl set_permissions -p vhostpath username ".*" ".*" ".*" 设置用户权限
rabbitmqctl add_vhost vhostpath: 创建虚拟主机
rabbitmqctl list_vhosts: 列出所有虚拟主机
rabbitmqctl list_permissions -p vhostpath: 列出虚拟主机上所有权限
rabbitmqctl delete_vhost vhostpath: 删除虚拟主机
rabbitmqctl list_queues： 查看所有队列信息
rabbitmqctl -p vhostpath purge_queue blue: 清除队列里的消息

rabbitmq高级操作
rabbitmqctl reset: 移除所有数据, 要在rabbitmqctl stop_app之后使用
rabbitmqctl join_cluster <clusternode>[--ram]: 组成集群命令
rabbitmqctl cluster_status: 查看集群状态
rabbitmqctl change_cluster_node_type disc | ram: 修改集群节点的存储形式
rabbitmqctl forget_cluster_node[--offline]忘记节点(摘除节点) -> 主节点也无法启动, 那么无法在online的
情况下忘记节点, 所以可以在offline的情况下忘记节点
rabbitmqctl rename_cluster_node oldnode1 newnode1 [oldnode2][newnode2...] （修改节点名称)

交换机: exchange
amq.fanout -> 广播交换机
amq.direct -> 直连交换机
type: 连接方式
features: D -> Disk -> 存在磁盘

Config file: rabbitmq 配置文件所在地
Database directory: 数据存在哪