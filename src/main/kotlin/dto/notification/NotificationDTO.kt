package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.enums.NotificationGroup
import java.time.LocalDateTime

data class CreateNotificationDTO (
    val sender: Long,
    val recipientGroups: List<NotificationGroup> = listOf(),
    val recievers: List<Long> = listOf(),
    val title: String,
    val content: String,
    val weight: String
)

data class NotificationDTO (
    val id: Long,
    val title: String,
    val content: String,
    val weight: String,
    val date: LocalDateTime,
    val read: Boolean? = null,
    val pinned: Boolean? = null,
    val sender: String? = null
)

fun NotificationRegistry.toDTO() = NotificationDTO (
    id,
    notification.title,
    notification.content,
    notification.weight.toString(),
    date,
    read,
    pinned,
    notification.senderName
)

fun Notification.toAdminDTO() = NotificationDTO (
    id,
    title,
    content,
    weight.toString(),
    date,
    sender = senderName
)