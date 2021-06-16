package Wit_app.Wit_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import javax.validation.Validator;


@SpringBootApplication
public class WitAppApplication {

	public static void main(String[] args) { SpringApplication.run(WitAppApplication.class, args); }

	@Bean
	Validator validator() {
		return new LocalValidatorFactoryBean();
	}

	}

