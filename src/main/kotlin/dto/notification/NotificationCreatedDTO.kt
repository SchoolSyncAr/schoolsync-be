package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotScope
import ar.org.schoolsync.model.NotificationGroup
import ar.org.schoolsync.model.User
import java.util.*

data class NotificationCreatedDTO (
    val id: UUID,
    val title: String,
    val content: String,
    val notificationSender: UUID,
    val notificationReceiver: MutableList<String>,  //UUID,
    val notificationScope: NotScope,
)

fun Notification.toCreateDTO() = NotificationCreatedDTO (
    id = this.id,
    title = this.title,
    content = this.content,
    notificationSender = this.notificationSender,
    notificationReceiver = this.notificationReceiver,
    notificationScope = this.notificationScope
)
