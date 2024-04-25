package ar.org.schoolsync.domain

class Parent(
    override var id: Long = 0,
    override val firstName: String,
    override val lastName: String,
    override val username: String,
    override var password: String,
) : User {
    override var userStatus: UserStatus = ParentStatus()
    var isFatherOf: List<Student> = listOf()
    var notifications: List<Notification> = listOf()
}