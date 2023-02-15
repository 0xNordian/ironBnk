package com.ironbank.proj;

import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProjApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(ProjApplication.class, args); }

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	SavingsRepository savingsRepository;

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


		SavingsDTO savingsDTO = new SavingsDTO();
			savingsDTO.setBalance("5000");
			savingsDTO.setSecretKey("1234");
			savingsDTO.setPrimaryOwnerId(2L);
			savingsDTO.setInterestRate("0.05");
			savingsDTO.setMinimunBalance("1000");

		Savings sav1 = AdminService.createSavingsAcc2(savingsDTO);

		System.out.println("TEST AGAIN       :" +  sav1);



	}
}