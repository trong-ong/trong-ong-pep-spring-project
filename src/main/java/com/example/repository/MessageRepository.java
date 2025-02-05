package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.*;

import javax.transaction.Transactional;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // GET postBy user exist
    @Query("SELECT m FROM Message m WHERE m.postedBy = :accountId")
    Message findPostByExist(@Param("accountId") Integer accountId);

    // GET all messages
    @Query("SELECT m FROM Message m")
    List<Message> findAllMessages();

    // GET messages by Id
    @Query("SELECT m FROM Message m WHERE m.messageId = :messageId")
    Message findMessageById(@Param("messageId") Integer messageId);

    // DEL messages by Id
    @Modifying
    @Transactional
    @Query("DELETE FROM Message m WHERE m.messageId = :messageId")
    Integer deleteMessageById(@Param("messageId") Integer messageId);

    // update messagese by id
    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = :messageText WHERE m.messageId = :messageId")
    Integer patchMessagesById(@Param("messageId") Integer messageId, @Param("messageText") String messageText);

    // Get messages by account id
    @Query("SELECT m FROM Message m WHERE m.postedBy = :accountId")
    List<Message> findAccountMessagesById(@Param("accountId") Integer accountId);

}

