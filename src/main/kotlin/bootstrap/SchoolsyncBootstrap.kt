package ar.org.schoolsync.bootstrap

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Domain.Parent
import ar.org.schoolsync.Domain.Student
import ar.org.schoolsync.Repositories.NotificationRepository
import ar.org.schoolsync.Repositories.ParentRepository
import ar.org.schoolsync.Repositories.StudentRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class SchoolsyncBootstrap (
    val notificationRepository: NotificationRepository,
    val parentRepository: ParentRepository,
    val studentRepository: StudentRepository
        ) : InitializingBean {

        fun initNotificationRepository(){
            notificationRepository.create(
                Notification(
                    id = 1,
                    title = "Acto 25 de mayo",
                    content = "Estimados Padres y Encargados:\n" +
                        "Nos complace invitarlos a nuestro próximo acto escolar con motivo de conmemorar el 25 de Mayo, fecha tan significativa en nuestra historia nacional. El evento se llevará a cabo el Lunes 27 de Mayo a las 15:00hs en el patio comun de recreo.\n" +
                        "\n" + "Este acto reviste una gran importancia para nuestra comunidad educativa, ya que nos brinda la oportunidad de reflexionar y celebrar juntos los valores de nuestra patria. Será una ocasión especial donde nuestros estudiantes demostrarán sus habilidades artísticas y compartirán con ustedes momentos de orgullo y emoción.\n" +
                        "\n" + "El programa del acto incluirá presentaciones de danzas folclóricas, declamaciones patrióticas, y otras actividades preparadas con esmero por nuestros alumnos y docentes.\n" +
                        "\n" + "Esperamos contar con su presencia en este evento, que fortalece los lazos entre la escuela y las familias, y enriquece la experiencia educativa de nuestros queridos estudiantes.\n" +
                        "\n" + "¡Los esperamos con entusiasmo!\n" +
                        "\n" + "Atentamente,\n Directora Silvana y el Complejo Educativo"
                )
            )
            notificationRepository.create(
                Notification(
                    id = 2,
                    title = "Reunión de Padres y Maestros - Recordatorio",
                    content = "Estimados Padres y Encargados:\n" +
                        "Queremos recordarles amablemente sobre nuestra próxima Reunión de Padres y Maestros, que se llevará a cabo el [fecha] a las [hora] en nuestras instalaciones escolares. Esta reunión es una oportunidad invaluable para discutir el progreso académico y el desarrollo de sus hijos con sus maestros.\n" +
                        "\n" + "Agradecemos de antemano su participación y compromiso con la educación de sus hijos. Esperamos verlos a todos allí y trabajar juntos en beneficio de nuestros estudiantes.\n" +
                        "\n" + "¡Muchas gracias!\n" +
                        "\n" +"Atentamente,\n Directora Silvana y el Complejo Educativo"
                )
            )

            notificationRepository.create(
                Notification(
                    id = 3,
                    title = "Cambio de Horario Salida - Nivel Primario",
                    content = "Estimada Comunidad Educativa:\n" +
                        "Queremos informarles que a partir del próximo Lunes 16 de Agosto, habrá un cambio en el horario escolar. Este cambio se implementa con el objetivo de mejorar la distribución de salida para el Nivel Primario.\n" +
                        "\n" + "El nuevo horario será el siguiente:\n" +
                        "\n" +
                        "Grados 1 y 2: 15:00hs\n" +
                        "Grados 3 y 4: 15:20hs\n" +
                        "Grados 5 y 6: 15:40hs\n" +
                        "\n" +"Agradecemos su comprensión y cooperación durante este ajuste. Por favor, asegúrense de que sus hijos estén informados sobre el nuevo horario y lleguen a la escuela a tiempo.\n" +
                        "\n" +"Atentamente,\n Directora Silvana y el Complejo Educativo"
                )
            )

            notificationRepository.create(
                Notification(
                    id = 4,
                    title = "Corte de Luz",
                    content = "Estimada Comunidad Educativa:\n" +
                        "Les informamos que tuvimos un pequeño corte de luz alrededor de las 12:00hs pero ya ha vuelto." +
                        "\n" +"Atentamente,\n Directora Silvana y el Complejo Educativo"
                )
            )

            notificationRepository.create(
                Notification(
                    id = 5,
                    title = "Campaña de Recaudación de Fondos para Excursión",
                    content = "Estimados Padres y Encargados:\n" +
                        "Estamos emocionados de anunciar el lanzamiento de nuestra campaña de recaudación de fondos para apoyar la próxima excursión escolar de nuestros estudiantes. La excursión está planeada para el [fecha] y será una experiencia educativa enriquecedora para todos los participantes.\n" +
                        "\n" + "Para hacer posible esta excursión y garantizar la participación de todos los estudiantes, necesitamos su colaboración. Cualquier contribución, grande o pequeña, será de gran ayuda. Los fondos recaudados se utilizarán para cubrir los gastos de transporte, entradas y otras necesidades relacionadas con la excursión.\n" +
                        "\n" + "Agradecemos de antemano su generosidad y apoyo en esta iniciativa. Juntos, podemos hacer que esta experiencia sea inolvidable para nuestros estudiantes.\n" +
                        "\n" +"Atentamente,\n Directora Silvana y el Complejo Educativo"
                )
            )
        }


        fun initStudentRepository() {
            studentRepository.create(
                Student(
                    id = 0,
                    firstName = "Esteban",
                    lastName = "Aguilar",
                    birthDate = LocalDate.of(2009, 2, 11),
                    enrolledSubjects = listOf(),
                    undergradedSubjects = listOf()
                )
            )

            studentRepository.create(
                Student(
                    id = 1,
                    firstName = "Paulina",
                    lastName = "Rodriguey",
                    birthDate = LocalDate.of(2012, 5, 18),
                    enrolledSubjects = listOf(),
                    undergradedSubjects = listOf()
                )
            )

            studentRepository.create(
                Student(
                    id = 3,
                    firstName = "Catalina",
                    lastName = "Rodriguey",
                    birthDate = LocalDate.of(2014, 7, 8),
                    enrolledSubjects = listOf(),
                    undergradedSubjects = listOf()
                )
            )

            studentRepository.create(
                Student(
                    id = 4,
                    firstName = "Pedro",
                    lastName = "Rodriguey",
                    birthDate = LocalDate.of(2010, 4, 1),
                    enrolledSubjects = listOf(),
                    undergradedSubjects = listOf()
                )
            )
        }

//    val studentUno = Student(
//        id = 1,
//        firstName = "Esteban",
//        lastName = "Aguilar",
//        birthDate = LocalDate.of(2009, 2, 11),
//        enrolledSubjects = listOf(),
//        undergradedSubjects = listOf()
//    )

    val studentDos = Student(
        id = 2,
        firstName = "Paulina",
        lastName = "Rodriguey",
        birthDate = LocalDate.of(2012, 5, 18),
        enrolledSubjects = listOf(),
        undergradedSubjects = listOf()
    )

    val studentTres = Student(
        id = 3,
        firstName = "Catalina",
        lastName = "Rodriguey",
        birthDate = LocalDate.of(2014, 7, 8),
        enrolledSubjects = listOf(),
        undergradedSubjects = listOf()
    )

    val studentCuatro = Student(
        id = 4,
        firstName = "Pedro",
        lastName = "Rodriguey",
        birthDate = LocalDate.of(2010, 4, 1),
        enrolledSubjects = listOf(),
        undergradedSubjects = listOf()
    )
//        }
    fun initParentRepository() {
        parentRepository.create(
            Parent(
                id = 1,
                firstName = "Ernesto",
                lastName = "Rodriguey",
                isFatherOf = listOf(studentDos, studentTres, studentCuatro),
                notifications = listOf()
            )
        )

//            val parentUno = Parent(
//                id = 1,
//                firstName = "Ernesto",
//                lastName = "Rodriguey",
//                isFatherOf = listOf(studentDos, studentTres, studentCuatro),
//                notifications = listOf()
//            )
//
        parentRepository.create(
            Parent(
                id = 2,
                firstName = "Gustavo",
                lastName = "Aguilar",
                isFatherOf = listOf(Student(id = 1,
        firstName = "Esteban",
        lastName = "Aguilar",
        birthDate = LocalDate.of(2009, 2, 11),
        enrolledSubjects = listOf(),
        undergradedSubjects = listOf())),
                notifications = listOf()
            )
        )

    }



    override fun afterPropertiesSet() {
        initNotificationRepository()
        initParentRepository()
        initStudentRepository()
    }
}
