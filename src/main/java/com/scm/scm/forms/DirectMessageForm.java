package com.scm.scm.forms;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DirectMessageForm {
    @NotBlank(message = "Recipient is required")
    private String contactId;

    @NotBlank(message = "Subject is required")
    private String subject;

    @NotBlank(message = "Message cannot be empty")
    private String content;
}