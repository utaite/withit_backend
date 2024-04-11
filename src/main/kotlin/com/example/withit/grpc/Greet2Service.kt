package com.example.withit.grpc

import com.example.withit.Greet2Request
import com.example.withit.Greet2Response
import com.example.withit.Greet2ServiceGrpcKt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class Greet2Service : Greet2ServiceGrpcKt.Greet2ServiceCoroutineImplBase() {
    override suspend fun greet2(requests: Flow<Greet2Request>): Greet2Response = Greet2Response
        .newBuilder()
        .setGreeting("Hello, ${requests.take(5).map { it.name }.toList().joinToString { it }}")
        .build()
}
