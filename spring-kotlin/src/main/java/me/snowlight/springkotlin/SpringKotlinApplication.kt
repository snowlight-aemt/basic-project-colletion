package me.snowlight.springkotlin

import jakarta.persistence.*
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.transaction.annotation.Transactional
import javax.annotation.processing.Generated

@SpringBootApplication
class SpringKotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringKotlinApplication>()
}

@Transactional
class Service

@Entity
@Table
class Person (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id : Long?,

    @Column
    var name : String,

    @Column
    var age : Int,
)