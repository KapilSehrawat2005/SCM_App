package com.scm.scm.entities;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feedback {
    @Id
    private String id;
    private String subject;
    @Column(length = 2000)
    private String message;
    private LocalDateTime submittedAt;

    @ManyToOne
    private User user;
}