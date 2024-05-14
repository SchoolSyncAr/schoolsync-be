package ar.org.schoolsync.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Notification(
    @Id
    var id: Long = 0,
    var title: String,
    var content: String,
    var weight: NotificationWeight = NotificationWeight.LOW){

    private var read = false

    fun read() {
        read = !read
    }
}