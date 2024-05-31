package ar.org.schoolsync.bootstrap

import ar.org.schoolsync.model.*
import ar.org.schoolsync.model.Persons.Parent
import ar.org.schoolsync.model.Persons.Student
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.ParentRepository
import ar.org.schoolsync.repositories.StudentRepository
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import kotlin.jvm.optionals.getOrNull

@Component
class SchoolsyncBootstrap(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val notificationRepository: NotificationRepository,
    @Autowired
    private val studentRepository: StudentRepository,
    @Autowired
    private val parentRepository: ParentRepository,
    @Autowired
    private val encoder: PasswordEncoder
) : InitializingBean {
    val factory = EntityFactory(encoder)

//    val receivers = mutableListOf("Franco", "Claudia")
    val receivers = 0L//mutableListOf(0L)
    fun initUsers() = setOf(
        factory.createUser(Role.ADMIN),
        factory.createUser(Role.USER)
    )

    private fun <T> persist(objects: Set<T>) {
        filterExistingObjects(objects).forEach {
            when (it) {
                is User -> userRepository.save(it)
            }
        }
    }

    private fun <T> filterExistingObjects(objects: Set<T>) =
        objects.filter {
            when (it) {
                is User -> {
                    val user = userRepository.findByEmail(it.email).getOrNull()
                    user == null}
                else -> throw IllegalArgumentException("Tipo de clase no soportado: ${it!!::class.simpleName}")
            }
        }


        fun initNotificationRepository(){
            val notification1 = Notification(
                "Acto 25 de mayo",
                "Estimados Padres y Encargados:\n" +
                        "Nos complace invitarlos a nuestro próximo acto escolar con motivo de conmemorar el 25 de Mayo, fecha tan significativa en nuestra historia nacional. El evento se llevará a cabo el Lunes 27 de Mayo a las 15:00hs en el patio comun de recreo.\n" +
                        "\n" + "Este acto reviste una gran importancia para nuestra comunidad educativa, ya que nos brinda la oportunidad de reflexionar y celebrar juntos los valores de nuestra patria. Será una ocasión especial donde nuestros estudiantes demostrarán sus habilidades artísticas y compartirán con ustedes momentos de orgullo y emoción.\n" +
                        "\n" + "El programa del acto incluirá presentaciones de danzas folclóricas, declamaciones patrióticas, y otras actividades preparadas con esmero por nuestros alumnos y docentes.\n" +
                        "\n" + "Esperamos contar con su presencia en este evento, que fortalece los lazos entre la escuela y las familias, y enriquece la experiencia educativa de nuestros queridos estudiantes.\n" +
                        "\n" + "¡Los esperamos con entusiasmo!\n" +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
                1,
                NotifScope.GENERAL,
                NotificationWeight.MEDIO
            )

            val notification2 = Notification(
                "Reunión de Padres y Maestros - Recordatorio",
                "Estimados Padres y Encargados:\n" +
                        "Queremos recordarles amablemente sobre nuestra próxima Reunión de Padres y Maestros, que se llevará a cabo el [fecha] a las [hora] en nuestras instalaciones escolares. Esta reunión es una oportunidad invaluable para discutir el progreso académico y el desarrollo de sus hijos con sus maestros.\n" +
                        "\n" + "Agradecemos de antemano su participación y compromiso con la educación de sus hijos. Esperamos verlos a todos allí y trabajar juntos en beneficio de nuestros estudiantes.\n" +
                        "\n" + "¡Muchas gracias!\n" +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
                1,
                NotifScope.GENERAL,
            )

            val notification3 = Notification(
                "Cambio de Horario Salida - Nivel Primario",
                "Estimada Comunidad Educativa:\n" +
                        "Queremos informarles que a partir del próximo Lunes 16 de Agosto, habrá un cambio en el horario escolar. Este cambio se implementa con el objetivo de mejorar la distribución de salida para el Nivel Primario.\n" +
                        "\n" + "El nuevo horario será el siguiente:\n" +
                        "\n" +
                        "Grados 1 y 2: 15:00hs\n" +
                        "Grados 3 y 4: 15:20hs\n" +
                        "Grados 5 y 6: 15:40hs\n" +
                        "\n" + "Agradecemos su comprensión y cooperación durante este ajuste. Por favor, asegúrense de que sus hijos estén informados sobre el nuevo horario y lleguen a la escuela a tiempo.\n" +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
                1,
                NotifScope.GENERAL

            )

            val notification4 = Notification(
                title = "Corte de Luz",
                content = "Estimada Comunidad Educativa:\n" +
                        "Les informamos que tuvimos un pequeño corte de luz alrededor de las 12:00hs pero ya ha vuelto." +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
                1,
                NotifScope.GENERAL,
                date = LocalDateTime.now().minusMonths(3)
            )

            val notification5 = Notification(
                title = "Campaña de Recaudación de Fondos para Excursión",
                content = "Estimados Padres y Encargados:\n" +
                        "Estamos emocionados de anunciar el lanzamiento de nuestra campaña de recaudación de fondos para apoyar la próxima excursión escolar de nuestros estudiantes. La excursión está planeada para el [fecha] y será una experiencia educativa enriquecedora para todos los participantes.\n" +
                        "\n" + "Para hacer posible esta excursión y garantizar la participación de todos los estudiantes, necesitamos su colaboración. Cualquier contribución, grande o pequeña, será de gran ayuda. Los fondos recaudados se utilizarán para cubrir los gastos de transporte, entradas y otras necesidades relacionadas con la excursión.\n" +
                        "\n" + "Agradecemos de antemano su generosidad y apoyo en esta iniciativa. Juntos, podemos hacer que esta experiencia sea inolvidable para nuestros estudiantes.\n" +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo",
                1,
                NotifScope.GENERAL,
                NotificationWeight.ALTO,
                date = LocalDateTime.now().minusMonths(1)
            )

            notificationRepository.apply {
                save(notification1)
                save(notification2)
                save(notification3)
                save(notification4)
                save(notification5)
            }
        }

    fun initStudentRepository(){
        val student1 = Student(
            firstName = "Mateo",
            lastName = "Rodriguez",
            absences = 0,
            notifications = mutableListOf()
        )
        val student2 = Student(
            firstName = "Delfina",
            lastName = "Rodriguez",
            absences = 0,
            notifications = mutableListOf()
        )
        val student3 = Student(
            firstName = "Nicolas",
            lastName = "Rodriguez",
            absences = 0,
            notifications = mutableListOf()
        )
        val student4 = Student(
            firstName = "Javier",
            lastName = "Melo",
            absences = 0,
            notifications = mutableListOf()
        )
        val student5 = Student(
            firstName = "Federico",
            lastName = "Alvarez",
            absences = 0,
            notifications = mutableListOf()
        )
        val student6 = Student(
            firstName = "Joaquin",
            lastName = "Alvarez",
            absences = 0,
            notifications = mutableListOf()
        )
        studentRepository.apply {
            save(student1)
            save(student2)
            save(student3)
            save(student4)
            save(student5)
            save(student6)
        }

    }

    fun initParentRepository() {

        val students = studentRepository.findAll().toList()

        val parent1 = Parent(
            firstName = "Juan Ignacio",
            lastName = "Rodriguez",
            isFatherOf = mutableListOf(students[0], students[1],students[2]),
            notifications = mutableListOf(),
            notificationGroups = mutableListOf(NotificationGroup.TODOS,
                NotificationGroup.GRADO2,
                NotificationGroup.EQUIPO_FUTBOL
            )
        )
        val parent2 = Parent(
            firstName = "Martin",
            lastName = "Melo",
            isFatherOf = mutableListOf(students[3]),
            notifications = mutableListOf(),
            notificationGroups = mutableListOf(NotificationGroup.TODOS,
                NotificationGroup.GRADO2,
                NotificationGroup.EQUIPO_FUTBOL
            )
        )
        val parent3 = Parent(
            firstName = "Tomas",
            lastName = "Alvarez",
            isFatherOf = mutableListOf(students[4], students[5]),
            notifications = mutableListOf(),
            notificationGroups = mutableListOf(NotificationGroup.TODOS,
                NotificationGroup.GRADO2,
                NotificationGroup.EQUIPO_FUTBOL
            )
        )
        parentRepository.apply {
            save(parent1)
            save(parent2)
            save(parent3)
        }

    }

    override fun afterPropertiesSet() {
        initNotificationRepository()
        initStudentRepository()
        initParentRepository()
        persist(initUsers())
    }
//
}
