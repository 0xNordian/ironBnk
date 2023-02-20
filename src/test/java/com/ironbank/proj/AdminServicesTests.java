package com.ironbank.proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.Checking;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountRepository;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Currency;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AdminServicesTests {
    private static final Currency USD = Currency.getInstance("USD");
    @Autowired
    WebApplicationContext context;
    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    private AccountHolder accountHolder1 = null;
    private AccountHolder accountHolder2 = null;
    private Account account1 = null;
    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        accountHolder1 = new AccountHolder("jose","josesh", "1234", new ArrayList<>(), LocalDate.of(1990,05,27), null, null);
        userService.saveUser(accountHolder1);
        userService.addRoleToUser("josesh", "ROLE_USER");

        accountHolder2 = new AccountHolder("nestor","nestorsh", "1234", new ArrayList<>(), LocalDate.of(2010,05,27), null, null);
        userService.saveUser(accountHolder2);
        userService.addRoleToUser("nestorsh", "ROLE_USER");

        account1 = new Checking(new Money((new BigDecimal("500")),USD),"ASdf",accountHolder1,accountHolder2);
        accountRepository.save(account1);
    }

    @Test
    public void create_new_CheckingAccount() throws Exception {
        AccountDTO accountDto = new AccountDTO("500",1L,null,null,"FRHS","100","0.1","40","1000",null);
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin/checkings")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));
    }

    @Test
    public void create_new_savings() throws Exception {
        AccountDTO accountDto = new AccountDTO("500",accountHolder1.getId(),null,null,"FRHS","100","0.1","40","1000",null);
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin//savings")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));

    }

    @Test
    public void delete_account() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/api/admin/delete/{id}", 1L))
                .andExpect(status()
                .isAccepted())
                .andReturn();
        boolean found = accountRepository.findById(1L).isPresent();
        assertFalse(found);
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

        Savings expectedSavings = new Savings(new Money(new BigDecimal("6000")), "4321", accountHolder, accountHolder, new BigDecimal("0.05"), new BigDecimal("2000"));
        Savings actualSavings = adminService.createSavingsAcc(savingsDTO);
        assertEquals(expectedSavings.toString(), actualSavings.toString());
    }

    @Test
    public void testCreateCreditCardAcc() {
        // Create a SavingsDTO object
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setBalance("6000");
        accountDTO.setPrimaryOwnerId(1L);
        accountDTO.setSecretKey("1234");
        accountDTO.setInterestRate("0.15");
        accountDTO.setCreditLimit("2000");

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1L);

        CreditCard expectedCC = new CreditCard(new Money(new BigDecimal("6000")), "1234", accountHolder, null, new BigDecimal("2000"), new BigDecimal("0.15"));
        CreditCard actualCC = adminService.createCreditCardAccount(accountDTO);
        System.out.println("ACTUALCC: " + actualCC.toString());
        assertEquals(expectedCC.toString(), actualCC.toString());
    }

        /*
    @Test
    public void create_new_student_checking_account() throws Exception {
        // create the account DTO
        AccountDTO accountDto = new AccountDTO("500", accountHolder2.getId(), null, "250", "FRHS", "100", "0.1", "40", "1000", null);

        // perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/api/admin/checkings")
                        .content(objectMapper.writeValueAsString(accountDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // check that the response contains the expected data
        String response = mvcResult.getResponse().getContentAsString();
        System.out.println("RESPONSE: " + response);
        assertTrue(response.contains("\"accountType\":\"STUDENT_CHECKING\""));
        assertTrue(response.contains("\"balance\":{\"currency\":\"USD\",\"amount\":500.00}"));
        assertTrue(response.contains("\"secretKey\":\"FRHS\""));
        assertTrue(response.contains("\"penaltyFee\":{\"currency\":\"USD\",\"amount\":40.00}"));
        assertTrue(response.contains("\"minimumBalance\":250"));

        try {
            // retrieve the created account from the response
            Account createdAccount = objectMapper.readValue(response, Account.class);
            System.out.println( "CREATED ACCOUNT TYPE: " + createdAccount);
            System.out.println( "ACCOUNTTYPE: " + AccountType.STUDENT_CHECKING.toString());

            // check if the created account is an instance of Checking or StudentChecking
            assertEquals(AccountType.STUDENT_CHECKING.toString(), createdAccount.getAccountType());
        } catch (Exception e) {
            e.printStackTrace();
            fail("An exception occurred while parsing the response");
        }
    }
     */
}
