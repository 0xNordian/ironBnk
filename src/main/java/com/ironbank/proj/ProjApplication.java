package com.ironbank.proj;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Role;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.Checking;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class ProjApplication implements CommandLineRunner {

	public static void main(String[] args) { SpringApplication.run(ProjApplication.class, args); }

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	SavingsRepository savingsRepository;

	@Autowired
	AdminService adminService;

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;



	public void run(String... args) throws Exception {
		userService.saveRole(new Role(null, "ROLE_ACCOUNT_HOLDER"));
		userService.saveRole(new Role(null, "ROLE_ADMIN"));

		AccountHolder ah1 = new AccountHolder("Jose", "Jose1995", passwordEncoder.encode("jose1985"),new ArrayList<>(),LocalDate.of(1986, 4, 15), null, null);
		accountHolderRepository.save(ah1);
		userService.addRoleToUser("Jose1995","ROLE_ACCOUNT_HOLDER");

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
			accountDTO1.setBalance("2000");
			accountDTO1.setSecretKey("N90ZF87b");
			accountDTO1.setPrimaryOwnerId(1L);

			Account checking1 = adminService.createCheckingOrStudChecking(accountDTO1);
		System.out.println("Checking1 from Main: " + accountDTO1);
		System.out.println("Cheking1: " + checking1);

	}
}