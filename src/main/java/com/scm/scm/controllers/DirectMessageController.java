package com.scm.scm.controllers;

import com.scm.scm.entities.Contact;
import com.scm.scm.entities.DirectMessage;
import com.scm.scm.entities.User;
import com.scm.scm.forms.DirectMessageForm;
import com.scm.scm.helpers.Helper;
import com.scm.scm.helpers.Message;
import com.scm.scm.helpers.MessageType;
import com.scm.scm.services.ContactService;
import com.scm.scm.services.DirectMessageService;
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
@RequestMapping("/user/direct-message")
public class DirectMessageController {

    @Autowired
    private DirectMessageService messageService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String messagePage(Model model, Authentication authentication,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "10") int size) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        Page<DirectMessage> messages = messageService.getMessagesForUser(user, page, size, "sentAt", "desc");
        model.addAttribute("messages", messages);
        model.addAttribute("contacts", contactService.getByUserId(user.getUserId()));
        model.addAttribute("messageForm", new DirectMessageForm());
        return "user/direct_message";
    }

    @PostMapping("/send")
    public String sendMessage(@Valid @ModelAttribute("messageForm") DirectMessageForm form,
                              BindingResult result,
                              Authentication authentication,
                              HttpSession session) {
        if (result.hasErrors()) {
            session.setAttribute("message", Message.builder().content("Please fill all fields correctly").type(MessageType.red).build());
            return "redirect:/user/direct-message";
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User sender = userService.getUserByEmail(email);
        Contact recipient = contactService.getById(form.getContactId());
        if (recipient == null || !recipient.getUser().getUserId().equals(sender.getUserId())) {
            session.setAttribute("message", Message.builder().content("Invalid recipient").type(MessageType.red).build());
            return "redirect:/user/direct-message";
        }
        DirectMessage msg = DirectMessage.builder()
                .sender(sender)
                .recipient(recipient)
                .subject(form.getSubject())
                .content(form.getContent())
                .build();
        messageService.sendMessage(msg);
        session.setAttribute("message", Message.builder().content("Message sent successfully!").type(MessageType.green).build());
        return "redirect:/user/direct-message";
    }
}