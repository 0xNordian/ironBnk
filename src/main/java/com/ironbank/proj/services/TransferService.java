package com.ironbank.proj.services;

        import com.ironbank.proj.models.accounts.Account;
        import com.ironbank.proj.models.accounts.Checking;
        import com.ironbank.proj.models.accounts.Savings;
        import com.ironbank.proj.repository.AccountRepository;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.http.HttpStatus;
        import org.springframework.stereotype.Service;
        import org.springframework.web.server.ResponseStatusException;

        import java.math.BigDecimal;
        import java.util.List;

@Service
public class TransferService {

    @Autowired
    AccountRepository accountRepository;

    public void transferMoney(Long senderAccountId, Long recipientAccountId, BigDecimal amount, String ownerName) {
        // Find the sender account
        Savings senderSavings = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        Checking senderChecking = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        // Find the recipient account
        Savings recipientSavings = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        Checking recipientChecking = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        // Verify that the sender account belongs to the specified owner
        boolean isOwner = senderSavings.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderSavings.getSecondaryOwner() != null && senderSavings.getSecondaryOwner().getName().equalsIgnoreCase(ownerName)) ||
                senderChecking.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderChecking.getSecondaryOwner() != null && senderChecking.getSecondaryOwner().getName().equalsIgnoreCase(ownerName));

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified owner does not own the sender account");
        }

        // Verify that the sender account has sufficient funds
        if (senderSavings.getBalance().getAmount().add(senderChecking.getBalance().getAmount()).compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender account does not have sufficient funds");
        }

        // Transfer the money between the accounts
        if (senderSavings.getBalance().getAmount().compareTo(amount) >= 0) {
            senderSavings.withdraw(amount);
            recipientSavings.deposit(amount);
        } else {
            BigDecimal savingsAmount = senderSavings.getBalance().getAmount();
            BigDecimal checkingAmount = amount.subtract(savingsAmount);
            senderSavings.withdraw(savingsAmount);
            senderChecking.withdraw(checkingAmount);
            recipientChecking.deposit(checkingAmount);
            recipientSavings.deposit(savingsAmount);
        }

        // Save the updated accounts
        accountRepository.saveAll(List.of(senderSavings, senderChecking, recipientSavings, recipientChecking));
    }
}

