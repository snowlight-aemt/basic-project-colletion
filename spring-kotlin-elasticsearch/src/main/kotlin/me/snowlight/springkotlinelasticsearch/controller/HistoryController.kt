package me.snowlight.springkotlinelasticsearch.controller

import me.snowlight.springkotlinelasticsearch.model.History
import me.snowlight.springkotlinelasticsearch.model.HistoryRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlinx.coroutines.flow.Flow
import me.snowlight.springkotlinelasticsearch.model.HistoryNativeRepository
import me.snowlight.springkotlinelasticsearch.model.QrySearch
import me.snowlight.springkotlinelasticsearch.model.ResSearch
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import java.lang.Error

@RestController
@RequestMapping("/history")
class HistoryController(
    private val repository: HistoryRepository,
    private val historyNativeRepository: HistoryNativeRepository,
) {
    @GetMapping("/{orderId}")
    suspend fun get(@PathVariable orderId: Long): History? {
        return repository.findById(orderId)
    }

    @GetMapping("/all")
    suspend fun getAll(): Flow<History> {
        return repository.findAll()
    }

    @PostMapping
    suspend fun save(@RequestBody request: ReqSaveHistory): History {
        val doc = repository.findById(request.orderId)?.let { history ->
            request.userId?.let { history.userId = it }
            request.description?.let { history.description = it }
            request.amount?.let { history.amount = it }
            request.pgStatus?.let { history.pgStatus = it }
            request.createdAt?.let { history.createdAt = it }
            request.updatedAt?.let { history.updatedAt = it }

            history
        } ?: request.toHistory()

        return repository.save(doc)
    }

    @DeleteMapping("/{orderId}")
    suspend fun delete(@PathVariable orderId: Long) {
        return repository.deleteById(orderId)
    }

    @DeleteMapping("/all")
    suspend fun deleteAll() {
        return repository.deleteAll()
    }

    @GetMapping("/search")
    suspend fun search(@ModelAttribute request: QrySearch): ResSearch {
        return historyNativeRepository.search(request)
    }
}