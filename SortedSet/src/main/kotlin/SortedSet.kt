package seungkyu

import io.lettuce.core.RedisClient
import io.lettuce.core.RedisURI

fun main() {
    val redisClient = RedisClient.create(
        RedisURI.builder()
            .withHost("localhost")
            .withPort(12042)
            .build()
    )

    val connection = redisClient.connect()
    val commands = connection.reactive()

    commands.zadd("game2:scores",
        100.0,
        "user1",
        200.0,
        "user2",
        300.0,
        "user3"
    ).block()

    commands.zrange("game2:scores", 0, Long.MAX_VALUE)
        .map{
            println(it)
        }.blockLast()

    commands.zrangeWithScores("game2:scores", 0, Long.MAX_VALUE).map {
        println(it)
    }.blockLast()

    commands.zincrby("game2:scores", 123123.0, "user2").block()

    commands.zrangeWithScores("game2:scores", 0, Long.MAX_VALUE).map {
        println(it)
    }.blockLast()
}