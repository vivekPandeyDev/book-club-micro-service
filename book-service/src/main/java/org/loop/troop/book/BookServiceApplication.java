package org.loop.troop.book;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * The type Book service application.
 *
 * @author alex
 */
@SpringBootApplication
@EnableTransactionManagement
@EnableAsync
@EnableDiscoveryClient
public class BookServiceApplication {

	/**
	 * The entry point of application.
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookServiceApplication.class, args);
	}

}
