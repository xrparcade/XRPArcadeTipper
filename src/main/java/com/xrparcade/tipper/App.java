package com.xrparcade.tipper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class App {
	private static ApplicationContext ctx;

	public static void main(String[] args) {
		ctx = SpringApplication.run(App.class, args);
	}

	public static ApplicationContext getContext() {
		return ctx;
	}
}
