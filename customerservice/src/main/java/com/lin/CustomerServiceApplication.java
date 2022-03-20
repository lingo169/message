package com.lin;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication//(scanBasePackages= {"com.lin.controller","com.lin.service","com.lin.event.socketio","com.lin.config","com.lin.properties"})
//@EnableEncryptableProperties
@ConfigurationPropertiesScan(basePackages={"com.lin.properties"})
@MapperScan(basePackages= {"com.lin.dao"})
@EnableOpenApi
public class CustomerServiceApplication {

	public static void main(String[] args) throws Exception {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}



}
