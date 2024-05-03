package ar.org.schoolsync.domain

import ar.org.schoolsync.ar.org.schoolsync.domain.NotificationWeight
import ar.org.schoolsync.repositories.Entity

class Notification (
    override var id: Int,
    var title: String,
    var content:String,
    var weight: NotificationWeight = NotificationWeight.LOW) : Entity{

    private var read = false

    fun read() {
        read = !read
    }


}