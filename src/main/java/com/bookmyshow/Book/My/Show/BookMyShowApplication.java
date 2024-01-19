package com.bookmyshow.Book.My.Show;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "BookMyShow Swagger UI", version = "1.0", description = "This Swagger page contains the details of all the endpoints of BookMyShow Application", contact = @Contact(name = "Shashwata Das", email = "shashwatadas802@gmail.com", url = "https://github.com/somendrat22?tab=repositories")))
@SpringBootApplication
public class BookMyShowApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyShowApplication.class, args);
	}

}
