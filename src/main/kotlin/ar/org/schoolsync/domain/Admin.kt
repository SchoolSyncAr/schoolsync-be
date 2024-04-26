package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

class Admin (
        override var id: Long = 0,
        override val firstName: String,
        override val lastName: String,
        override val username: String,
        override var password: String,

) : User {
        override var userStatus: UserStatus = AdminStatus()
}