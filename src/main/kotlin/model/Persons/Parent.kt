package ar.org.schoolsync.model.Persons

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup
import jakarta.persistence.*

@Entity
@Table(name = "app_parent")
class Parent(

    firstName: String,
    lastName: String,
    @OneToMany(fetch = FetchType.EAGER)
    var isFatherOf: MutableList<Person>? = null,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
//    @NotFound(action = NotFoundAction.IGNORE)
    var notifications: MutableList<Notification> = mutableListOf(),
    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroups: MutableList<NotificationGroup>,

    ) : Person(firstName, lastName) {


    constructor(
        firstName: String,
        lastName: String,
        isFatherOf: MutableList<Person>,
        notifications: MutableList<Notification>,
    ) : this( firstName, lastName, mutableListOf(), mutableListOf(), mutableListOf<NotificationGroup>()) {
        this.isFatherOf = isFatherOf
        this.notifications = notifications
        this.notificationGroups = mutableListOf()
    }

}
