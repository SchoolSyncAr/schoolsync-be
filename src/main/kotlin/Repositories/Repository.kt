package ar.org.schoolsync.Repositories

open class Repository<T: Entity> {

    var actualId = 1
    var nextId: Int = 0

    val elements: MutableSet<T> = mutableSetOf()

    //creates elements, add an element to the collection and assigns an id
    fun create(element: T){
        element.id = nextId
        nextId++
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
