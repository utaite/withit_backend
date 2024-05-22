package com.withit.app.route.planner

import com.withit.app.PlannerReadRequest
import com.withit.app.PlannerReadResponse
import com.withit.app.PlannerServiceGrpcKt
import com.withit.app.plannerReadResponse
import com.withit.app.plannerReadResponseData
import com.withit.app.plannerReadResponsePlan
import com.withit.app.plannerReadResponseSubject
import com.withit.app.response.ResultCode
import com.withit.app.response.ResultType
import com.withit.app.result
import com.withit.app.route.plan.PlanRepository
import com.withit.app.route.planHistory.PlanHistoryRepository
import com.withit.app.route.subject.SubjectRepository
import com.withit.app.route.user.UserRepository
import org.lognet.springboot.grpc.GRpcService
import org.springframework.http.HttpStatus
import java.time.LocalDate

@GRpcService
class PlannerService(
    private val planRepository: PlanRepository,
    private val planHistoryRepository: PlanHistoryRepository,
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository,
) : PlannerServiceGrpcKt.PlannerServiceCoroutineImplBase() {
    override suspend fun read(request: PlannerReadRequest): PlannerReadResponse = runCatching {
        val dateTime = LocalDate.parse(request.data.dateTime)
        val user = requireNotNull(userRepository.findByDeviceToken(request.data.deviceToken))
        val subjects = subjectRepository.findByUserId(user.id)
        val plans = planRepository.findBySubjectIdIn(subjects.map { it.id })
        val planMap = plans.groupBy { it.subject.id }
        val planHistories = planHistoryRepository.findByPlanIdInAndStartedAtBetween(
            plans.map { it.id },
            dateTime.atStartOfDay(),
            dateTime.plusDays(1).atStartOfDay().minusSeconds(1),
        )

        plannerReadResponse {
            data = plannerReadResponseData {
                this.subjects.addAll(
                    planHistories
                        .map { it.plan.subject }
                        .distinct()
                        .map {
                            plannerReadResponseSubject {
                                id = it.id.toLong()
                                name = it.name
                                code = it.code
                                color = it.color.toLong()
                                backgroundColor = it.backgroundColor.toLong()
                                this.plans.addAll(
                                    planMap.getOrDefault(it.id, listOf()).map {
                                        plannerReadResponsePlan {
                                            id = it.id.toLong()
                                            detail = it.detail
                                        }
                                    }
                                )
                            }
                        },
                )
            }
            result = result {
                status = HttpStatus.OK.value()
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        plannerReadResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }
}
