package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup
import java.time.LocalDate
import java.time.LocalDateTime

data class NotificationDTO (
    val id: Long,
    val title: String,
    val content: String,
    val weight: String,
    val sender: Long,
    val scope: String,
    val recipientGroups: MutableList<NotificationGroup>,
    val recipient: Long? = null,
    val date: LocalDateTime,
)

fun Notification.toDTO() = NotificationDTO (
    this.id,
    this.title,
    this.content,
    this.weight.name,
    this.sender,
    this.scope.name,
    this.recipientGroups,
    this.recipient,
    this.date,
)