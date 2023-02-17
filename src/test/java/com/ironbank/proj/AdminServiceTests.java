package com.ironbank.proj;

import com.ironbank.proj.DTO.SavingsDTO;
import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Savings;
import com.ironbank.proj.models.users.AccountHolder;
import com.ironbank.proj.repository.AccountHolderRepository;
import com.ironbank.proj.repository.SavingsRepository;
import com.ironbank.proj.services.AdminService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AdminServiceTests {

    @Mock
    private AccountHolderRepository accountHolderRepository;

    @Mock
    private SavingsRepository savingsRepository;

    @InjectMocks
    private AdminService adminService;

    @Test
    public void testCreateSavingsAcc() {
        // Create a SavingsDTO object
        SavingsDTO savingsDTO = new SavingsDTO();
        savingsDTO.setBalance("6000");
        savingsDTO.setPrimaryOwnerId(1L);
        savingsDTO.setMinimunBalance("2000");
        savingsDTO.setSecretKey("4321");
        savingsDTO.setInterestRate("0.05");

        // Create an AccountHolder object
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setId(1L);

        // Stub the accountHolderRepository to return the account holder when findById() is called with id = 1
        when(accountHolderRepository.findById(1L)).thenReturn(Optional.of(accountHolder));

        // Create a Savings object
        Savings expectedSavings = new Savings(new Money(new BigDecimal("6000")), "4321", accountHolder, accountHolder, new BigDecimal("0.05"), new BigDecimal("2000"));

        // Stub the savingsRepository to return the expectedSavings object when save() is called with any Savings object
        when(savingsRepository.save(any(Savings.class))).thenReturn(expectedSavings);

        // Call the createSavingsAcc() method of the adminService object and save the returned Savings object
        Savings actualSavings = adminService.createSavingsAcc(savingsDTO);

        // Assert that the actual Savings object is the same as the expected Savings object
        assertEquals(expectedSavings, actualSavings);
    }
}
