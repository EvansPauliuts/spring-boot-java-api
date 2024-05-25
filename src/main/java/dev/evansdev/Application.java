package dev.evansdev;

import com.github.javafaker.Faker;
import dev.evansdev.customer.Customer;
import dev.evansdev.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository ctx) {
		return args -> {
			Faker faker = new Faker();
			Random random = new Random();

			Customer customer = new Customer(
					faker.name().firstName(),
					faker.internet().safeEmailAddress(),
					random.nextInt(16, 99)
			);

			ctx.save(customer);
		};
	}
}
