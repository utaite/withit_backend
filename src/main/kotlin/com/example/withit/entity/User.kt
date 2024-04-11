package com.example.withit.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id

@Entity
class User(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val id: Long = 0,

    val name: String = "",

    val deviceToken: String = "",
)
