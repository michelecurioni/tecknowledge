# Spring Boot with RabbitMq

This is a sample app that demonstrates how to integrate with RabbitMq to send 
and receive messages from specific queues.

Publish is triggered from a RestController. 
The http server (default tomcat) listens on a random port (server.port=0) to facilitate running multiple instances.
If you want to run on a static port, modify application.properties

**Notes**:
- concurrency of message processing is specified in @RabbitListener annotation on the Receiver
- prefetch count is configured by creating a custom RabbitListenerContainerFactory for the listener
- messages are ack-ed automatically in spring, but it looks like the semantics are not consistent with RabbitMQ documentation.
  RabbitMQ suggests that auto ack will consider a message delivered as soon as it's sent. In Spring, even with auto ack,
  the message is only acknowledged when the consumer finishes the processing regularly. It there is an exception the message is put back on the queue.

## Run
Start RabbitMQ docker container
```aidl
docker run --rm -it --hostname my-rabbit -p 15672:15672 -p 5672:5672 rabbitmq:3-management
```
From command line, using maven:
```aidl
mvn spring-boot:run
```
In the output, look for statement **"Tomcat started on port(s): xxx"**, where **xxx** is the random port number 
allocated to this process.  
To send a message to the queue, open Url: **http://localhost:xxx/send/test**  
In the output you will see that the message has been sent and also received by a subscriber.  
  
To check activity in RabbitMQ you can use the management interface: http://localhost:15672/#/queues/%2F/MessageQueue

