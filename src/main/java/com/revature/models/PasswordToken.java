package com.revature.models;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "reset_password_token")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class PasswordToken {
    @Id
    private String passwordToken;

    @ManyToOne
    @JoinColumn(name = "email", nullable = false)
    private User user;

    @Column(nullable=false)
    private boolean processed;

}