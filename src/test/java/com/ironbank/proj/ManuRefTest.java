/*
package com.ironhack.demosecurityjwt.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.demosecurityjwt.dtos.AccountDto;
import com.ironhack.demosecurityjwt.models.Account;
import com.ironhack.demosecurityjwt.models.AccountHolder;
import com.ironhack.demosecurityjwt.models.Address;
import com.ironhack.demosecurityjwt.models.Money;
import com.ironhack.demosecurityjwt.repositories.AccountRepository;
import com.ironhack.demosecurityjwt.repositories.SavingsRepository;
import com.ironhack.demosecurityjwt.services.AdminService;
import com.ironhack.demosecurityjwt.services.impl.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ManuRefTest {

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
        accountHolder1 = new AccountHolder("manuel","manuelsh", "1234", new ArrayList<>(), LocalDate.of(1990,05,27), new Address("calle", 23, "barcelona", 899), null);
        userService.saveUser(accountHolder1);
        userService.addRoleToUser("manuelsh", "ROLE_USER");

        accountHolder2 = new AccountHolder("nestor","nestorsh", "1234", new ArrayList<>(), LocalDate.of(2010,05,27), new Address("calle", 23, "barcelona", 899), null);
        userService.saveUser(accountHolder2);
        userService.addRoleToUser("nestorsh", "ROLE_USER");

        account1 = new Account(new Money(new BigDecimal("500")), accountHolder1, null);
        accountRepository.save(account1);
    }

    @Test
    public void create_new_account() throws Exception {
        AccountDto accountDto = new AccountDto("500", accountHolder1.getId(), null, null, 1234L, null, null );
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/checking")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));

    }

    @Test
    public void create_new_savings() throws Exception {
        AccountDto accountDto = new AccountDto("500", accountHolder2.getId(), null, "250", 1234L, null, null );
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/savings")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));

    }

    @Test
    public void create_new_creditcard() throws Exception {
        AccountDto accountDto = new AccountDto("500", accountHolder1.getId(), null, null, 1234L, null, "600" );
        String body = objectMapper.writeValueAsString(accountDto);

        MvcResult mvcResult = mockMvc.perform(post("/creditcard")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("500"));

    }

    @Test
    public void delete_account() throws Exception{
        MvcResult mvcResult = mockMvc.perform(delete("/delete/account?id=1")).andExpect(status().isOk()).andReturn();
        boolean found = accountRepository.findById(1L).isPresent();

        assertFalse(found);
    }

    //arreglar test update
    /*@Test
    public void testUpdateBalance() throws Exception {
        //account1.getPrimaryOwner().getId();
        account1.setBalance(new Money(new BigDecimal("1000")));
        account1.setPrimaryOwner(accountHolder1);
        MvcResult createResult = mockMvc.perform(MockMvcRequestBuilders.post("/checking")
                        .content(objectMapper.writeValueAsString(account1))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Long accountId = objectMapper.readValue(createResult.getResponse().getContentAsString(), Account.class).getPrimaryOwner().getId();
        // Update the account balance
        Money newBalance = new Money(new BigDecimal("200.00"));
        MvcResult updateResult = mockMvc.perform(MockMvcRequestBuilders.post("/update_balance")
                        .param("id", accountId.toString())
                        .param("newBalance", newBalance.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        // Verify the response
        Money updatedBalance = new Money(new BigDecimal(updateResult.getResponse().getContentAsString()));
        assertEquals(newBalance, updatedBalance);
        // Clean up the test data
        mockMvc.perform(MockMvcRequestBuilders.delete("/delete/account")
                        .param("id", accountId.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
 */
