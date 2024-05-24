package dev.evansdev;

import dev.evansdev.customer.Customer;
import dev.evansdev.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner runner(CustomerRepository ctx) {
		return args -> {
			Customer customer1 = new Customer("Dev#1", "dev1@test.com", 30);
			Customer customer2 = new Customer("Dev#2", "dev2@test.com", 32);

			List<Customer> allCustomers = List.of(customer1, customer2);
			ctx.saveAll(allCustomers);
		};
	}
}
