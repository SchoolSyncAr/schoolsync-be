package ar.org.schoolsync.bootstrap

import ar.org.schoolsync.domain.Admin
import ar.org.schoolsync.domain.Notification
import ar.org.schoolsync.domain.Parent
import ar.org.schoolsync.repositories.NotificationRepository
import ar.org.schoolsync.repositories.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class SchoolsyncBootstrap(
    val notificationRepository: NotificationRepository,
    val userRepository: UserRepository
) : InitializingBean {

    fun initNotificationRepository() {
        notificationRepository.save(
            Notification(
                id = 1,
                title = "Acto 25 de mayo",
                content = "Se realizara a las 10 hs",
            )
        )
        notificationRepository.save(
            Notification(
                id = 2,
                title = "Acto 9 de julio",
                content = "Se realizara a las 12:30 hs",
            )

        )
    }

    fun initParents() {
        userRepository.saveAll(
            listOf(
                Admin(firstName = "Sol", lastName = "Lopez", username = "admin", password = "manzanas"),
                Parent(firstName = "Pablo", lastName = "Foglia", username = "mad", password = "bananas")
            )
        )
    }

    override fun afterPropertiesSet() {
        initNotificationRepository()
        initParents()
    }

}
