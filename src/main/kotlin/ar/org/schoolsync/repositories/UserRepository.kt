package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.User
import ar.org.schoolsync.model.enums.Role
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UserRepository : CrudRepository<User, Long> {
    @EntityGraph(attributePaths = ["notificationGroups"])
    fun findByEmail(email: String): Optional<User>

    @EntityGraph(attributePaths = ["childrens", "notificationGroups"])
    override fun findById(id: Long): Optional<User>

    @EntityGraph(attributePaths = ["notificationGroups"])
    override fun findAll(): Iterable<User>

    @EntityGraph(attributePaths = ["childrens", "notificationGroups"])
    fun findAllByRole(role: Role): Iterable<User>
}