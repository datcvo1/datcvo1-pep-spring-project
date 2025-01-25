package com.example.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @Autowired
    AccountService accService;
    @Autowired
    MessageService msgService;

    @PostMapping("/register")
    public ResponseEntity registration(@RequestBody Account body){
        Account newAcc = accService.registration(body);

        if(newAcc == null)
            return ResponseEntity.status(409).body("Username already exists.");
        else if(newAcc.getUsername().isEmpty())
            return ResponseEntity.status(400).body("Username cannot be blank.");
        
        return ResponseEntity.status(200).body(newAcc);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody Account body){
        Account acc = accService.login(body);

        if(acc == null)
            return ResponseEntity.status(401).body("Invalid credentials.");
        
        return ResponseEntity.status(200).body(acc);
    }

    @PostMapping("/messages")
    public ResponseEntity postMsg(@RequestBody Message body){
        if(body.getMessageText().isBlank())
            return ResponseEntity.status(400).body("Message cannot be blank.");
        if(body.getMessageText().length() > 255)
            return ResponseEntity.status(400).body("Message is longer than 255 characters.");
        
        Message newMsg = msgService.postMsg(body);
        
        if(newMsg == null)
            return ResponseEntity.status(400).body("User does not exists");
            
        return ResponseEntity.status(200).body(newMsg);
    }

    @GetMapping("/messages")
    public ResponseEntity getMsg(){
        return ResponseEntity.status(200).body(msgService.getAll());
    }

    @GetMapping("/messages/{messageId}")
    public ResponseEntity getMsgById(@PathVariable int messageId){
        return ResponseEntity.status(200).body(msgService.getById(messageId));
    }

    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity deleteMsgById(@PathVariable int messageId){
        int deleted = msgService.deleteById(messageId);

        if(deleted > 0)
            return ResponseEntity.status(200).body(deleted);
        
        return null;
    }

    @PatchMapping("/messages/{messageId}")
    public ResponseEntity updateMsgById(@PathVariable int messageId, @RequestBody Message text){
        if(text.getMessageText().isBlank())
            return ResponseEntity.status(400).body("Message cannot be blank.");
        if(text.getMessageText().length() > 255)
            return ResponseEntity.status(400).body("Message is longer than 255 characters.");

        int updated = msgService.updateById(text.getMessageText(), messageId);

        if(updated > 0)
            return ResponseEntity.status(200).body(updated);

        return ResponseEntity.status(400).body("");
    }

    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity msgByAccId(@PathVariable int accountId){
        return ResponseEntity.status(200).body(msgService.byAccId(accountId));
    }
}
