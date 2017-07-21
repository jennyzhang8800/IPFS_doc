>新闻资讯总线日志发布到Kafka的实现

**目录**
* [0. 需求](#demand)
* [1. 框架](#archi)
* [2. 代码实现](#code)
* [3. 附录](#attach)
<hr/>

<h2 id='demand'>0. 需求</h2>

>原先接口的调用日志直接保存在oracle数据库。但随着商城业务的丰富，商城接口调用愈加频繁，日志记录量庞大，原先的日志存储方法己不能满足需求，如今对吞吐率有更高要求。

>Kafka具有高吞吐率,分布式,可扩展，高可靠的特点，能有效解决大量日志传输的问题,被用作LinkedIn的活动流（Activity Stream）和运营数据处理管道（Pipeline）的基础,也是新浪日志处理的基础。

>因此，采用Kafka集群作为消息队列，负责日志数据的接收，存储和转发，然后把日志持久保存到MongDB中的方法，能满足目前商城接口调用日志存储的需求。

<h2 id='archi'>1.框架</h2>

原先日志存储方式：

![kafkalog_old.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/kafkalog_old.jpg)

使用kafka集群后的日志存储方法：

![kafkalog_new.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/kafkalog_new.jpg)


<h2 id='code'>2. 代码实现</h2>

这是“新闻资讯”版块接口调用日志存储的例子。在原先直接存储到oracle数据库的基础代码上进行修改.最终实现先把日志信息异步发布到Kafka集群中，再存储到MongoDB的效果。 Kafka的配置己经封装成jar。

### (1)maven 把jar包导入本地仓库

命令行输入下面的代码:
```
mvn install:install-file -Dfile=D:\common-kafka.jar   -DgroupId=com.hahafucker  -DartifactId=common-kafka  -Dversion=0.0.2  -Dpackaging=jar

```

注意上面的:
+ -Dfile：是jar包所在的本地路径.
+ -DgroupId:是jar包的group id
+ -DartifactId:是jar包的artifact id
+ -Dversion:是jar包的version

### (2)在maven项目的pom.xml中加入该jar包的依赖

在pom.xml中加入：
```
 <dependency>
            <groupId>com.hahafucker</groupId>
            <artifactId>common-kafka</artifactId>
            <version>0.0.2</version>
 </dependency>
```
注意（1）和（2）元素的对应关系

如果导入成功，会在``External Libraries``中看到``Maven:com.hahafucker:common-kafka:0.0.2``

### (3)新建bean
在``app/src/main/resources/`` 下新建``applicationContext_kafa.xml``。加入两个bean
```
 <!--kafka生产者-->
    <bean id="ProducerProvider" class="***">
        <property name="defaultAsynSend" value="true"></property>
        <property name="delayRetries" value="2"></property>
        <property name="configs">
            <props>
                <prop key="bootstrap.servers">${kafka.servers}</prop>
                <prop key="acks">all</prop>
            </props>
        </property>
        <!--<property name="env" value="dev"></property>-->
        <!--生产组-->
        <property name="producerGroup" value="LOG_COLLECTION"></property>
    </bean>

    <bean id="SqlLogger" class="***">
        <constructor-arg>
            <value type="java.lang.String">${logTopic}</value>
        </constructor-arg>
    </bean>
```

### (4)实现ApplicationContextAware接口

第一个bean，ProducerProvider己经有默认的构造器（在封装好的jar里）,因此不用再实现

第二个bean，方法类SqlLogger需要自己实现。在``common/src/main/java/com/linkstec/common/log/``下的SqlLogger.java
```
public class SqlLogger implements ApplicationContextAware{
 /**
     *  默认构造器
     */
    public SqlLogger() {
    }
}
```
### (5)通过 ApplicationContext上下文取到kafka生产者工具bean和topic

``SqlLogger.java`` 中获得producerProvide
```
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
        producerProvider = (ProducerProvider) context.getBean("ProducerProvider");
    }
```
### (6)转换日志格式

新建CommonLog类，统一日志格式
```
public class CommonLog implements Serializable{
...
}
```
### (7)重写SqlLogger.log
```
  public static void log(T2LogDto dto) {
        if (!StringUtil.isNullOrEmpty(dto.getFunctionId())) {
           ...
            // 触发db logger输出
            dblogger.info(dto);
        }
         // kafka logger发布
        if (null == producerProvider) {
            producerProvider = (ProducerProvider) context.getBean("ProducerProvider");
        }
        Boolean flag = producerProvider.publish(logTopic, new DefaultMessage(JSON.toJSON(new CommonLog(dto)).toString()));

    }
```

<h2 id='attach'>3. 附录</h2>

+ kafka集群:

![kafka_cluster.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/kafka_cluster.jpg)

+ kafka消息传递:

![kafka_message.jpg](https://github.com/jennyzhang8800/gtja_mall/blob/master/pictures/kafka_message.jpg)

kafka的特性决定它非常适合作为"日志收集中心";application可以将操作日志"批量""异步"的发送到kafka集群中,而不是保存在本地或者DB中;kafka可以批量提交消息/压缩消息等,这对producer端而言,几乎感觉不到性能的开支.此时consumer端可以使Hadoop等其他系统化的存储和分析系统

+ 参考文档:
  + [kafka_quick_start](https://www.w3cschool.cn/apache_kafka/apache_kafka_quick_guide.html)
  + [Kafka特点](http://blog.csdn.net/louisliaoxh/article/details/51516150)
  + [kafka结构 ](http://www.cnblogs.com/gslyyq/p/5240122.html)
  + [Kafka应用场景](http://www.cnblogs.com/stopfalling/p/5375492.html)
  + [Kafka使用帮助](https://github.com/jennyzhang8800/gtja_mall/blob/master/documentation/kafka.md)
 

