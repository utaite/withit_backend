package com.example.withit.route.user

import com.example.withit.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByDeviceToken(deviceToken: String): User?
}
