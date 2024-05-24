package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Persons.Parent
import org.springframework.data.repository.CrudRepository
import java.util.UUID

interface ParentRepository : CrudRepository<Parent, Long>{
}