package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity
import java.time.LocalDate
import java.time.Period

class Student (
    override var id: Long,
    var firstName:String,
    var lastName: String,
    var birthDate: LocalDate,
    var enrolledSubjects: List<Subject>,
    var undergradedSubjects: List<Subject>
        ) : Entity {

            fun edad() = Period.between(birthDate, LocalDate.now()).years
}