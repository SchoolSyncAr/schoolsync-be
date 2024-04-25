package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

class Teacher (
    override var id: Long,
    var firstName:String,
    var lastName: String,
        ) : Entity {
}