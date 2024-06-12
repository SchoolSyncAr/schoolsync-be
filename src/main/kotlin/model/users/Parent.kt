package ar.org.schoolsync.model.users

import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.Role
import jakarta.persistence.*

@Entity
class Parent(
    firstName: String,
    lastName: String,
    email: String,
    password: String,
) : User(firstName, lastName, email, password) {
    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    override var role: Role = Role.PARENT

    init {
        addGroup(NotificationGroup.TODOS)
    }

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinTable(
        name = "students",
        joinColumns = [JoinColumn(name = "parent_id")],
        inverseJoinColumns = [JoinColumn(name = "student_id")]
    )
    var isFatherOf: MutableList<Student> = mutableListOf()

    fun addStudents(students: List<Student>) {
        isFatherOf.addAll(students)
    }

    fun removeStudent(student: Student) {
        isFatherOf.remove(student)
    }
}