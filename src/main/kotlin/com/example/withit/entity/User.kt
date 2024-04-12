package com.example.withit.entity

import jakarta.persistence.*

@Table(name = "users")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Int = 0,

    @Column(name = "email")
    val email: String = "",

    @Column(name = "device_token")
    val deviceToken: String = "",
)