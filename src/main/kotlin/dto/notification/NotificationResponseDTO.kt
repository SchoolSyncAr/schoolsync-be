package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup
import java.time.LocalDateTime

data class NotificationResponseDTO (

    val id: Long,
    val title: String,
    val content: String,
    val notificationSender: Long,
    val notificationReceiver: Long?,//MutableList<Long>?,
    val notificationGroup: MutableList<NotificationGroup>,
    val date: LocalDateTime
)

fun Notification.toResponseDTO() = NotificationResponseDTO (
    this.id,
    this.title,
    this.content,
    this.sender,
    this.recipient,
    this.recipientGroups,
    this.date
)
