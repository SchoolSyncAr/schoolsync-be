package ar.org.schoolsync.Services

import ar.org.schoolsync.Domain.Parent
import ar.org.schoolsync.Domain.Student
import ar.org.schoolsync.Repositories.ParentRepository
import org.springframework.stereotype.Service

@Service
class ParentService (val parentRepository: ParentRepository) {

    fun getAllChildren(parentId: Int):List<Student> {
        var allParents = parentRepository.getById(parentId)
        println(allParents.id)
//        var parent = allParents.find { parentId == it.id} ?: throw IdInvalido("El elemento con id=${parentId} no existe")
        var allChildren = allParents.isFatherOf
        return allChildren
    }

    fun getAllParents():List<Parent>{
        return parentRepository.getAll()
    }

}