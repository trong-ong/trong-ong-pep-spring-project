package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;

import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {
    private final AccountService accountService;
    private final MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
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
    public ResponseEntity<Message> postMessages(@RequestBody Message message) {
        try {
            Message postMessage = messageService.postMessage(message);
            return ResponseEntity.ok(postMessage);

        } catch(IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        }
    }

    // GET all /messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> getAllMessages = messageService.getAllMessages(); 
        return ResponseEntity.ok(getAllMessages);
    }
    // GET /messages by ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> getMessagesById(@PathVariable Integer messageId) {
        Message getMessagesById = messageService.getMessagesById(messageId);
        return ResponseEntity.ok(getMessagesById);
    }
    // DELETE /messages/{messageId} by id
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId) {
        Integer deletedRows = messageService.deleteMessagesById(messageId);
        if (deletedRows > 0) {
            return ResponseEntity.ok(deletedRows);
        }
        return ResponseEntity.ok().build();
    }
    // PATCH /messages/{messageId} by id
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> patchMessagesById(@PathVariable Integer messageId, @RequestBody Message message) {
        try {
            Integer patchedRows = messageService.patchMessagesById(messageId, message.getMessageText());
            if (patchedRows > 0) {
                return ResponseEntity.ok(patchedRows);
            }
            return ResponseEntity.ok().build();


        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).build();
        }
    }
    // GET /accounts/{accountId}/messages
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAccountMessagesById(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getAccountMessagesById(accountId));
    }

}
