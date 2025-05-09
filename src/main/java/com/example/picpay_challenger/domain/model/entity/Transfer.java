package com.example.picpay_challenger.domain.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "user_transfer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;
    private Timestamp date;
    private BigDecimal amount;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private User receiver;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;
}
