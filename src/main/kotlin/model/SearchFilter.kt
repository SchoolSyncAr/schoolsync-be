package ar.org.schoolsync.model

data class SearchFilter(
    var searchField: String = "",
    var orderParam: String = "",
    var sortDirection: String = "",
    )
