package com.alibaba.otter.canal.client.running.kafka;

import org.junit.Assert;

/**
 * Kafka 测试基类
 *
 * @author machengyuan @ 2018-6-12
 * @version 1.0.0
 */
public abstract class AbstractKafkaTest {

    public static String  topic     = "canal";
    public static Integer partition = 0;
    public static String  groupId   = "g1";
    public static String  servers   = "192.144.170.156:9092";
    public static String  zkServers = "slave1:2181,slave2:2181,slave3:2181";

    public void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            Assert.fail(e.getMessage());
        }
    }
}
