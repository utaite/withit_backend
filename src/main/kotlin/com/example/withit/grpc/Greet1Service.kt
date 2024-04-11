package com.example.withit.grpc

import com.example.withit.Greet1Request
import com.example.withit.Greet1Response
import com.example.withit.Greet1ServiceGrpcKt
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class Greet1Service : Greet1ServiceGrpcKt.Greet1ServiceCoroutineImplBase() {
    override suspend fun greet1(request: Greet1Request): Greet1Response = Greet1Response
        .newBuilder()
        .setGreeting("Hello, ${request.name}")
        .build()
}
