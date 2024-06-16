package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.NotificationType
import ar.org.schoolsync.model.enums.NotificationWeight
import ar.org.schoolsync.model.enums.Role
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.time.Month

@Component
class EntityFactory(private val encoder: PasswordEncoder) {

    fun createUser(type: Role) = when (type) {
        Role.USER -> TODO()
        Role.ADMIN -> AdminUser(encoder).build()
        Role.STUDENT -> StudentUser(encoder).build()
        Role.TEACHER -> TODO()
        Role.PARENT -> ParentUser(encoder).build()
    }

    fun createNotification(type: NotificationType, sender: User) = when (type) {
        NotificationType.PATRIOTIC -> PatriotNotificaton(sender).build()
        NotificationType.REUNION -> ReunionNotification(sender).build()
    }
}

interface FactoryObject<T> {
    fun build(): T
}

interface UserObject : FactoryObject<User> {
    val encoder: PasswordEncoder
}

interface NotificationObject : FactoryObject<Notification> {
    val sender: User
}


class AdminUser(override var encoder: PasswordEncoder) : UserObject {
    override fun build() =
        User(
            firstName = "Director",
            lastName = "Perez",
            email = "adminuser@schoolsync.mail.com",
            password = encoder.encode("adminuser")
        ).apply {
            changeBehavior(AdminBehavior())
        }
}

class ParentUser(override val encoder: PasswordEncoder) : UserObject {
    override fun build() =
        User(
            firstName = "Daniel",
            lastName = "Follio",
            email = "parent@schoolsync.mail.com",
            password = encoder.encode("parentuser")
        ).apply {
            changeBehavior(ParentBehavior())
        }
}

class StudentUser(override val encoder: PasswordEncoder) : UserObject {
    override fun build() =
        User(
            firstName = "Ismael",
            lastName = "Follio",
            email = "ismaelfollio@schoolsync.mail",
            password = ""
        ).apply {
            changeBehavior(StudentBehavior())
        }
}

class PatriotNotificaton(override val sender: User) : NotificationObject {
    override fun build(): Notification =
        Notification(
            sender = sender,
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
        ).apply {
            date = LocalDateTime.of(LocalDateTime.now().year, Month.MAY, 15, 0, 0, 0)
        }
}

class ReunionNotification(override val sender: User) : NotificationObject {
    override fun build(): Notification =
        Notification(
            sender = sender,
            title = "Reunión de Padres y Maestros - Recordatorio",
            content = """
                "Queremos recordarles amablemente sobre nuestra próxima Reunión de Padres y Maestros, que se llevará a cabo el [fecha] a las [hora] en nuestras instalaciones escolares. Esta reunión es una oportunidad invaluable para discutir el progreso académico y el desarrollo de sus hijos con sus maestros.
                "Agradecemos de antemano su participación y compromiso con la educación de sus hijos. Esperamos verlos a todos allí y trabajar juntos en beneficio de nuestros estudiantes.
                "¡Muchas gracias!
                "Atentamente,\n Directora Silvana y el Complejo Educativo",
            """.trimIndent(),
            weight = NotificationWeight.ALTO
        ).apply {
            date = LocalDateTime.now().minusDays(4)
        }
}
