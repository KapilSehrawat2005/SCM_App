package com.scm.scm.services;

import com.scm.scm.entities.DirectMessage;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;

public interface DirectMessageService {
    DirectMessage sendMessage(DirectMessage message);
    Page<DirectMessage> getMessagesForUser(User user, int page, int size, String sortBy, String direction);
}