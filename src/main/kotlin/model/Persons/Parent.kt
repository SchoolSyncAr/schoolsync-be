package ar.org.schoolsync.model.Persons

import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationGroup
import jakarta.persistence.*
import org.hibernate.annotations.NotFound
import org.hibernate.annotations.NotFoundAction

@Entity
@Table(name = "app_parent")
class Parent(

    firstName: String,
    lastName: String,
    @OneToMany(fetch = FetchType.EAGER)
    var isFatherOf: MutableList<Person>? = null,
    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.PERSIST])
//    @NotFound(action = NotFoundAction.IGNORE)
    var notifications: MutableList<Notification>? = null,
    @ElementCollection(fetch = FetchType.EAGER)
    var notificationGroup: MutableList<NotificationGroup>,

    ) : Person(firstName, lastName) {


    constructor(
        firstName: String,
        lastName: String,
        isFatherOf: MutableList<Person>,
        notifications: MutableList<Notification>?,
    ) : this( firstName, lastName, mutableListOf(), mutableListOf(), mutableListOf<NotificationGroup>()) {
        this.isFatherOf = isFatherOf
        this.notifications = notifications
        this.notificationGroup = mutableListOf()
    }


//
//    constructor(
//        firstName: String,
//        lastName: String
//    ) : this( firstName, lastName, mutableListOf(), 0) {
//        this.isFatherOf = mutableListOf()
//        this.prueba = 0
//    }

}