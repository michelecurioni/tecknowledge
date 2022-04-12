package com.opentext.sample.rabbit.receiver;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Receiver {

	private static final Logger LOGGER = LoggerFactory.getLogger(Receiver.class);

	@Autowired
	Queue deploymentQueue;

	@RabbitListener(queues = "#{deploymentQueue.getName()}", ackMode = "MANUAL",
			containerFactory = "prefetchTenRabbitListenerContainerFactory",
			concurrency = "5")	// Dynamically reading the queue name using SpEL from the "queue" object.
	public void getMsg(final String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
			throws IOException {
		LOGGER.info("Getting messages.....");
		LOGGER.info("Finally Receiver received the message and the message  is..\n" + message);

		channel.basicAck(tag, false);
	}
}
