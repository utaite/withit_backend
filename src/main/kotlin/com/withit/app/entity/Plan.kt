package com.withit.app.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Table(name = "plan")
@Entity
class Plan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val subjectId: Int = 0,
    val durationMinutes: Int = 0,
    val detail: String = "",
    val planType: Int = 0,
)
