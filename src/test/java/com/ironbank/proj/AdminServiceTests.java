
package com.ironbank.proj;

import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.CreditCardRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
public class AdminServiceTests {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    UserService userService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AdminService adminService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        AccountHolder ah1 = new AccountHolder("jose","jose1995","1234",new ArrayList<>(),LocalDate.of(1987,7,15),null,null);
        accountHolderRepository.save(ah1);
        userService.addRoleToUser("Jose1995", "ROLE_ADMIN");

        AccountHolder ah2 = new AccountHolder("Nestor", "Nestor1986", "1234", new ArrayList<>(), LocalDate.of(1987, 7, 15), null, null);
        accountHolderRepository.save(ah2);
        userService.addRoleToUser("Nestor1986", "ROLE_ACCOUNT_HOLDER");
    }

    @Test
    public void testCreateSavingsAcc() {
        // Create a SavingsDTO object
        SavingsDTO savingsDTO = new SavingsDTO();
            savingsDTO.setBalance("6000");
            savingsDTO.setPrimaryOwnerId(1L);
            savingsDTO.setMinimunBalance("2000");
            savingsDTO.setSecretKey("4321");
            savingsDTO.setInterestRate("0.05");

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1L);

        //when(accountHolderRepository.findById(1L)).thenReturn(Optional.of(accountHolder));
        Savings expectedSavings = new Savings(new Money(new BigDecimal("6000")), "4321", accountHolder, accountHolder, new BigDecimal("0.05"), new BigDecimal("2000"));
        //when(savingsRepository.save(any(Savings.class))).thenReturn(expectedSavings);
        Savings actualSavings = adminService.createSavingsAcc(savingsDTO);
        assertEquals(expectedSavings.toString(), actualSavings.toString());
    }

    @Test
    public void testCreateCreditCardAcc2() {
        // Create a SavingsDTO object
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance("6000");
        accountDTO.setPrimaryOwnerId(1L);
        accountDTO.setSecretKey("1234");
        accountDTO.setInterestRate("0.15");
        accountDTO.setCreditLimit("2000");

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1L);

        //when(accountHolderRepository.findById(1L)).thenReturn(Optional.of(accountHolder));
        CreditCard expectedCC = new CreditCard(new Money(new BigDecimal("6000")), "1234", accountHolder, null, new BigDecimal("2000"), new BigDecimal("0.15"));
        //when(creditCardRepository.save(any(CreditCard.class))).thenReturn(expectedCC);
        CreditCard actualCC = adminService.createCreditCardAccount(accountDTO);
        assertEquals(expectedCC.toString(), actualCC.toString());
    }

    @Test
    public void testCreateCreditCardAccount() {
        // create a new accountDTO
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setPrimaryOwnerId(1L);
        accountDTO.setSecondaryOwnerId(null);
        accountDTO.setBalance("0");
        accountDTO.setSecretKey("secretKey");
        accountDTO.setCreditLimit("1000");
        accountDTO.setInterestRate("0.15");

        // call createCreditCardAccount() to create a new credit card account
        CreditCard creditCard = adminService.createCreditCardAccount(accountDTO);

        // assert that the credit card account was created successfully
        assertEquals(accountDTO.getSecretKey(), creditCard.getSecretKey());
        assertEquals(new BigDecimal(accountDTO.getBalance()), creditCard.getBalance().getAmount());
        assertEquals(new BigDecimal(accountDTO.getCreditLimit()), creditCard.getCreditLimit());
        assertEquals(new BigDecimal(accountDTO.getInterestRate()), creditCard.getInterestRate());
        assertEquals(1L, creditCard.getPrimaryOwner().getId());
        assertEquals(null, creditCard.getSecondaryOwner());
        assertEquals(creditCard, creditCardRepository.findById(creditCard.getId()).orElse(null));
    }
}
