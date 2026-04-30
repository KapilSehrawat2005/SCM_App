package com.scm.scm.repsitories;

import com.scm.scm.entities.DirectMessage;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectMessageRepo extends JpaRepository<DirectMessage, String> {
    Page<DirectMessage> findBySender(User sender, Pageable pageable);
}