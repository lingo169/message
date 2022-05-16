package com.lin;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CustomerGatewayApplication {
	public static void main(String[] args) throws Exception {
		SpringApplication.run(CustomerGatewayApplication.class, args);
	}

	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		config.useSingleServer().setAddress("redis://127.0.0.1:6379");//"redis://139.198.176.37:6379"
		return Redisson.create(config);
	}
}
