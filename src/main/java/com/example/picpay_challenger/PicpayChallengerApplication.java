package com.example.picpay_challenger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class PicpayChallengerApplication {

	public static void main(String[] args) {

		SpringApplication.run(PicpayChallengerApplication.class, args);
	}
}
