package ar.org.schoolsync.model

import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Notification(
    @Id
    var id: UUID = UUID.randomUUID(),
    var title: String,
    var content: String
)