package com.ironbank.proj;

import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;

@SpringBootApplication
public class ProjApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(ProjApplication.class, args); }

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	SavingsRepository savingsRepository;

	@Autowired
	AdminService adminService;


	public void run(String... args) throws Exception {
		AccountHolder accountH = new AccountHolder("Jose", LocalDate.now(), null, null);
		accountH.setId(1L);
		accountHolderRepository.save(accountH);

		SavingsDTO savingsDTO = new SavingsDTO();
			savingsDTO.setBalance("5000");
			savingsDTO.setPrimaryOwnerId(1L);
			savingsDTO.setMinimunBalance("1000");
			savingsDTO.setSecretKey("1234");
			savingsDTO.setInterestRate("0.05");

		Savings sav1 = adminService.createSavingsAcc2(savingsDTO);

		System.out.println("TEST: " +  savingsDTO);

	}
}