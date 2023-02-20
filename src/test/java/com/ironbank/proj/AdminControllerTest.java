/*
package com.ironbank.proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironbank.proj.DTO.AccountDTO;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.CreditCard;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.services.AdminService;
import com.ironbank.proj.services.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AdminControllerTest {
    private MockMvc mockMvc;

    @Mock
    private AdminService adminService;

    @Mock
    private TransferService transferService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        mockMvc = mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .apply(springSecurity())
        .build();
    }

    @Test
    public void createSavingsAccount_shouldReturnCreatedStatus() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO();
        savingsDTO.setPrimaryOwnerId(1L);
        savingsDTO.setBalance("1000");
        savingsDTO.setSecretKey("secret");

        Savings savings = new Savings();
        savings.setId(1L);
        savings.setPrimaryOwner(null);
        savings.setSecondaryOwner(null);
        savings.setBalance(new Money(new BigDecimal(1000));
        savings.setInterestRate(new BigDecimal("0.0025"));
        savings.setMinimumBalance(new BigDecimal("1000"));

        when(adminService.createSavingsAcc(any(SavingsDTO.class))).thenReturn(savings);

        mockMvc.perform(post("/api/admin/savings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(savingsDTO)))
                .andExpect(status().isCreated());
    }

    @Test
    public void createCreditCardAccount_shouldReturnCreatedStatus() throws Exception {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setPrimaryOwnerId(1L);
        accountDTO.setBalance("1000");
        accountDTO.setSecretKey("secret");
        accountDTO.setCreditLimit("1000");
        accountDTO.setInterestRate("0.2");

        CreditCard creditCard = new CreditCard();
        creditCard.setId(1L);
        creditCard.setPrimaryOwner(null);
        creditCard.setSecondaryOwner(null);
        creditCard.setBalance(new Money(new BigDecimal("1000")));
        creditCard.setCreditLimit(new BigDecimal("1000"));
        creditCard.setInterestRate(new BigDecimal("0.2"));

        when(adminService.createCreditCardAccount(any(AccountDTO.class))).thenReturn(creditCard);

        mockMvc.perform(post("/api/admin/creditcard")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated());
    }
}

 */