package com.example.withit.grpc

import com.example.withit.Greet3Request
import com.example.withit.Greet3Response
import com.example.withit.Greet3ServiceGrpcKt
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class Greet3Service : Greet3ServiceGrpcKt.Greet3ServiceCoroutineImplBase() {
    override fun greet3(request: Greet3Request): Flow<Greet3Response> = flow {
        List(5) { it }
            .map {
                Greet3Response
                    .newBuilder()
                    .setGreeting("Hello, ${request.name + it.toString()}")
                    .build()
            }
            .forEach {
                delay(200)
                emit(it)
            }
    }
}
