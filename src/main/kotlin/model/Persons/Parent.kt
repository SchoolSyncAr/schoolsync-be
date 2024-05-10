package ar.org.schoolsync.model.Persons

import ar.org.schoolsync.model.Notification
import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "app_parent")
class Parent(

    firstName: String,
    lastName: String,
    @OneToMany
    var isFatherOf: MutableList<Person>? = null,
    @OneToMany
    var notifications: MutableList<Notification>? = null,
    var prueba: Int,

) : Person(firstName, lastName) {


    constructor(
        firstName: String,
        lastName: String,
        isFatherOf: MutableList<Person>,
        notifications: MutableList<Notification>?,
    ) : this( firstName, lastName, mutableListOf(), mutableListOf(), 0) {
        this.isFatherOf = isFatherOf
        this.notifications = notifications
        this.prueba = 0
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
