package com.withit.app.route.user

import com.withit.app.UserCreateRequest
import com.withit.app.UserCreateResponse
import com.withit.app.UserReadRequest
import com.withit.app.UserReadResponse
import com.withit.app.UserServiceGrpcKt
import com.withit.app.entity.User
import com.withit.app.response.ResultCode
import com.withit.app.response.ResultType
import com.withit.app.result
import com.withit.app.userCreateResponse
import com.withit.app.userReadResponse
import com.withit.app.userReadResponseData
import org.lognet.springboot.grpc.GRpcService
import org.springframework.http.HttpStatus

@GRpcService
class UserService(
    private val userRepository: UserRepository,
) : UserServiceGrpcKt.UserServiceCoroutineImplBase() {
    override suspend fun create(request: UserCreateRequest): UserCreateResponse = runCatching {
        require(userRepository.findByDeviceToken(request.data.deviceToken) == null)

        userRepository.save(
            User(
                name = request.data.name,
                deviceToken = request.data.deviceToken,
            ),
        )

        userCreateResponse {
            result = result {
                status = HttpStatus.OK.value()
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        userCreateResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.DIALOG.name
                code = ResultCode.WRONG.name
            }
        }
    }

    override suspend fun read(request: UserReadRequest): UserReadResponse = runCatching {
        val user = requireNotNull(userRepository.findByDeviceToken(request.data.deviceToken))

        userReadResponse {
            data = userReadResponseData {
                id = user.id.toLong()
                name = user.name
                deviceToken = user.deviceToken
            }
            result = result {
                status = HttpStatus.OK.value()
                code = ResultCode.SUCCESS.name
            }
        }
    }.getOrElse {
        userReadResponse {
            result = result {
                status = HttpStatus.OK.value()
                type = ResultType.PAGE.name
                code = ResultCode.NOT_FOUND.name
            }
        }
    }
}
