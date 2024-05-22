package com.withit.app.route.planHistory

import com.withit.app.entity.PlanHistory
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface PlanHistoryRepository : JpaRepository<PlanHistory, Long> {
    fun findByPlanIdInAndStartedAtBetween(
        planIds: List<Int>,
        startedAt: LocalDateTime,
        endedAt: LocalDateTime,
    ): List<PlanHistory>
}
