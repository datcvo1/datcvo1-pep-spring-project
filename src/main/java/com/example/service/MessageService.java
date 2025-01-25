package com.example.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired  
    AccountRepository accRepo;
    @Autowired
    MessageRepository msgRepo;

    public Message postMsg(Message msg) {
        if(!accRepo.existsById(msg.getPostedBy()))
            return null;

        return msgRepo.save(msg);
    }

    public List<Message> getAll() {
        return msgRepo.findAll();
    }

    public Message getById(int id) {
        Optional<Message> msg = msgRepo.findById(id);
        
        if(msg.isPresent())
            return msg.get();
        
        return null;
    }

    @Transactional
    public int deleteById(int id) {
        return msgRepo.deleteByMessageId(id).intValue();
    }

    public int updateById(String text, int id) {
        Optional<Message> msg = msgRepo.findById(id);

        if(msg.isPresent()) {
            msg.get().setMessageText(text);
            msgRepo.save(msg.get());
            return 1;
        }

        return 0;
    }

    public List<Message> byAccId(int id) {
        return msgRepo.findAllByPostedBy(id);
    }
}
