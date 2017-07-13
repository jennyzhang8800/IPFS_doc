package com.linkstec.common.kafka.examples;

/**
 * Created by linkage on 2017/7/13.
 */
public class ProducerDemo {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");
        Producer producerThread = new Producer(KafkaProperties.TOPIC, isAsync);
        producerThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
