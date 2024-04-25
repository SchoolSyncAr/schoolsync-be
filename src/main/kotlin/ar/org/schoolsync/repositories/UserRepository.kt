package ar.org.schoolsync.repositories

import ar.org.schoolsync.domain.User
import org.springframework.stereotype.Repository
@Repository
class UserRepository : CRepository<User>()