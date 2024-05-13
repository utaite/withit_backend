package com.withit.app.route.planHistory

import com.withit.app.entity.PlanHistory
import org.springframework.data.jpa.repository.JpaRepository

interface PlanHistoryRepository : JpaRepository<PlanHistory, Long>
