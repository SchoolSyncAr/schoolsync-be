package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotScope
import ar.org.schoolsync.model.NotificationGroup
import java.util.*
import ar.org.schoolsync.model.User

data class NotificationResponseDTO (
    val id: UUID,
    val title: String,
    val content: String,
    val notificationSender: UUID,
    val notificationReceiver: MutableList<String>, //UUID,
    val notificationScope: NotScope
        )

fun Notification.toResponseDTO() = NotificationResponseDTO (
    this.id,
    this.title,
    this.content,
    this.notificationSender,
    this.notificationReceiver,
    this.notificationScope
        )
