package ar.org.schoolsync.domain

import org.springframework.stereotype.Component


@Component
class EntityFactory {
    fun createUser(type: Role) = when (type) {
        Role.USER -> NormalUser().build()
        Role.ADMIN -> AdminUser().build()
        Role.STUDENT -> TODO()
        Role.TEACHER -> TODO()
        Role.PARENT -> TODO()
    }
}

interface FactoryObject<T> {
    fun build(): T
}

class AdminUser : FactoryObject<User> {
    override fun build() =
        User(
            firstName = "Admin",
            lastName = "User",
            email = "adminuser@schoolsync.mail.com",
            password = "adminuser",
            roles = listOf(Role.USER, Role.ADMIN)
        )
}

class NormalUser : FactoryObject<User> {
    override fun build() =
        User(
            firstName = "Common",
            lastName = "User",
            email = "commonuser@schoolsync.mail.com",
            password = "commonuser",
            roles = listOf(Role.USER)
        )
}