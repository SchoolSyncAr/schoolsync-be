package ar.org.schoolsync.repositories

import ar.org.schoolsync.model.Persons.Person
import org.springframework.data.repository.CrudRepository
import java.util.*

interface PersonRepository : CrudRepository<Person, UUID>
