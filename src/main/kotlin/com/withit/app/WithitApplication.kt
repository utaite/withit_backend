package com.withit.app

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WithitApplication

fun main(args: Array<String>) {
    runApplication<WithitApplication>(args = args)
}
