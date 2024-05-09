package ar.org.schoolsync.model

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_user")
data class User(
    @Column(length = 40, nullable = false)
    val firstName: String,

    @Column(length = 40, nullable = false)
    val lastName: String,

    @Column(length = 60, nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    var role: Role = Role.USER
) {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID = UUID.randomUUID()
}



