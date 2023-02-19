/*
package com.ironbank.proj.controllers;

import com.ironbank.proj.models.Money;
import com.ironbank.proj.models.accounts.Account;
import com.ironbank.proj.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/{originAccountId}/transfer")
    public ResponseEntity<?> transferMoney(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long originAccountId,
            @RequestParam String recipientOwnerName,
            @RequestParam Long recipientAccountId,
            @RequestBody Money amount) {

        // Check that origin account belongs to the authenticated user
        Account originAccount = accountService.findByIdAndPrimaryOwnerUsernameOrSecondaryOwnerUsername(originAccountId, userDetails.getUsername());
        if (originAccount == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Origin account not found");
        }

        // Transfer funds
        try {
            accountService.transferMoney(originAccount, recipientOwnerName, recipientAccountId, amount);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Transfer successful");
    }
}

 */

