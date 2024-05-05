package ar.org.schoolsync.model

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class EntityFactory(private val encoder: PasswordEncoder) {
    fun createUser(type: Role) = when (type) {
        Role.USER -> NormalUser().build(encoder)
        Role.ADMIN -> AdminUser().build(encoder)
        Role.STUDENT -> TODO()
        Role.TEACHER -> TODO()
        Role.PARENT -> TODO()
    }
}

interface FactoryObject<T> {
    fun build(encoder: PasswordEncoder): T
}

class AdminUser : FactoryObject<User> {
    override fun build(encoder: PasswordEncoder) =
        User(
            firstName = "Admin",
            lastName = "User",
            email = "adminuser@schoolsync.mail.com",
            password = encoder.encode("adminuser"),
            ).apply { role = Role.ADMIN }
}

class NormalUser : FactoryObject<User> {
    override fun build(encoder: PasswordEncoder) =
        User(
            firstName = "Common",
            lastName = "User",
            email = "commonuser@schoolsync.mail.com",
            password = encoder.encode("commonuser"),
        )
}