package com.scm.scm.repsitories;

import com.scm.scm.entities.Feedback;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepo extends JpaRepository<Feedback, String> {
    Page<Feedback> findByUser(User user, Pageable pageable);
}