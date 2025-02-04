package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    // private final MessageService messageService;

    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
        // this.messageService = messageService;
    }
    // POST /register
    @PostMapping("/register")
    public ResponseEntity<Account> postRegister(@RequestBody Account account) {
        if (accountService.userNameExist(account.getUsername())) {
            return ResponseEntity.status(409).build();
        }
        try {
            Account createdAccount = accountService.createAccount(account);
            return ResponseEntity.ok(createdAccount);

        } catch(IllegalArgumentException e ) {
            return ResponseEntity.status(400).build();
        }

    }
    // POST /login
    @PostMapping("/login")
    public ResponseEntity<Account> postLogin(@RequestBody Account account) {
        try {
            Account loginAccount = accountService.loginAccount(account);
            return ResponseEntity.ok(loginAccount);
        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(401).build();
        }
    }
    // POST /message
    @PostMapping("/messages")
    public ResponseEntity<Message> postMessage(@RequestBody Message message) {
        return null;
    }

    // GET all /messages
    // GET /messages by ID
    // DELETE /messages/{messageId} by id
    // PATCH /messages/{messageId} by id
    // GET /accounts/{accountId}/messages

}
