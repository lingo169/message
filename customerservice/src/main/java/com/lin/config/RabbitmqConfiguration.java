//package com.lin.config;
//
//import com.rabbitmq.client.impl.AMQImpl;
//import org.springframework.amqp.core.Binding;
//import org.springframework.amqp.core.BindingBuilder;
//import org.springframework.amqp.core.DirectExchange;
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
//import org.springframework.amqp.support.converter.MessageConverter;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RabbitmqConfiguration {
//    @Value("${spring.rabbitmq.socketio.topic}")
//    private String socketioTopic;
//
//    @Value("${spring.rabbitmq.content.topic}")
//    private String contentTopic;
//
//    @Bean
//    DirectExchange TestDirectExchange() {
//        //  return new DirectExchange("TestDirectExchange",true,true);
//        return new DirectExchange("TestDirectExchange",true,false);
//    }
//
//    //绑定  将队列和交换机绑定, 并设置用于匹配键：TestDirectRouting
//    @Bean
//    Binding bindingDirect() {
//        return BindingBuilder.bind(content()).to(TestDirectExchange()).with("TestDirectRouting");
//    }
//
//    @Bean
//    MessageConverter createMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }
//
//    @Bean
//    public Queue message() {
//        // 其三个参数：durable exclusive autoDelete
//        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
//        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
//        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
//        return new Queue(socketioTopic,true);
//    }
//
//    @Bean
//    public Queue content() {
//        return new Queue("content",true);
//    }
//
//}
