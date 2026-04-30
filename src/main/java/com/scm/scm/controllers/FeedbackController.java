package com.scm.scm.controllers;

import com.scm.scm.entities.Feedback;
import com.scm.scm.entities.User;
import com.scm.scm.forms.FeedbackForm;
import com.scm.scm.helpers.Helper;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.FeedbackService;
import com.scm.scm.services.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String feedbackPage(Model model, Authentication authentication,
                               @RequestParam(defaultValue = "0") int page) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        Page<Feedback> feedbacks = feedbackService.getFeedbacksByUser(user, page, 10);
        model.addAttribute("feedbacks", feedbacks);
        model.addAttribute("feedbackForm", new FeedbackForm());
        return "user/feedback";
    }

    @PostMapping("/submit")
    public String submitFeedback(@Valid @ModelAttribute("feedbackForm") FeedbackForm form,
                                 BindingResult result,
                                 Authentication authentication,
                                 HttpSession session) {
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder().content("Please fill subject and message").type(MessageType.red).build());
            return "redirect:/user/feedback";
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        Feedback feedback = Feedback.builder()
                .user(user)
                .subject(form.getSubject())
                .message(form.getMessage())
                .build();
        feedbackService.submitFeedback(feedback);
        session.setAttribute("message", Message.builder().content("Thank you for your feedback!").type(MessageType.green).build());
        return "redirect:/user/feedback";
    }
}