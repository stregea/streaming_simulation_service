package com.streaming.simulation_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Configures Kafka topic creation for the simulation service.
 *
 * <p>This configuration class declares the Kafka topic definitions used by the application and
 * resolves the topic name from configuration. It keeps topic setup in one place so the simulator
 * and producer components remain focused on producing events instead of managing broker setup.</p>
 *
 * @see NewTopic
 * @see TopicBuilder
 */
@Configuration
public class KafkaTopicConfig {

    /**
     * Name of the Kafka topic used to publish simulated match events.
     *
     * <p>The value is resolved from the property {@code simulation.kafka.match-event-topic}.</p>
     */
    @Value("${simulation.kafka.match-event-topic}")
    private String matchEventTopic;

    /**
     * Create the Kafka topic used for simulated match events.
     *
     * <p>Spring Kafka will register this topic with the configured Kafka admin client on startup,
     * ensuring the topic exists before producers publish to it. The default topic configuration is
     * used unless additional partitions or replication settings are required.</p>
     *
     * @return a {@link NewTopic} definition for the match event stream
     */
    @Bean
    public NewTopic matchEventTopic() {
        return TopicBuilder.name(matchEventTopic)
                .build();
    }
}
