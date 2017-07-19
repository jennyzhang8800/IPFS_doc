>Kafka生产与消费监控

### 1. KafkaOffsetMonitor

[KafkaOffsetMonitor github仓库](https://github.com/quantifind/KafkaOffsetMonitor)

### 2. 使用方法

(1)下载jar包

+ [KafkaOffsetMonitor-assembly-0.2.1.jar](https://github.com/quantifind/KafkaOffsetMonitor/releases/download/v0.2.1/KafkaOffsetMonitor-assembly-0.2.1.jar)

(2)运行

进入到KafkaOffsetMonitor-assembly-0.2.1.jar所在目录,运行下面的命令以启动Monitor

```
java -cp KafkaOffsetMonitor-assembly-0.3.0-SNAPSHOT.jar \
     com.quantifind.kafka.offsetapp.OffsetGetterWeb \
     --offsetStorage kafka \
     --zk 192.168.5.128:2181,192.168.5.128:2182,192.168.5.128:2183 \
     --port 8080 \
     --refresh 10.seconds \
     --retain 2.days
```

(3)浏览器查看

http://192.168.5.128:8080/

### 3. 监控示例

四个producer(每个producer异步发送消息),三个consumer

四个producer:
  
+ 有两个发送到TOPIC("fucker")
+ 有一个发送到TOPIC2("topic2")
+ 有一个发送到TOPIC3("topic3")

三个consumer:

+ 分别订阅"fucker","topic2","topic3"


producer代码示例：
```
        Producer producerThread = new Producer(KafkaProperties.TOPIC,"client1", isAsync);
        producerThread.start();
        Producer producerThread1 = new Producer(KafkaProperties.TOPIC, "client2",isAsync);
        producerThread1.start();
        Producer producerThread3 = new Producer(KafkaProperties.TOPIC2, "client2",isAsync);
        producerThread3.start();
        Producer producerThread2 = new Producer(KafkaProperties.TOPIC3,"client3", isAsync);
        producerThread2.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
```

监控结果：

由于topic "fucker"有两个生产者，只有一个消费者，因此消费速度比生产速度慢

![monitor_topic_fucker.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/monitor_topic_fucker.jpg)

topic "topic3"有一个生产者，一个消费者，因此消费速度与生产速度一致

![Monitor_topic3.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/Monitor_topic3.jpg)


