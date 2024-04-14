package ar.org.schoolsync.bootstrap

import ar.org.schoolsync.Domain.Notification
import ar.org.schoolsync.Repositories.NotificationRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Service

@Service
class SchoolsyncBootstrap (
    val notificationRepository: NotificationRepository
        ) : InitializingBean {

        fun initNotificationRepository(){
            notificationRepository.create(
                Notification(
                    id = 1,
                    title = "Acto 25 de mayo",
                    content = "Se realizara a las 10 hs",
                    general = true
                )
            )
            notificationRepository.create(
                Notification(
                    id = 2,
                    title = "Acto 9 de julio",
                    content = "Se realizara a las 12:30 hs",
                    general = true
                )

            )
        }

    override fun afterPropertiesSet() {
        initNotificationRepository()
    }

}
