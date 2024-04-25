package ar.org.schoolsync.domain

import ar.org.schoolsync.repositories.Entity

class Notification (
    override var id: Long,
    var title: String,
    var content:String
        ) : Entity{
}