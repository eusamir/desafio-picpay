package com.example.picpay_challenger.domain.model.entity;

import com.example.picpay_challenger.domain.enums.UserType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "app_user")
public class User {
    @Id
    private Long id;
    private String name;
    private String email;
    @Enumerated
    private UserType type;
    @OneToOne(mappedBy = "user")
    private Wallet wallet;
}
