package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.User
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {
    fun findByEmail(email: String): Optional<User>
}