package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.dto.user.UserResponseDTO
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.User
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
    val content: String? = null,
    val weight: String? = null,
    val date: LocalDateTime? = null,
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

fun Notification.toCreateResponse() = NotificationDTO(
    id,
    title
)

fun Notification.toAdminResponse() = NotificationDTO (
    id,
    title,
    content,
    weight.toString(),
    date,
    sender = senderName
)

fun NotificationRegistry.toCreateResponse() = NotificationDTO(
    id,
    notification.title
)