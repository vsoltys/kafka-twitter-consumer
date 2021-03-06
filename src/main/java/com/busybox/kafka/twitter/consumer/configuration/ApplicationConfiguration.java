package com.busybox.kafka.twitter.consumer.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:properties/producer.properties")
@Configuration
@ComponentScan(basePackages = {"com.busybox.kafka.twitter.consumer"})
public class ApplicationConfiguration {

}
