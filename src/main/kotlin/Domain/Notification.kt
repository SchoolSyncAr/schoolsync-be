package ar.org.schoolsync.Domain

import ar.org.schoolsync.Repositories.Entity

class Notification (
    override var id: Int,
    var title: String,
    var content:String
        ) : Entity{
}