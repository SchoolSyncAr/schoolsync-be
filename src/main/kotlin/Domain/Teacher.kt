package ar.org.schoolsync.Domain

import ar.org.schoolsync.Repositories.Entity

class Teacher (
    override var id: Int,
    var firstName:String,
    var lastName: String,
        ) : Entity {
}