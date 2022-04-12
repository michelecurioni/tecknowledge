package com.codeusingjava.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// Marker annotation that tells spring to generate bean definitions at runtime for the methods annotated with @Bean annotation.
@Configuration
public class RabbitMqConfig {

	
	@Value("${rabbitmq.queue}")
	private String qName;
	
	
	@Value("${rabbitmq.exchange}")
	private String exchange;
	
	
	@Value("${rabbitmq.routingkey}")
	private String routingKey;

	@Bean
	Queue qu() {
		return new Queue(qName, Boolean.TRUE);
	}

	@Bean
	DirectExchange directExchange() {
		return new DirectExchange(exchange);
	}

	@Bean
	Binding binding(final Queue q, final DirectExchange directExchange) {
		return BindingBuilder.bind(q).to(directExchange).with(routingKey);
	}

	@Bean
	public RabbitListenerContainerFactory<DirectMessageListenerContainer> prefetchTenRabbitListenerContainerFactory(ConnectionFactory rabbitConnectionFactory) {
		DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
		factory.setConnectionFactory(rabbitConnectionFactory);
		factory.setPrefetchCount(10);
		return factory;
	}
}
