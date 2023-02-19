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

        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;

@Service
public class TransferService {

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class);

    @Autowired
    AccountRepository accountRepository;

    public void transferMoneyFromChecking(Long senderAccountId, Long recipientAccountId, BigDecimal amount, String ownerName) {
        logger.info("Transfer request: senderAccountId={}, recipientAccountId={}, amount={}, ownerName={}", senderAccountId, recipientAccountId, amount, ownerName);

        // Find the sender account
        Checking senderChecking = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        logger.info("Sender account: id={}, balance={}, primaryOwner={}, secondaryOwner={}", senderChecking.getId(), senderChecking.getBalance().getAmount(), senderChecking.getPrimaryOwner(), senderChecking.getSecondaryOwner());

        // Find the recipient account
        Checking recipientChecking = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        logger.info("Recipient account: id={}, balance={}, primaryOwner={}, secondaryOwner={}", recipientChecking.getId(), recipientChecking.getBalance().getAmount(), recipientChecking.getPrimaryOwner(), recipientChecking.getSecondaryOwner());

        // Verify that the sender account belongs to the specified owner
        boolean isOwner = senderChecking.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderChecking.getSecondaryOwner() != null && senderChecking.getSecondaryOwner().getName().equalsIgnoreCase(ownerName));

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified owner does not own the sender account");
        }

        logger.info("Owner verified");

        // Verify that the sender account has sufficient funds
        if (senderChecking.getBalance().getAmount().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender account does not have sufficient funds");
        }

        logger.info("Sufficient funds verified");

        // Transfer the money between the accounts
        senderChecking.withdraw(amount);
        recipientChecking.deposit(amount);

        // Save the updated accounts
        accountRepository.saveAll(List.of(senderChecking, recipientChecking));

        logger.info("Transfer completed");

    }

    public void transferMoneyFromSaving(Long senderAccountId, Long recipientAccountId, BigDecimal amount, String ownerName) {
        // Find the sender account
        Savings senderSavings = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        // Find the recipient account
        Savings recipientSavings = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        // Verify that the sender account belongs to the specified owner
        boolean isOwner = senderSavings.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderSavings.getSecondaryOwner() != null && senderSavings.getSecondaryOwner().getName().equalsIgnoreCase(ownerName));

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified owner does not own the sender account");
        }

        // Verify that the sender account has sufficient funds
        if (senderSavings.getBalance().getAmount().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender account does not have sufficient funds");
        }

        // Transfer the money between the accounts
        senderSavings.withdraw(amount);
        recipientSavings.deposit(amount);

        // Save the updated accounts
        accountRepository.saveAll(List.of(senderSavings, recipientSavings));
    }

/*
public class TransferService {

    @Autowired
    AccountRepository accountRepository;

    public void transferMoneyFromChecking(Long senderAccountId, Long recipientAccountId, BigDecimal amount, String ownerName) {
        // Find the sender account
        Checking senderChecking = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        // Find the recipient account
        Checking recipientChecking = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Checking)
                .map(account -> (Checking) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        // Verify that the sender account belongs to the specified owner
        boolean isOwner = senderChecking.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderChecking.getSecondaryOwner() != null && senderChecking.getSecondaryOwner().getName().equalsIgnoreCase(ownerName));

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified owner does not own the sender account");
        }

        // Verify that the sender account has sufficient funds
        if (senderChecking.getBalance().getAmount().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender account does not have sufficient funds");
        }

        // Transfer the money between the accounts
        senderChecking.withdraw(amount);
        recipientChecking.deposit(amount);

        // Save the updated accounts
        accountRepository.saveAll(List.of(senderChecking, recipientChecking));
    }

    public void transferMoneyFromSaving(Long senderAccountId, Long recipientAccountId, BigDecimal amount, String ownerName) {
        // Find the sender account
        Savings senderSavings = accountRepository.findById(senderAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account not found"));

        // Find the recipient account
        Savings recipientSavings = accountRepository.findById(recipientAccountId)
                .filter(account -> account instanceof Savings)
                .map(account -> (Savings) account)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Recipient account not found"));

        // Verify that the sender account belongs to the specified owner
        boolean isOwner = senderSavings.getPrimaryOwner().getName().equalsIgnoreCase(ownerName) ||
                (senderSavings.getSecondaryOwner() != null && senderSavings.getSecondaryOwner().getName().equalsIgnoreCase(ownerName));

        if (!isOwner) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The specified owner does not own the sender account");
        }

        // Verify that the sender account has sufficient funds
        if (senderSavings.getBalance().getAmount().compareTo(amount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The sender account does not have sufficient funds");
        }

        // Transfer the money between the accounts
        senderSavings.withdraw(amount);
        recipientSavings.deposit(amount);

        // Save the updated accounts
        accountRepository.saveAll(List.of(senderSavings, recipientSavings));
    }

 */

}


