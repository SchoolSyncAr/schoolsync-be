package ar.org.schoolsync.dto.notification

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup

data class NotificationCreatedDTO (
    val id: Long,

    val title: String,
    val content: String,
    val notificationSender: Long,
    val notificationReceiver: Long,//MutableList<Long>?,
    val notificationGroup: MutableList<NotificationGroup>,
)

fun Notification.toCreateDTO() = NotificationCreatedDTO (
    id = this.id,
    title = this.title,
    content = this.content,
    notificationSender = this.notificationSender,
    notificationReceiver = this.notificationReceiver,
    notificationGroup = this.notificationGroup
)
