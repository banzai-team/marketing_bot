package banz.ai.marketing.bot.modelinterceptor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ModelInterceptorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModelInterceptorApplication.class, args);
	}

}
