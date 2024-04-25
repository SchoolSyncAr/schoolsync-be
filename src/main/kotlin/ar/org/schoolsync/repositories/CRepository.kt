package ar.org.schoolsync.repositories

import org.springframework.stereotype.Repository

@Repository
class CRepository<T : Entity> {

    var actualId = 1
    var nextId: Int = 0

    val elements: MutableSet<T> = mutableSetOf()

    //creates elements
    fun save(element: T) {
        addAnElement(element)
    }

    //adds an element
    private fun addAnElement(element: T) {
        elements.add(element)
    }

    //returns all the elements of a list
    fun findAll(): List<T> = elements.toList()

    fun deleteAll() {
        elements.clear()
    }
}
