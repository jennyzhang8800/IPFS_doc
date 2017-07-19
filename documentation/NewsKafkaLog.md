>新闻资讯总线日志发布到Kafka的实现

**目录**
* [0. 需求](#demand)
* [1. 代码实现](#code)

<hr/>

<h2 id='demand'>0. 需求</h2>



<h2 id='code'>1. 代码实现</h2>

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
    <bean id="ProducerProvider" class="com.linkstec.common.kafka.defaultKafkaTemplate.ProducerProvider">
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

    <bean id="SqlLogger" class="com.linkstec.common.log.SqlLogger">
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
