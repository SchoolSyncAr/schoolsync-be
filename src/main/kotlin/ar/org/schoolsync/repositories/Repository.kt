package ar.org.schoolsync.repositories

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

    fun getAllCount(): Int {
        return elements.size
    }

    //deletes an element
    fun delete(element: T){
        elements.remove(element)
    }

    //searches element by Id
    fun getById(id:Int): T {
        return elements.find { id == it.id }
            ?: throw IdInvalido("El elemento con id=${id} no existe")
    }


    //deletes an element by Id
    fun deleteById(id: Int) {
        val deletedElement = getById(id)
        elements.remove(deletedElement)
    }

}
