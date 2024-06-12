package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.NotificationType
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.users.Admin
import ar.org.schoolsync.model.users.Parent
import ar.org.schoolsync.model.users.Student
import ar.org.schoolsync.model.users.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Component
class EntityFactory(private val encoder: PasswordEncoder) {

    fun createUser(type: Role) = when (type) {
        Role.UNDEFINED -> TODO()
        Role.ADMIN -> AdminUser(encoder).build()
        Role.STUDENT -> StudentUser(encoder).build()
        Role.TEACHER -> TODO()
        Role.PARENT -> ParentUser(encoder).build()
    }

    fun createNotification(type: NotificationType) = when (type) {
        NotificationType.PATRIOTIC -> PatriotNotificaton().build()
        NotificationType.REUNION -> ReunionNotification().build()
    }
}

interface FactoryObject<T> {
    fun build(): T
}

interface UserObject : FactoryObject<User> {
    val encoder: PasswordEncoder
}

class AdminUser(override var encoder: PasswordEncoder) : UserObject {
    override fun build() =
        Admin(
            firstName = "Admin",
            lastName = "User",
            email = "adminuser@schoolsync.mail.com",
            password = encoder.encode("adminuser"),
        )
}

class ParentUser(override val encoder: PasswordEncoder) : UserObject {
    override fun build() =
        Parent(
            firstName = "Daniel",
            lastName = "Follio",
            email = "parent@schoolsync.mail.com",
            password = encoder.encode("parentuser"),
        )
}

class StudentUser(override val encoder: PasswordEncoder) : UserObject {
    override fun build() =
        Student(
            firstName = "Ismael",
            lastName = "Follio",
            email = "ismaelfollio@schoolsync.mail"
        )
}

class PatriotNotificaton : FactoryObject<Notification> {
    override fun build(): Notification =
        Notification(
            title = "Acto 25 de mayo",
            content = """
                Estimados Padres y Encargados: 
                Nos complace invitarlos a nuestro próximo acto escolar con motivo de conmemorar el 25 de Mayo, fecha tan significativa en nuestra historia nacional. El evento se llevará a cabo el Lunes 27 de Mayo a las 15:00hs en el patio común de recreo.    
                Este acto reviste una gran importancia para nuestra comunidad educativa, ya que nos brinda la oportunidad de reflexionar y celebrar juntos los valores de nuestra patria. Será una ocasión especial donde nuestros estudiantes demostrarán sus habilidades artísticas y compartirán con ustedes momentos de orgullo y emoción.    
                El programa del acto incluirá presentaciones de danzas folclóricas, declamaciones patrióticas, y otras actividades preparadas con esmero por nuestros alumnos y docentes.    
                Esperamos contar con su presencia en este evento, que fortalece los lazos entre la escuela y las familias, y enriquece la experiencia educativa de nuestros queridos estudiantes.    
        
                ¡Los esperamos con entusiasmo!
    
                Atentamente,
                Directora Silvana y el Complejo Educativo
            """.trimIndent(),
            weight = NotificationWeight.MEDIO
        )
}

class ReunionNotification : FactoryObject<Notification> {
    override fun build(): Notification =
        Notification(
            title = "Reunión de Padres y Maestros - Recordatorio",
            content = """
                "Queremos recordarles amablemente sobre nuestra próxima Reunión de Padres y Maestros, que se llevará a cabo el [fecha] a las [hora] en nuestras instalaciones escolares. Esta reunión es una oportunidad invaluable para discutir el progreso académico y el desarrollo de sus hijos con sus maestros.
                "Agradecemos de antemano su participación y compromiso con la educación de sus hijos. Esperamos verlos a todos allí y trabajar juntos en beneficio de nuestros estudiantes.
                "¡Muchas gracias!
                "Atentamente,\n Directora Silvana y el Complejo Educativo",
            """.trimIndent(),
            weight = NotificationWeight.ALTO
        )
}
