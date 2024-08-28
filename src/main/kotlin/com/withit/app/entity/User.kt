package com.withit.app.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(name = "user")
@Entity
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val name: String = "",
    val deviceToken: String = "",
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "user")
    val subject: List<Subject> = listOf(),
)
