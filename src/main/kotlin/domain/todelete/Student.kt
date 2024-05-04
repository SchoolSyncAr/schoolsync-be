//package ar.org.schoolsync.domain
//
//import ar.org.schoolsync.repositories.Entity
//import java.time.LocalDate
//import java.time.Period
//
//class Student (
//    override var id: Int,
//    var firstName:String,
//    var lastName: String,
//    var birthDate: LocalDate,
//    var enrolledSubjects: List<ar.org.schoolsync.domain.todelete.Subject>,
//    var undergradedSubjects: List<ar.org.schoolsync.domain.todelete.Subject>
//        ) : ar.org.schoolsync.repositories.Entity {
//
//            fun edad() = Period.between(birthDate, LocalDate.now()).years
//}