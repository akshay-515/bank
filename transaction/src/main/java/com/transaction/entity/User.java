package com.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long  id;
    @Column(name = "name", nullable = false)
    private String username;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
}
