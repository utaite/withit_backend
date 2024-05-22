package com.withit.app.route.home

import com.withit.app.HomeDeleteRequest
import com.withit.app.HomeDeleteResponse
import com.withit.app.HomeReadRequest
import com.withit.app.HomeReadResponse
import com.withit.app.HomeServiceGrpcKt
import com.withit.app.HomeUpdateRequest
import com.withit.app.HomeUpdateResponse
import com.withit.app.homeDeleteResponse
import com.withit.app.homeReadResponse
import com.withit.app.homeReadResponseData
import com.withit.app.homeReadResponsePlan
import com.withit.app.homeReadResponseSubject
import com.withit.app.homeUpdateResponse
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
class HomeService(
    private val planRepository: PlanRepository,
    private val planHistoryRepository: PlanHistoryRepository,
    private val subjectRepository: SubjectRepository,
    private val userRepository: UserRepository,
) : HomeServiceGrpcKt.HomeServiceCoroutineImplBase() {
    override suspend fun read(request: HomeReadRequest): HomeReadResponse = runCatching {
        val dateTime = LocalDate.parse(request.data.dateTime)
        val user = requireNotNull(userRepository.findByDeviceToken(request.data.deviceToken))
        val subjects = subjectRepository.findByUserId(user.id)
        val plans = planRepository.findBySubjectIdIn(subjects.map { it.id })
        val planHistories = planHistoryRepository.findByPlanIdInAndStartedAtBetween(
            plans.map { it.id },
            dateTime.atStartOfDay(),
            dateTime.plusDays(1).atStartOfDay().minusSeconds(1),
        )

        homeReadResponse {
            data = homeReadResponseData {
                this.subjects.addAll(
                    subjects
                        .map {
                            homeReadResponseSubject {
                                id = it.id.toLong()
                                name = it.name
                                code = it.code
                                color = it.color.toLong()
                                backgroundColor = it.backgroundColor.toLong()
                            }
                        },
                )
                this.plans.addAll(
                    planHistories
                        .reversed()
                        .map {
                            homeReadResponsePlan {
                                id = it.id.toLong()
                                name = it.plan.subject.name
                                detail = it.plan.detail
                                startedAt = it.startedAt.toString()
                                endedAt = it.endedAt.toString()
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
        homeReadResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }

    override suspend fun update(request: HomeUpdateRequest): HomeUpdateResponse = runCatching {
        planRepository.save(
            planHistoryRepository.findById(request.data.planId).get().plan.apply {
                subject = subjectRepository.findById(request.data.subjectId).get()
            },
        )

        homeUpdateResponse {
            result = result {
                status = HttpStatus.OK.value()
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        homeUpdateResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }

    override suspend fun delete(request: HomeDeleteRequest): HomeDeleteResponse = runCatching {
        planHistoryRepository.deleteById(request.data.planId)

        homeDeleteResponse {
            result = result {
                status = HttpStatus.OK.value()
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        homeDeleteResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }
}
