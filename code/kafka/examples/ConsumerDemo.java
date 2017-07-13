package com.linkstec.common.kafka.examples;

/**
 * Created by linkage on 2017/7/13.
 */
public class ConsumerDemo {
    public static void main(String[] args) {
        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();
    }
}
