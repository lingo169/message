package com.lin.config;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * rocketmq生产者单例
 */
public class CommonProductFactory{

	private static Logger logger = LoggerFactory.getLogger(CommonProductFactory.class);
	private enum CommonProductSingleton{
		INSTANCE;

		private DefaultMQProducer instance;

		private CommonProductSingleton(){//枚举类的构造方法在类加载是被实例化
			instance = new DefaultMQProducer(PropertyUtil.getString("rocketmq.producer.group"));
			instance.setNamesrvAddr(PropertyUtil.getString("rocketmq.name-server"));
			instance.setSendMsgTimeout(Integer.parseInt(PropertyUtil.getString("rocketmq.producer.sendMessageTimeout")));
			try {
				instance.start();
				logger.info("Socketmq Product Success");
			} catch (MQClientException e) {
				logger.error("start error :",e);
			}
		}

		public DefaultMQProducer getInstance(){
			return instance;
		}

	}

	public static DefaultMQProducer getInstance(){
		return CommonProductSingleton.INSTANCE.getInstance();
	}

}
