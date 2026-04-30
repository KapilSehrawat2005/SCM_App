package com.scm.scm.services.impl;

import com.scm.scm.entities.Feedback;
import com.scm.scm.entities.User;
import com.scm.scm.repsitories.FeedbackRepo;
import com.scm.scm.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Override
    public Feedback submitFeedback(Feedback feedback) {
        feedback.setId(UUID.randomUUID().toString());
        feedback.setSubmittedAt(LocalDateTime.now());
        return feedbackRepo.save(feedback);
    }

    @Override
    public Page<Feedback> getFeedbacksByUser(User user, int page, int size) {
        return feedbackRepo.findByUser(user, PageRequest.of(page, size));
    }
}