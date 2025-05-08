package com.example.picpay_challenger.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "user_wallet")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Wallet {
    @Id
    private UUID id;
    private BigDecimal balance;
    @OneToOne
    @JoinColumn(name = "user_id")
    private  User user;

    public Wallet(BigDecimal balance, User user){
        this.id = UUID.randomUUID();
        this.balance= balance;
        this.user = user;
    }
}
