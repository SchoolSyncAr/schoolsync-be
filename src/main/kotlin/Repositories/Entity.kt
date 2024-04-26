package ar.org.schoolsync.Repositories

interface Entity {
    companion object {
        const val InitialId = 0
    }

    var id: Int
}