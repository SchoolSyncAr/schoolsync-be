package ar.org.schoolsync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication


@SpringBootApplication
class Schoolsync

fun main(args: Array<String>) {
    runApplication<Schoolsync>(*args)
    println("Hello World!")
}