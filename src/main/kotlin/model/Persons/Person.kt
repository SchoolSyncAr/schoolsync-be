package ar.org.schoolsync.model.Persons

import ar.org.schoolsync.model.NotScope
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)  // este para tablas separadas
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
abstract class Person {
    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
//    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0L//UUID = UUID.randomUUID(),

//    @GeneratedValue(strategy = GenerationType.UUID)
//    var id: UUID = UUID.randomUUID()

    var firstName: String = ""
    var lastName: String = ""


    constructor() : super()

    constructor(firstName: String, lastName: String) : super() {
        this.firstName = firstName
        this.lastName = lastName
    }
}






