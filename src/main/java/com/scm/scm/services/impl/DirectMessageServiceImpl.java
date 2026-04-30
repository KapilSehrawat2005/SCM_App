package com.scm.scm.services.impl;

import com.scm.scm.entities.DirectMessage;
import com.scm.scm.entities.User;
import com.scm.scm.repsitories.DirectMessageRepo;
import com.scm.scm.services.DirectMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

    @Autowired
    private DirectMessageRepo messageRepo;

    @Override
    public DirectMessage sendMessage(DirectMessage message) {
        message.setId(UUID.randomUUID().toString());
        message.setSentAt(LocalDateTime.now());
        message.setRead(false);
        return messageRepo.save(message);
    }

    @Override
    public Page<DirectMessage> getMessagesForUser(User user, int page, int size, String sortBy, String direction) {
        Sort sort = direction.equals("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        PageRequest pageable = PageRequest.of(page, size, sort);
        return messageRepo.findBySender(user, pageable);
    }
}