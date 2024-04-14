package ar.org.schoolsync.Repositories

open class Repository<T: Entity> {

    var ActualId = 1
    var NextId: Int = 0

    val elements: MutableSet<T> = mutableSetOf()

    //creates elements
    fun create(element: T){
        addAnElement(element)
    }
    //adds an element
    private fun addAnElement(element: T){
        elements.add(element)
    }

    //returns all the elements of a list
    fun getAll(): List<T>{
        return elements.toList()
    }

}
