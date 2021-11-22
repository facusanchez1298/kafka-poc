package com.example.kafka;

import com.example.kafka.events.Event;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableKafka
public class KafkaExampleApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaExampleApplication.class, args);
    }
//
//	@Bean
//	public SeekToCurrentErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
//		return new SeekToCurrentErrorHandler(
//				new DeadLetterPublishingRecoverer(template), new FixedBackOff(1000L, 2));
//	}
//
//	@Bean
//	public RecordMessageConverter converter() {
//		ByteArrayJsonMessageConverter converter = new ByteArrayJsonMessageConverter();
//		DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
//		typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.TYPE_ID);
//		typeMapper.addTrustedPackages("com.common");
//		Map<String, Class<?>> mappings = new HashMap<>();
//		mappings.put("event", Event.class);
//		typeMapper.setIdClassMapping(mappings);
//		converter.setTypeMapper(typeMapper);
//		return converter;
//	}

    @Bean
    public ProducerFactory<String, Event> producerFactory() {

        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory(config);
    }

    @Bean
    public KafkaTemplate<String, Event> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, Event> consumerFactory(){
        JsonDeserializer<Event> deserializer = new JsonDeserializer<>(Event.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), deserializer);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Event> kafkaListenerContainerFactory(){
        ConcurrentKafkaListenerContainerFactory<String, Event> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;

    }

//
//	@Bean
//	public NewTopic bars() {
//		return new NewTopic("bars", 1, (short) 1);
//	}
}
