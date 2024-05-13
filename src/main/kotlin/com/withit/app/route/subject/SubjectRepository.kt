package com.withit.app.route.subject

import com.withit.app.entity.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface SubjectRepository : JpaRepository<Subject, Long>
