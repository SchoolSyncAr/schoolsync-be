package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

interface User: Entity {
    val firstName: String
    val lastName: String
    val username: String
    var password: String
    var userStatus: UserStatus

    fun currentStatus() = userStatus

    fun changeStatus(newStatus: UserStatus) {
        userStatus = newStatus
    }
}