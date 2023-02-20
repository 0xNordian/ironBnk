package com.ironbank.proj;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.security.SecurityConfig;
import com.ironbank.proj.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(SecurityConfig.class)
public class AdminControllerTest {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UserService userService;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    private AccountHolder ah1 = null;
    private AccountHolder ah2 = null;


    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        AccountHolder ah1 = new AccountHolder("jose","jose1995","1234",new ArrayList<>(),LocalDate.of(1987,7,15),null,null);
        accountHolderRepository.save(ah1);
        userService.addRoleToUser("Jose1995", "ROLE_ADMIN");

        AccountHolder ah2 = new AccountHolder("Nestor", "Nestor1986", "1234", new ArrayList<>(), LocalDate.of(1987, 7, 15), null, null);
        accountHolderRepository.save(ah2);
        userService.addRoleToUser("Nestor1986", "ROLE_ACCOUNT_HOLDER");
    }

    @Test
    public void create_new_savings() throws Exception {
        SavingsDTO savingsDTO = new SavingsDTO("6000",1L,null,"1357","4321",null);
        String body = objectMapper.writeValueAsString(savingsDTO);

        MvcResult mvcResult = mockMvc.perform(post("/api/admin/savings")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status()
                .isCreated())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
        assertTrue(mvcResult.getResponse().getContentAsString().contains("6000"));
    }

}