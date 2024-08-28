package com.withit.app.route.user

import com.withit.app.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByDeviceToken(deviceToken: String): User?
}
