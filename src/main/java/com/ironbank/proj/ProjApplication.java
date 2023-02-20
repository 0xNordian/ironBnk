package com.ironbank.proj;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Role;
import com.ironbank.proj.models.User;
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

		AccountHolder ah1 = new AccountHolder("Jose", "jose1995", passwordEncoder.encode("jose1985"),new ArrayList<>(),LocalDate.of(1986, 4, 23), null, null);
		accountHolderRepository.save(ah1);
		userService.addRoleToUser("jose1995","ROLE_ADMIN");

		AccountHolder ah2 = new AccountHolder("Nestor", "Nestor1986", passwordEncoder.encode("nt1986"),new ArrayList<>(),LocalDate.of(1987, 7, 2), null, null);
		accountHolderRepository.save(ah2);
		userService.addRoleToUser("Nestor1986","ROLE_ACCOUNT_HOLDER");

		AccountHolder ah3 = new AccountHolder("John Doe", "john", passwordEncoder.encode("1234"),new ArrayList<>(),LocalDate.of(1991, 12, 10), null, null);
		accountHolderRepository.save(ah3);
		userService.addRoleToUser("john","ROLE_ACCOUNT_HOLDER");


		//userService.saveUser(new User(null, "John Doe", "john", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "James Smith", "james", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Jane Carry", "jane", "1234", new ArrayList<>()));
		userService.saveUser(new User(null, "Chris Anderson", "chris", "1234", new ArrayList<>()));

		//userService.addRoleToUser("john", "ROLE_ACCOUNT_HOLDER");
		userService.addRoleToUser("james", "ROLE_ADMIN");
		userService.addRoleToUser("jane", "ROLE_ACCOUNT_HOLDER");
		userService.addRoleToUser("chris", "ROLE_ADMIN");
		userService.addRoleToUser("chris", "ROLE_ACCOUNT_HOLDER");

		SavingsDTO savingsDTO = new SavingsDTO();
		savingsDTO.setBalance("6000");
		savingsDTO.setPrimaryOwnerId(1L);
		savingsDTO.setMinimunBalance("2000");
		savingsDTO.setSecretKey("4321");
		savingsDTO.setInterestRate(null);

		Savings sav1 = adminService.createSavingsAcc(savingsDTO);
		System.out.println("TEST: " +  savingsDTO);

		SavingsDTO savingsDTO1 = new SavingsDTO();
		savingsDTO1.setBalance("2000");
		savingsDTO1.setPrimaryOwnerId(1L);
		savingsDTO1.setMinimunBalance("7000");
		savingsDTO1.setSecretKey("789");
		savingsDTO1.setInterestRate("0.005");

		Savings sav2 = adminService.createSavingsAcc(savingsDTO1);
		System.out.println("TEST: " +  savingsDTO1);

		AccountDTO accountDTO = new AccountDTO();
		accountDTO.setBalance("3000");
		accountDTO.setSecretKey("RE45Bh67");
		accountDTO.setPrimaryOwnerId(1L);
		accountDTO.setCreditLimit("1250");
		accountDTO.setInterestRate("0.1");

		CreditCard cc1 = adminService.createCreditCardAccount(accountDTO);
		System.out.println("CreditCard: " + accountDTO);


		//Checking > Id1
		AccountDTO accountDTO1 = new AccountDTO();
		accountDTO1.setBalance("2000");
		accountDTO1.setSecretKey("N90ZF87b");
		accountDTO1.setPrimaryOwnerId(1L);

		Account checking1 = adminService.createCheckingOrStudChecking(accountDTO1);
		System.out.println("Checking1 from Main: " + accountDTO1);
		System.out.println("Cheking1: " + checking1);

		//Checking > Id2
		AccountDTO accountDTO2 = new AccountDTO();
		accountDTO2.setBalance("4000");
		accountDTO2.setSecretKey("SDFGsdfgrew43");
		accountDTO2.setPrimaryOwnerId(2L);

		Account checking2 = adminService.createCheckingOrStudChecking(accountDTO2);
		System.out.println("Checking2 from Main: " + accountDTO2);
		System.out.println("Cheking2: " + checking2);

		//Checking > Id3
		AccountDTO accountDTO3 = new AccountDTO();
		accountDTO3.setBalance("6000");
		accountDTO3.setSecretKey("yrutfhFGHJfhj");
		accountDTO3.setPrimaryOwnerId(3L);

		Account checking3 = adminService.createCheckingOrStudChecking(accountDTO3);
		System.out.println("Checking3 from Main: " + accountDTO3);
		System.out.println("Cheking3: " + checking3);

	}
}