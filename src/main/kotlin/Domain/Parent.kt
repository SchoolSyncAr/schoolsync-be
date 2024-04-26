package ar.org.schoolsync.Domain

import ar.org.schoolsync.Repositories.Entity

class Parent(
    override var id: Int,
    var firstName: String,
    var lastName: String,
    var isFatherOf: List<Student>,
    var notifications: List<Notification>,
): Entity {
}