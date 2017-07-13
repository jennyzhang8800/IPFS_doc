package com.linkstec.common.kafka.examples;

/**
 * Created by linkage on 2017/7/13.
 */
public class KafkaProperties {
    public static final String TOPIC = "fucker";
    public static final String KAFKA_SERVER_URL = "192.168.5.128:9092,192.168.5.128:9093,192.168.5.128:9094";
    public static final int KAFKA_SERVER_PORT = 9092;
    public static final int KAFKA_PRODUCER_BUFFER_SIZE = 64 * 1024;
    public static final int CONNECTION_TIMEOUT = 100000;
    public static final String TOPIC2 = "topic2";
    public static final String TOPIC3 = "topic3";
    public static final String CLIENT_ID = "SimpleConsumerDemoClient";

    private KafkaProperties() {}
}