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

rabbitmq默认分发消息的模式 -> 轮询

通过设置消费者basicQos = 1且设置消费者自动应答消息参数为0 -> 手动应答, 手动发送ack 
可让消息处理快的消费者处理更多的消息(手动ack, 消费者一次处理一条消息), 
且没有被应答的消息会保存在队列中(消费者一次处理一条消息)

直连 -> consumer根据消息做相同的业务逻辑
fanout -> consumer根据消息做不同的业务逻辑
routing -> 加了筛选条件的fanout(routing key) -> 降低访问流量
topic -> 增加了通配符的操作 
\#(匹配0个或多个单词) -> .x.x.x.x.x.x无限多个
*(匹配有且仅有一个单词) -> .x

队列与交换机的创建主要与消费者有关, 没有消费者进行消费, 
仅生产者发送消息交换机不会被创建

可靠消息: 
第一步: 消息到交换机
消息的可靠性投递
两种模式
confirm模式
如果消息到达了exchange -> confirmCallback回调, ack=false
如果消息没有到达exchange -> confirmCallback回调, ack=true
在confirm方法进行回调

第二步: 交换机到队列
return模式
exchange到queue成功,则不回调ReturnCallback
在returnedMessage方法进行回调

rabbitmq提供事务机制性能较差
txSelect(), 用于将当前channel设置成transaction模式
txCommit(), 用于提交事务
txRollback(), 用于回滚事务

第三步: 队列到消费端
配置: 
自动确认: acknowledge = "none"
消息被consumer接收到, 自动确认收到, 并将相应message从RabbitMQ的消息缓存中移除

手动确认: acknowledge = "manual"
业务处理成功后, 调用channel.basicAck()手动签收
若失败channel.basicNack()

根据异常情况确认: acknowledge = "auto" (根据异常类型的不同执行不同的业务逻辑)

basicAck, basicNack方法参数
1. tag 2. false: 仅拒绝当前tag的消息 true: 拒绝直到当前参数tag参数的所有消息
3. 重回队列
void basicNack(long deliveryTag, boolean multiple, boolean requeue)
            throws IOException;

1. basicReject方法单条处理, 没有multiple参数

消息可靠性总结
1. 持久化
    a) exchange要持久化
    b) queue要持久化
    c) message要持久化
2. 生产方确认Confirm(消息到达交换机)
交换机到队列 -> return callback
3. 消费方确认Ack
4. Broker高可用

1. 消费端限流
业务场景: 秒杀 -> 部分秒杀请求可以直接丢弃

问题: 设置统一的队列过期时间在什么业务场景下可以使用?

设置队列统一过期
注意: 设置了消息的过期时间和队列的过期时间以消息的过期时间为准
消息过期后 -> 只有消息在队列顶端才会被移除 -> 不遍历提高效率