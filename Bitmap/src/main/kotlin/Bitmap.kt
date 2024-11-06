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

    commands.setbit("request-sompage-20241106", 100, 1).block()
    commands.setbit("request-sompage-20241106", 200, 1).block()

    println(commands.getbit("request-sompage-20241106", 100).block()!!)
    println(commands.getbit("request-sompage-20241106", 50).block()!!)

    println(commands.bitcount("request-sompage-20241106").block()!!)

}