# Spring Boot with RabbitMq

This is a sample app that demonstrates how to integrate with RabbitMq to send 
and receive messages from specific queues.

Publish is triggered from a RestController. Http server listens on a random port (server.port=0) to facilitate running multiple instances.


**Notes**:
- concurrency of message processing is specified in @RabbitListener annotation on the Receiver
- prefetch count is configured by creating a custom RabbitListenerContainerFactory for the listener
- messages are ack-ed automatically in spring, but it looks like the semantics are not consistent with RabbitMQ documentation.
  RabbitMQ suggests that auto ack will consider a message delivered as soon as it's sent. In Spring, even with auto ack,
  the message is only acknowledged when the consumer finishes the processing regularly. It there is an exception the message is put back on the queue. 