package ar.org.schoolsync.dto.user

import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

typealias AppUser = ar.org.schoolsync.model.User
fun AppUser.toUserDetailsDTO(): UserDetails =
    User.builder()
        .username(this.email)
        .password(this.password)
        .roles(this.role.name)
        .build()