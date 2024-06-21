package ar.org.schoolsync.model

import ar.org.schoolsync.model.enums.Status
import jakarta.persistence.criteria.Join
import jakarta.persistence.criteria.JoinType
import jakarta.persistence.criteria.Root
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification

class SearchFilter(
    var searchField: String = "",
    private var sortField: String = "",
) {
    private val sortParameters = sortField.lowercase().split("_")
    private fun getDirection() = if (sortParameters[1] == "asc") Sort.Direction.ASC else Sort.Direction.DESC

    fun getSortUser(): Sort =
        Sort.by(
            Sort.Order(Sort.Direction.DESC, "pinned"),
            if (sortField.isNotEmpty()) Sort.Order(getDirection(), sortParameters[0]) else Sort.Order.asc("date")
        )


    fun getSortAdmin(): Sort =
        Sort.by(
            if (sortField.isNotEmpty()) Sort.Order(getDirection(), sortParameters[0]) else Sort.Order.asc("date")
        )
}

class SearchFilterBuilder(
    private val filter: SearchFilter,
    private val isRegistry: Boolean = false
) {
    private var specs: Specification<CommonNotification> = Specification.where(null)

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

    fun userId(id: Long): SearchFilterBuilder {
        specs = specs.and { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<Long>("receiver").get<Long>("id"), id)
        }
        return this
    }

    fun active(): SearchFilterBuilder {
        specs = specs.and { root, _, criteriaBuilder ->
            val rootJoined = if (isRegistry) joinedRoot(root) else root
            criteriaBuilder.equal(
                rootJoined.get<Status>("status"),
                Status.ACTIVE
            )
        }
        return this
    }

    fun build(): Specification<CommonNotification> {
        if (filterMap.isNullOrEmpty()) {
            searchEverywhere()
        }
        return specs
    }

    private fun extractSpect(type: FilterTypes) {
        filterMap?.let { map ->
            map[type.name.lowercase()]?.let { value ->
                specs = specs.and { root, _, criteriaBuilder ->
                    val rootJoined = if (isRegistry) joinedRoot(root) else root
                    criteriaBuilder.like(
                        criteriaBuilder.lower(rootJoined.get(type.eng)),
                        "%${value.lowercase()}%"
                    )
                }
            }
        }
    }

    private fun searchEverywhere() {
        filter.searchField.takeIf { it.isNotEmpty() }?.let { searchValue ->
            val searchValueLower = searchValue.lowercase()
            specs = specs.and { root, _, criteriaBuilder ->
                val rootJoined = if (isRegistry) joinedRoot(root) else root
                criteriaBuilder.or(
                    criteriaBuilder.like(
                        criteriaBuilder.lower(rootJoined.get(FilterTypes.TITULO.eng)),
                        "%$searchValueLower%"
                    ),
                    criteriaBuilder.like(
                        criteriaBuilder.lower(rootJoined.get(FilterTypes.AUTOR.eng)),
                        "%$searchValueLower%"
                    ),
                    criteriaBuilder.like(
                        criteriaBuilder.lower(rootJoined.get(FilterTypes.CONTENIDO.eng)),
                        "%$searchValueLower%"
                    )
                )
            }
        }
    }

    private fun joinedRoot(root: Root<CommonNotification>): Join<NotificationRegistry, Notification> =
        root.join("notification", JoinType.LEFT)
}

enum class FilterTypes(val eng: String) {
    TITULO("title"), AUTOR("senderName"), CONTENIDO("content")
}