package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

class Parent(
    override var id: Int,
    var firstName: String,
    var lastName: String,
    var isFatherOf: List<Student>,
    var notifications: List<Notification>,
): Entity {
}