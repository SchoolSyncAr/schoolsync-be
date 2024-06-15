package ar.org.schoolsync.model

import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

class SearchFilter(
    var searchField: String = "",
    var orderParam: String = "",
    private var sortDirection: String = "",
) {
    fun getDirection() = if (sortDirection == "asc") Sort.Direction.ASC else Sort.Direction.DESC

    fun getSort() = Sort.by(
        Sort.Order(Sort.Direction.DESC, "pinned"),
        if (orderParam.isNotEmpty()) Sort.Order(getDirection(), orderParam) else Sort.Order.asc("date")
    )
}

class SearchFilterBuilder(private val filter: SearchFilter) {
    private var specs: Specification<Notification>? = Specification.where(null)

    private val regex = Regex("(autor|titulo|contenido):([^:]*)(?= autor:| titulo:| contenido:|$)")
    private val matches: Sequence<MatchResult>? =
        if (filter.searchField.isEmpty()) null else regex.findAll(filter.searchField)
    private val filterMap = matches?.associate { it.groupValues[1] to it.groupValues[2].trim() }

    fun from(): SearchFilterBuilder {
        extractSpect(FilterTypes.AUTOR)
        return this
    }

    fun title(): SearchFilterBuilder {
        extractSpect(FilterTypes.TITULO)
        return this
    }

    fun content(): SearchFilterBuilder {
        extractSpect(FilterTypes.CONTENIDO)
        return this
    }

    fun build(): Specification<Notification>? {
        if (filterMap.isNullOrEmpty()) {
            searchEverywhere()
        }
        return specs
    }
    private fun extractSpect(type: FilterTypes) {
        filterMap?.let { map ->
            map[type.name.lowercase()]?.let { value ->
                specs = specs?.and(Specification { root, _, criteriaBuilder ->
                    criteriaBuilder.like(
                        criteriaBuilder.lower(root.get(type.eng)),
                        "%${value.lowercase()}%"
                    )
                })
            }
        }
    }

    private fun searchEverywhere() {
        filter.searchField.takeIf { it.isNotEmpty() }?.let { searchValue ->
            val searchValueLower = searchValue.lowercase()
            specs = Specification.where { root, _, criteriaBuilder ->
                criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(FilterTypes.TITULO.eng)), "%$searchValueLower%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(FilterTypes.AUTOR.eng)), "%$searchValueLower%"),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get(FilterTypes.CONTENIDO.eng)), "%$searchValueLower%")
                )
            }
        }
    }
}

enum class FilterTypes(val eng: String) {
    TITULO("title"), AUTOR("senderName"), CONTENIDO("content")
}