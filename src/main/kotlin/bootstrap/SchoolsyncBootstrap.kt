package ar.org.schoolsync.bootstrap

import ar.org.schoolsync.model.EntityFactory
import ar.org.schoolsync.model.Notification
import ar.org.schoolsync.model.NotificationRegistry
import ar.org.schoolsync.model.enums.NotificationGroup
import ar.org.schoolsync.model.enums.NotificationType
import ar.org.schoolsync.model.enums.Role
import ar.org.schoolsync.model.users.Parent
import ar.org.schoolsync.model.users.Student
import ar.org.schoolsync.model.users.User
import ar.org.schoolsync.repositories.NotificationRegistryRepository
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class SchoolsyncBootstrap(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val notificationRepository: NotificationRepository,
    @Autowired
    private val notificationRegistryRepository: NotificationRegistryRepository,
    @Autowired
    private val encoder: PasswordEncoder
) : InitializingBean {
    val factory = EntityFactory(encoder)

    fun initUsers() = setOf(
        factory.createUser(Role.ADMIN),
        factory.createUser(Role.PARENT)
    )

    fun initParents() = setOf(
        Parent(
            "Juan Ignacio",
            "Rodriguez",
            "juanrodriguez@gmail.com",
            encoder.encode("juanrodriguez")
        ).apply {
            addStudents(
                listOf(
                    Student("Mateo", "Rodriguez", "mateo@gmail.com"),
                    Student("Delfina", "Rodriguez", "delfina@gmail.com"),
                    Student("Nicolas", "Rodriguez", "nicolas@gmail.com")
                        .apply { repeat(3) { addAbsence() } },
                )
            )
            addGroup(NotificationGroup.GRADO2)
            addGroup(NotificationGroup.EQUIPO_FUTBOL)
        },
        Parent(
            "Martin",
            "Melo",
            "martinmelo@gmail.com",
            encoder.encode("martinmelo")
        ).apply {
            addStudents(listOf(Student("Javier", "Melo", "javiermelo@gmail.com")))
            addGroup(NotificationGroup.GRADO2)
            addGroup(NotificationGroup.EQUIPO_FUTBOL)
        },
        Parent(
            "Tomas",
            "Alvarez",
            "tomasalvarez@gmail.com",
            encoder.encode("tomasalvarez")
        ).apply {
            addStudents(
                listOf(
                    Student(
                        "Federico", "Alvarez", "federicoalvarez@gmail.com"
                    ).apply { repeat(3) { addAbsence() } },
                    Student(
                        "Joaquin", "Alvarez", "joaquinalvarez@gmail.com"
                    ).apply { repeat(20) { addAbsence() } }
                )
            )
            addGroup(NotificationGroup.GRADO2)
            addGroup(NotificationGroup.EQUIPO_FUTBOL)
        }
    )

    private fun <T> filterExistingObjects(objects: Set<T>) =
        objects.filter {
            when (it) {
                is User -> {
                    val user = userRepository.findByEmail(it.email).getOrNull()
                    user == null
                }

                is Notification -> {
                    val notification = notificationRepository.findDistinctByTitleAndContent(it.title, it.content)
                    notification.isEmpty()
                }

                is NotificationRegistry -> {
                    val registry =
                        notificationRegistryRepository.findNotificationRegistryBySenderAndRecieverAndNotification(
                            it.sender,
                            it.reciever,
                            it.notification
                        )
                    registry.isEmpty()
                }

                else -> throw IllegalArgumentException("Tipo de clase no soportado: ${it!!::class.simpleName}")
            }
        }

    private fun <T> persist(objects: Set<T>) {
        filterExistingObjects(objects).forEach {
            when (it) {
                is User -> userRepository.save(it)
                is Notification -> notificationRepository.save(it)
                is NotificationRegistry -> notificationRegistryRepository.save(it)
            }
        }
    }

    fun initNotifications() = setOf(
        EntityFactory(encoder).createNotification(NotificationType.PATRIOTIC),
        EntityFactory(encoder).createNotification(NotificationType.REUNION),
        Notification(
            title = "Cambio de Horario Salida - Nivel Primario",
            content = "Estimada Comunidad Educativa:\n" +
                    "Queremos informarles que a partir del próximo Lunes 16 de Agosto, habrá un cambio en el horario escolar. Este cambio se implementa con el objetivo de mejorar la distribución de salida para el Nivel Primario.\n" +
                    "\n" + "El nuevo horario será el siguiente:\n" +
                    "\n" +
                    "Grados 1 y 2: 15:00hs\n" +
                    "Grados 3 y 4: 15:20hs\n" +
                    "Grados 5 y 6: 15:40hs\n" +
                    "\n" + "Agradecemos su comprensión y cooperación durante este ajuste. Por favor, asegúrense de que sus hijos estén informados sobre el nuevo horario y lleguen a la escuela a tiempo.\n" +
                    "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
        ),
        Notification(
            title = "Corte de Luz",
            content = "Estimada Comunidad Educativa:\n" +
                    "Les informamos que tuvimos un pequeño corte de luz alrededor de las 12:00hs pero ya ha vuelto." +
                    "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo"
        ),
        Notification(
            title = "Campaña de Recaudación de Fondos para Excursión",
            content = "Estimados Padres y Encargados:\n" +
                    "Estamos emocionados de anunciar el lanzamiento de nuestra campaña de recaudación de fondos para apoyar la próxima excursión escolar de nuestros estudiantes. La excursión está planeada para el [fecha] y será una experiencia educativa enriquecedora para todos los participantes.\n" +
                    "\n" + "Para hacer posible esta excursión y garantizar la participación de todos los estudiantes, necesitamos su colaboración. Cualquier contribución, grande o pequeña, será de gran ayuda. Los fondos recaudados se utilizarán para cubrir los gastos de transporte, entradas y otras necesidades relacionadas con la excursión.\n" +
                    "\n" + "Agradecemos de antemano su generosidad y apoyo en esta iniciativa. Juntos, podemos hacer que esta experiencia sea inolvidable para nuestros estudiantes.\n" +
                    "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo"
        )
    )

    fun initNotificationRegistry(): Set<NotificationRegistry> {
        val admin = userRepository.findByEmail("adminuser@schoolsync.mail.com").get()
        val juanIgnacio = userRepository.findByEmail("juanrodriguez@gmail.com").get()
        val martinMelo = userRepository.findByEmail("martinmelo@gmail.com").get()
        val tomasAlvarez = userRepository.findByEmail("tomasalvarez@gmail.com").get()
        val padres = listOf(juanIgnacio, martinMelo, tomasAlvarez)
        val notificatciones = notificationRepository.findAll().map { it }

        val registries = notificatciones.flatMap { padres.map { padre -> NotificationRegistry(admin, padre, it ) } }

        return registries.toSet()
    }

    override fun afterPropertiesSet() {
        persist(initNotifications())
        println("All notifications have been initialized")
        persist(initUsers())
        println("All base users have been initialized")
        persist(initParents())
        println("All parents have been initialized")
        persist(initNotificationRegistry())
        println("All registries have been initialized")
    }
}
