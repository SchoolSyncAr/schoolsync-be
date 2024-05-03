package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

class Teacher (
    override var id: Int,
    var firstName:String,
    var lastName: String,
        ) : Entity {
}