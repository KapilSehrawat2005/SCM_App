package com.scm.scm.controllers;

import com.scm.scm.entities.User;
import com.scm.scm.helpers.Helper;
import com.scm.scm.services.ContactService;
import com.scm.scm.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private ContactService contactService;

    @RequestMapping("/dashboard")
    public String userDashboard(Model model, Authentication authentication) {
        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);
        long totalContacts = contactService.getByUserId(user.getUserId()).size();
        long favoriteContacts = contactService.getByUserId(user.getUserId()).stream().filter(contact -> contact.isFavorite()).count();
        model.addAttribute("totalContacts", totalContacts);
        model.addAttribute("favoriteContacts", favoriteContacts);
        return "user/dashboard";
    }

    @RequestMapping("/profile")
    public String userProfile() {
        return "user/profile";
    }
}