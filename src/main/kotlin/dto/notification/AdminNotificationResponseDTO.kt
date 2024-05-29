package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup
import java.time.LocalDateTime

data class AdminNotificationResponseDTO (
    val id: Long,
    val title: String,
    val content: String,
    val sender: Long,
    val weight: String,
    val date: LocalDateTime,
    val group: MutableList<NotificationGroup>,
    val reciever: Long? = null//MutableList<Long>?,
)

fun Notification.toAdminResponseDTO() = AdminNotificationResponseDTO (
    this.id,
    this.title,
    this.content,
    this.sender,
    this.weight.name,
    this.date,
    this.recipientGroups,
    this.recipient
)

data class UserNotificationResponseDTO (
    val id: Long,
    val title: String,
    val content: String,
    val weight: String,
    val date: LocalDateTime
)

fun Notification.toUserResponseDTO() = UserNotificationResponseDTO (
    this.id,
    this.title,
    this.content,
    this.weight.name,
    this.date
)
