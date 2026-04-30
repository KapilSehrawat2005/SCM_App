package com.scm.scm.services;

import com.scm.scm.entities.Feedback;
import com.scm.scm.entities.User;
import org.springframework.data.domain.Page;

public interface FeedbackService {
    Feedback submitFeedback(Feedback feedback);
    Page<Feedback> getFeedbacksByUser(User user, int page, int size);
}