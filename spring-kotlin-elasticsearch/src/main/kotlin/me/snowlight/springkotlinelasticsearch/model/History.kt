package me.snowlight.springkotlinelasticsearch.model

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.DateFormat
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType
import java.time.LocalDateTime

@Document(indexName = "history")
class History (
    @Id
    val orderId: Long = 0,
    var userId: Long,
    var description: String = "",
    var amount: Long = 0,
    var pgStatus: PgStatus = PgStatus.CREATE,
    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
    var createdAt: LocalDateTime = LocalDateTime.now(),
    @Field(type = FieldType.Date, format = [DateFormat.date_hour_minute_second_millis])
    var updatedAt: LocalDateTime = LocalDateTime.now(),
)