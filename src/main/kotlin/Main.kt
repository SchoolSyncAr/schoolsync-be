package ar.org.schoolsync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class schoolsync
fun main(args: Array<String>) {
    runApplication<schoolsync>(*args)
    println("Hello World!")
}