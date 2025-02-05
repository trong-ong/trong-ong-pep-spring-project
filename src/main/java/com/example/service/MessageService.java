package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    // POST /message
    public Message postMessage(Message message) {
        // is blank
        if (message.getMessageText() == null || message.getMessageText().isBlank()) {
            throw new IllegalArgumentException();
        }
        // is over 255 char
        if (message.getMessageText().length() > 255) {
            throw new IllegalArgumentException();
        }
        // postBy refers to existing user
        if(messageRepository.findPostByExist(message.getPostedBy()) == null) {
            throw new IllegalArgumentException();
        }
        return messageRepository.save(message);
    }
    // GET all /messages
    public List<Message> getAllMessages() {
        return messageRepository.findAllMessages();
    }
    // GET /messages by ID
    public Message getMessagesById(Integer messageId) {
        return messageRepository.findMessageById(messageId);
    }
    // DELETE /messages/{messageId} by id
    public Integer deleteMessagesById(Integer messageId) {
        return messageRepository.deleteMessageById(messageId);
    }
    // PATCH /messages/{messageId} by id
    public Integer patchMessagesById(Integer messageId, String messageText) {
        // is blank
        if (messageText == null || messageText.isBlank()) {
            throw new IllegalArgumentException();
        }
        // is more than 255 char
        if (messageText.length() > 255) {
            throw new IllegalArgumentException();
        }
        // if message id exist
        if (messageRepository.findMessageById(messageId) == null) {
            throw new IllegalArgumentException();
        }
        return messageRepository.patchMessagesById(messageId, messageText);

    }
    // GET /accounts/{accountId}/messages
    public List<Message> getAccountMessagesById(Integer accountId) {
        return messageRepository.findAccountMessagesById(accountId);
    }
}
