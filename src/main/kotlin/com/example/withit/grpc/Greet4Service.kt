package com.example.withit.grpc

import com.example.withit.Greet4Request
import com.example.withit.Greet4Response
import com.example.withit.Greet4ServiceGrpcKt
import kotlinx.coroutines.flow.*
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class Greet4Service : Greet4ServiceGrpcKt.Greet4ServiceCoroutineImplBase() {
    override fun greet4(requests: Flow<Greet4Request>): Flow<Greet4Response> = flow {
        requests
            .withIndex()
            .map {
                Greet4Response
                    .newBuilder()
                    .setGreeting("Hello, ${it.value.name + it.index.toString()}")
                    .build()
            }
            .collect {
                emit(it)
            }
    }
}
