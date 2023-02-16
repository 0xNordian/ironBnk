package com.ironbank.proj;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.CreditCard;
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
		AccountHolder ah1 = new AccountHolder("Jose", LocalDate.now(), null, null);
		ah1.setId(1L);
		accountHolderRepository.save(ah1);

		SavingsDTO savingsDTO = new SavingsDTO();
			savingsDTO.setBalance("5000");
			savingsDTO.setPrimaryOwnerId(1L);
			savingsDTO.setMinimunBalance("1000");
			savingsDTO.setSecretKey("1234");
			savingsDTO.setInterestRate("0.05");

		Savings sav1 = adminService.createSavingsAcc2(savingsDTO);

		System.out.println("TEST: " +  savingsDTO);

		AccountDTO accountDTO = new AccountDTO();
			accountDTO.setBalance("3000");
			accountDTO.setSecretKey("RE45Bh67");
			accountDTO.setPrimaryOwnerId(1L);
			accountDTO.setCreditLimit("1250");
			accountDTO.setInterestRate("2");

		CreditCard cc1 = adminService.createCreditCardAccount(accountDTO);
		System.out.println("CreditCard: " + accountDTO);

	}
}