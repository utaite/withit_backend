package com.example.withit.route.user

import com.example.withit.UserCreateRequest
import com.example.withit.UserCreateResponse
import com.example.withit.UserReadRequest
import com.example.withit.UserReadResponse
import com.example.withit.UserServiceGrpcKt
import com.example.withit.response.ResultCode
import com.example.withit.response.ResultType
import com.example.withit.result
import com.example.withit.userCreateResponse
import com.example.withit.userReadResponse
import com.example.withit.userReadResponseData
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class UserService(
    private val userRepository: UserRepository,
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    override suspend fun signUp(request: UserCreateRequest): UserCreateResponse = runCatching {
        userCreateResponse {
            result = result {
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        userCreateResponse {
            result = result {
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }

    override suspend fun signIn(request: UserReadRequest): UserReadResponse = runCatching {
        val user = requireNotNull(userRepository.findByDeviceToken(request.deviceToken))

        userReadResponse {
            data = userReadResponseData {
                id = user.id
                name = user.name
                deviceToken = user.deviceToken
            }
            result = result {
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        userReadResponse {
            result = result {
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }
}
