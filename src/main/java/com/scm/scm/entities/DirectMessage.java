package com.scm.scm.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "direct_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DirectMessage {
    @Id
    private String id;
    private String subject;
    @Column(length = 2000)
    private String content;
    private LocalDateTime sentAt;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Contact recipient;

    private boolean isRead;
}