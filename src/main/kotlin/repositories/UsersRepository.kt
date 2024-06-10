package ar.org.schoolsync.repositories


import ar.org.schoolsync.model.users.User
import org.springframework.data.repository.CrudRepository

interface UsersRepository : CrudRepository<User, Long>
