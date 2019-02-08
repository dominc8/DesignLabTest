package de.jonashackt.springbootvuejs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SpringBootVuejsApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBootVuejsApplication.class, args);
		System.out.println("haslo: " + new BCryptPasswordEncoder().encode("admin@123"));
	}
}
