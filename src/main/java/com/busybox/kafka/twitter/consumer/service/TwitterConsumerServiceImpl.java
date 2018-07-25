package com.busybox.kafka.twitter.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Properties;

@Slf4j
@Service
public class TwitterConsumerServiceImpl implements TwitterConsumerService {

    private static final int POLL_TIMEOUT = 100;
    private static final String GROUP_ID = "test-consumer-group";

    @Value("${kafka.client.id}")
    private String clientId;

    @Value("${kafka.bootstrap.servers}")
    private String brokerBootstrapServers;

    @Value("${kafka.topic.default}")
    private String defaultTopicName;

    @Override
    public void run() {
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(getProperties());

        consumer.subscribe(Collections.singleton(defaultTopicName));
        log.info("Subscribed to {} topic.", defaultTopicName);

        while (true) {
            final ConsumerRecords<String, String> records = consumer.poll(POLL_TIMEOUT);
            records.forEach(System.out::println);
        }
    }

    private Properties getProperties() {
        final Properties properties = new Properties();
        properties.put(ConsumerConfig.CLIENT_ID_CONFIG, clientId);
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokerBootstrapServers);

        properties.put(ConsumerConfig.GROUP_ID_CONFIG, GROUP_ID);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getCanonicalName());
        return properties;
    }
}
