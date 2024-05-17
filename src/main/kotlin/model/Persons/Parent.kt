package ar.org.schoolsync.model.Persons

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "app_parent")
class Parent(

    firstName: String,
    lastName: String,
    @OneToMany
    var isFatherOf: MutableList<Person>,
    var prueba: Int,



) : Person(firstName, lastName) {

    constructor(
        firstName: String,
        lastName: String
    ) : this( firstName, lastName, mutableListOf(), 0) {
        this.isFatherOf = mutableListOf()
        this.prueba = 0
    }

}
