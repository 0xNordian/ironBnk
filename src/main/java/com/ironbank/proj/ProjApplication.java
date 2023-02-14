package com.ironbank.proj;

import com.ironbank.proj.model.Address;
import com.ironbank.proj.model.User;
import com.ironbank.proj.model.UserRole;
import com.ironbank.proj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootApplication
public class ProjApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) { SpringApplication.run(ProjApplication.class, args); }

	public void run(String... args) throws Exception {
/*
		Address primaryAddress = new Address();
		primaryAddress.setStreet("123 Main St");
		primaryAddress.setCity("Anytown");
		primaryAddress.setState("CA");
		primaryAddress.setCountry("USA");
		primaryAddress.setZipCode("12345");

		User user = new User("John Doe", LocalDate.of(1987, 10, 30), primaryAddress, null, null, UserRole.ACCOUNT_HOLDER.name());
		User adminUser = userService.createUser("John Smith", null, primaryAddress, null, UserRole.ADMIN, null);

 */

	}

}