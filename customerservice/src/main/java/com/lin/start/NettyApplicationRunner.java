package com.lin.start;

import com.corundumstudio.socketio.SocketIOServer;
import com.lin.config.SpringContextIniter;
import com.lin.properties.ConsumeProperties;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Configuration
@Component
public class NettyApplicationRunner implements ApplicationRunner {
    protected static Logger log = LoggerFactory.getLogger(NettyApplicationRunner.class);

//    public static final String NAMESPACE_ = "/chat1";//构建命名空间
    @Autowired
    private SocketIOServer socketIOServer;
    @Autowired
    private ConsumeProperties consumeProperties;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        //socketio 消费
//        DefaultMQPushConsumer dpc = CommonConsumerFactory.getInstance(consumeProperties.getSocketio().get(RocketMqConstant.CONSUMER_GROUP), MessageModel.BROADCASTING, consumeProperties.getSocketio().get(RocketMqConstant.TOPIC), consumeProperties.getSocketio().get(RocketMqConstant.TAGS));
//        log.info("DefaultMQPushConsumer {},MessageModel:{},Subscription:{}",dpc.getConsumerGroup(),dpc.getMessageModel(),dpc.getSubscription());
//        Thread.sleep(1000);
//        //更新group的内容消费
//        DefaultMQPushConsumer dpc2 = CommonConsumerFactory.getInstance(consumeProperties.getContent().get(RocketMqConstant.CONSUMER_GROUP), MessageModel.CLUSTERING, consumeProperties.getContent().get(RocketMqConstant.TOPIC), consumeProperties.getContent().get(RocketMqConstant.TAGS));
//        log.info("DefaultMQPushConsumer {},MessageModel:{},Subscription:{}",dpc2.getConsumerGroup(),dpc2.getMessageModel(),dpc2.getSubscription());
        socketIOServer.start();

    }

//    public static void main(String[] args) throws MQClientException, UnsupportedEncodingException, RemotingException, InterruptedException {
//        DefaultMQProducer producer = new DefaultMQProducer("please_rename_unique_group_name");
//        // Specify name server addresses.
//        producer.setNamesrvAddr("139.198.176.37:9876");
//        //Launch the instance.
//        producer.start();
//        //Create a message instance, specifying topic, tag and message body.
//        Message msg = new Message("TopicTest",
//                "TagA",
//                ("Hello RocketMQ ").getBytes(RemotingHelper.DEFAULT_CHARSET)
//        );
//        SendResult sendResult = null;
//        try {
//            //Call send message to deliver message to one of brokers.
//            producer.send(msg, new SendCallback() {
//
//                @Override
//                public void onSuccess(SendResult sendResult) {
//                    System.out.println(sendResult);
//                }
//
//                @Override
//                public void onException(Throwable throwable) {
//                    log.error("eerror: {}", throwable);
//                    System.out.println(throwable);
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        Thread.sleep(Integer.MAX_VALUE);
//        System.out.printf("shutdown %s%n", sendResult);
//        //Shut down once the producer instance is not longer in use.
//        producer.shutdown();
//    }

    @PreDestroy
    private void autoStop() throws Exception {
        log.info("stop socketIOServer");
//        SocketIOServer ss =SocketServerFactory.getInstance();
        // 避免重启项目服务端口占用问题
        if (socketIOServer != null) {
            socketIOServer.stop();
        }
        log.info("shutdown redissonClient");
        RedissonClient redissonClient = SpringContextIniter.getBean(RedissonClient.class);
        if (redissonClient != null) {
            redissonClient.shutdown();
        }
    }

}
