package com.withit.app.entity

import com.fasterxml.jackson.annotation.JsonBackReference
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Table(name = "plan")
@Entity
class Plan(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int = 0,
    val durationMinutes: Int = 0,
    val detail: String = "",
    val planType: Int = 0,
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "subject_id", referencedColumnName = "id")
    var subject: Subject = Subject(),
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "plan")
    val planHistory: List<PlanHistory> = listOf(),
)
