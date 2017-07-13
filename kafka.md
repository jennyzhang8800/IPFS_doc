### Kafka使用帮助

### 0.配置

zookeeper:3个

broker:3个

partition:3个


### 1.启动Kafka

**(1)启动zookeeper集群**

进入到zookeeper目录，分别启动三个zookeeper
```
cd /opt/rpc/zookeeper/
```
```
bin/zkServer.sh start conf/zoo.cfg
bin/zkServer.sh start conf/zoo1.cfg
bin/zkServer.sh start conf/zoo2.cfg
```
检查zookeeper集群状态
```
bin/zkServer.sh status conf/zoo.cfg
bin/zkServer.sh status conf/zoo1.cfg
bin/zkServer.sh status conf/zoo2.cfg
```
可以看到其中一个显示Mode:leader,另外两个显示Mode:follower

**(2)启动Kafka**

进入到kafka目录，分别启动三个broker
```
cd /opt/rpc/kafka/
```
```
nohup bin/kafka-server-start1.sh config/server1.properties &
nohup bin/kafka-server-start2.sh config/server2.properties &
nohup bin/kafka-server-start3.sh config/server3.properties &
```
检查kafka和zookeeper是否都己启动。输入``jps``查看当前java进程,可以看到下面的信息：
```
3380 Kafka
2807 Kafka
2554 QuorumPeerMain
3659 Jps
2669 QuorumPeerMain
2622 QuorumPeerMain
3103 Kafka
```

至此，Kafka己经准备好了！接下面就可以进行生产与消费了。

### 2.简单的测试

**(1)新建一个topic**

在进行生产与消费信息之前，必须建立topic。
```
cd /opt/rpc/kafka/
```

```
 bin/kafka-topics.sh --create --zookeeper 192.168.5.128:2181,192.168.5.128:2182,192.168.5.128:2183 --replication-factor 3 --partitions 3 --topic test
```
+ --zookeeper集群有三个:192.168.5.128:2181,192.168.5.128:2182,192.168.5.128:2183
+ --replication-factor 3  :三个副本
+ --partitions 3 ：三个分区
+ --topic test ：topic名称为test

检查topic是否创建成功:
```
bin/kafka-topics.sh --list --zookeeper 192.168.5.128:2181,192.168.5.128:2182,192.168.5.128:2183
```

**(2)启动一个生产者**
```
 bin/kafka-console-producer.sh --broker-list 192.168.5.128:9092,192.168.5.128:9093,192.168.5.128:9094 --topic test

```
可以输入想要发送的消息，回车即发送


**(3)启动一个消费者**

重新开启一个terminal
```
cd /opt/rpc/kafka/
```

```
bin/kafka-console-consumer.sh --bootstrap-server 192.168.5.128:9092,192.168.5.128:9093,192.168.5.128:9094 --topic test --from-beginning
```

可以看到一旦在生产者窗口输入一条消息，消费者窗口就会显示出来。

### 3.在IDEA中写java代码进行测试

**(1)在pom.xml中加入dependencies和plugin**
```
 <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka_2.10</artifactId>
            <version>0.9.0.1</version>
</dependency>
```
```
 <plugin>
       <artifactId>maven-compiler-plugin</artifactId>
       <version>3.3</version>
       <configuration>
              <source>1.7</source>
               <target>1.7</target>
       </configuration>
</plugin>
```
把example下的代码复制到project中

**(2)运行ConsumerDemo.java**

可以看到下面的信息，表示Consumer己经启动:
```
14:41:19.173 [main] DEBUG o.a.k.clients.consumer.KafkaConsumer - Starting the Kafka consumer
```

**(3)运行ProducerDemo.java**

**(4)查看consumer是否收到消息**

可以看到ConsumerDemo.java的运行窗口显示出下面的信息：
```
Received message: (218448, Message:218448) at offset 487808
Received message: (218452, Message:218452) at offset 487809
Received message: (218454, Message:218454) at offset 487810
Received message: (218457, Message:218457) at offset 487811
Received message: (218458, Message:218458) at offset 487812
...

```
