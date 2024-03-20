package me.snowlight.springkotlinelasticsearch.model

import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HistoryRepository: CoroutineCrudRepository<History, Long>