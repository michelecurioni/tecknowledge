package com.opentext.sample.rabbit.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marker annotation that tells spring to generate bean definitions at runtime for the methods annotated with @Bean annotation.
@Configuration
public class RabbitMqConfig {
	private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMqConfig.class);
	
	@Value("${rabbitmq.queue}")
	private String qName;

	private String testQName = "testQueue";
	
	@Value("${rabbitmq.exchange}")
	private String exchange;

	@Value("${rabbitmq.deploy.prefetch.count}")
	private int prefetchCount;

	@Value("${rabbitmq.routingkey}")
	private String routingKey;

	@Bean
	@Qualifier("deploymentQueue")
	Queue deploymentQueue() {
		return new Queue(qName, Boolean.TRUE);
	}

	@Bean
	Queue testQueue() {
		return new Queue(testQName, Boolean.TRUE);
	}

	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(@Qualifier("deploymentQueue") final Queue q, final DirectExchange directExchange) {
		LOGGER.info("Listening on queue {}", q.getName());
		return BindingBuilder.bind(q).to(directExchange).with(routingKey);
	}

//	@Bean
//	public RabbitListenerContainerFactory<DirectMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
//		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
//		factory.setConnectionFactory(rabbitConnectionFactory);
//
//		factory.setPrefetchCount(prefetchCount);
//		factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
//		return factory;
//	}
}
