package ar.org.schoolsync.repositories

interface Entity {
    companion object {
        const val InitialId = 0
    }

    var id: Long
}