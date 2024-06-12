package ar.org.schoolsync.model

import org.springframework.data.domain.Sort
class SearchFilter(
    var searchField: String = "",
    var orderParam: String = "",
    private var sortDirection: String = "",
) {
    fun getDirection() = if (sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC
}
