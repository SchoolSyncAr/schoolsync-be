package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotScope
import ar.org.schoolsync.model.NotificationGroup
import java.util.*
import ar.org.schoolsync.model.User
import java.time.LocalDateTime

data class NotificationResponseDTO (

    val id: Long,
    val title: String,
    val content: String,
    val notificationSender: Long,
    val notificationReceiver: MutableList<String>, //UUID,
    val notificationGroup: MutableList<NotificationGroup>,
    val date: LocalDateTime
)

fun Notification.toResponseDTO() = NotificationResponseDTO (
    this.id,
    this.title,
    this.content,
    this.notificationSender,
    this.notificationReceiver,
    this.notificationGroup,
    this.date
)
