package ar.org.schoolsync.model

import jakarta.persistence.*
import java.util.*

@Entity
data class Notification(
    var title: String,
    @Lob
    var content: String,
    //@Enumerated(EnumType.STRING)
    var weight: NotificationWeight = NotificationWeight.LOW){

    private var read = false

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long = 0

    fun read() {
        read = !read
    }
}