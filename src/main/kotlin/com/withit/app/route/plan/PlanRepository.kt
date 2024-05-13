package com.withit.app.route.plan

import com.withit.app.entity.Plan
import org.springframework.data.jpa.repository.JpaRepository

interface PlanRepository : JpaRepository<Plan, Long>
