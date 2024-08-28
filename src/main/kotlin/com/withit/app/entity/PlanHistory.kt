package com.withit.app.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "plan_history")
@Entity
class PlanHistory(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val startedAt: LocalDateTime,
    val endedAt: LocalDateTime,
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "plan_id", referencedColumnName = "id")
    val plan: Plan = Plan(),
)
