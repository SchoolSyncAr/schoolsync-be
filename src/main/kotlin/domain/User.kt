package ar.org.schoolsync.domain

import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "app_user")
data class User(
    @Column(nullable = false)
    val firstName: String,

    @Column(nullable = false)
    val lastName: String,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    var password: String,

    @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_role", joinColumns = [JoinColumn(name = "user_id")])
    val roles: Collection<Role> = ArrayList()
) {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID()
}

