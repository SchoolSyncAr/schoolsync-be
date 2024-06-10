package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.users.Parent
import org.springframework.data.repository.CrudRepository

interface ParentRepository : CrudRepository<Parent, Long>{
}