package com.ironbank.proj;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.Checking;
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
		AccountHolder ah1 = new AccountHolder("Jose", LocalDate.of(1986, 4, 15), null, null);
		ah1.setId(1L);
		accountHolderRepository.save(ah1);

		SavingsDTO savingsDTO = new SavingsDTO();
			savingsDTO.setBalance("6000");
			savingsDTO.setPrimaryOwnerId(1L);
			savingsDTO.setMinimunBalance("2000");
			savingsDTO.setSecretKey("4321");
			savingsDTO.setInterestRate("0.05");

		Savings sav1 = adminService.createSavingsAcc(savingsDTO);
		System.out.println("TEST: " +  savingsDTO);

		SavingsDTO savingsDTO1 = new SavingsDTO();
		savingsDTO1.setBalance("2000");
		savingsDTO1.setPrimaryOwnerId(1L);
		savingsDTO1.setMinimunBalance("7000");
		savingsDTO1.setSecretKey("789");
		savingsDTO1.setInterestRate("0.1");

		Savings sav2 = adminService.createSavingsAcc(savingsDTO1);
		System.out.println("TEST: " +  savingsDTO1);

		AccountDTO accountDTO = new AccountDTO();
			accountDTO.setBalance("3000");
			accountDTO.setSecretKey("RE45Bh67");
			accountDTO.setPrimaryOwnerId(1L);
			accountDTO.setCreditLimit("1250");
			accountDTO.setInterestRate("2");

		CreditCard cc1 = adminService.createCreditCardAccount(accountDTO);
		System.out.println("CreditCard: " + accountDTO);


		//Checking > 24
		AccountDTO accountDTO1 = new AccountDTO();
			accountDTO1.setBalance("3000");
			accountDTO1.setSecretKey("ZF87bN90");
			accountDTO1.setPrimaryOwnerId(1L);
			accountDTO1.setMinimunBalance("100");
			accountDTO1.setMonthlyMaintenanceFee("");
			accountDTO1.setDateOfBirth(LocalDate.of(1995, 4, 15));

			Account checking1 = adminService.createCheckingOrStudChecking(accountDTO1);
		System.out.println("Checking1 from Main: " + accountDTO1);
		System.out.println("Cheking1: " + checking1);


	}
}