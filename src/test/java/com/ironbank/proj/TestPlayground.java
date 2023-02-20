package com.ironbank.proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.models.accounts.Checking;
import com.ironbank.proj.models.accounts.StudentChecking;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountRepository;
import com.ironbank.proj.repository.SavingsRepository;
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
public class TestPlayground {
    private static final Currency USD = Currency.getInstance("USD");
    @Autowired
    WebApplicationContext context;
    @Autowired
    SavingsRepository savingsRepository;
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
    public void create_new_account() throws Exception {
        AccountDTO accountDto = new AccountDTO("500",1L,null,null,"FRHS","100","0.1","40","1000",null);
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin/checkings")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));
    }

    @Test
    public void create_new_student_checking_account() throws Exception {
        // create the account DTO
        AccountDTO accountDto = new AccountDTO("500", 2L, null, null, "FRHS", "100", "0.1", "40", "1000", null);

        // perform the POST request
        MvcResult mvcResult = mockMvc.perform(post("/api/admin/checkings")
                        .content(objectMapper.writeValueAsString(accountDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        // check that the response contains the expected account type
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(response.contains("\"accountType\":\"studentChecking\""));
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
    public void create_new_creditcard() throws Exception {
        AccountDTO accountDto = new AccountDTO("500",accountHolder1.getId(),null,null,"FRHS","100","0.1","40","1000",null);
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin//creditcard")
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
}
