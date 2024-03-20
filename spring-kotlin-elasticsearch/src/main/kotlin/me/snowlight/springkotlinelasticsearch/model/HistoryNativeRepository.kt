package me.snowlight.springkotlinelasticsearch.model

import kotlinx.coroutines.reactor.awaitSingle
import me.snowlight.springkotlinelasticsearch.config.extension.toLocalDate
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchTemplate
import org.springframework.data.elasticsearch.core.query.Criteria
import org.springframework.data.elasticsearch.core.query.CriteriaQuery
import org.springframework.stereotype.Component
import kotlin.reflect.KProperty

@Component
class HistoryNativeRepository(
    private val template: ReactiveElasticsearchTemplate,
) {
    suspend fun search(request: QrySearch): ResSearch {
        val criteria = Criteria().apply {
            request.orderId?.let { and(
                History::orderId.criteria.`in`(it)
            )}
            request.userId?.let { and(
                History::userId.criteria.`in`(it)
            )}
            request.keyword?.split(" ")?.toSet()?.forEach { and(
                History::description.criteria.contains(it)
            )}
            request.pgStatus?.let { and(
                History::pgStatus.criteria.`in`(it)
            )}
            request.fromDt?.toLocalDate()?.atStartOfDay()?.let { and(
                History::createdAt.criteria.greaterThanEqual(it)
            )}
            request.toDt?.toLocalDate()?.plusDays(1)?.atStartOfDay()?.let { and(
                History::createdAt.criteria.lessThan(it)
            )}
            request.fromAmount?.let { and(
                History::amount.criteria.greaterThanEqual(it)
            )}
            request.toAmount?.let { and(
                History::amount.criteria.lessThanEqual(it)
            )}
        }
        val query = CriteriaQuery(criteria, PageRequest.of(0, request.pageSize)).apply {
            sort = History::createdAt.sort(Sort.Direction.DESC)
            searchAfter = request.pageNext
        }

        return template.searchForPage(query, History::class.java).awaitSingle().let { res ->
            ResSearch(
                res.content.map { it.content },
                res.totalElements,
                res.content.lastOrNull()?.sortValues
            )
        }
    }

}

val KProperty<*>.criteria: Criteria
    get() = Criteria(this.name)

fun KProperty<*>.sort(direction: Direction = Sort.Direction.ASC): Sort {
    return Sort.by(Sort.Direction.DESC, this.name)
}

