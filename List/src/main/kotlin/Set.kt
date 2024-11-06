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

    commands.sadd("users:500:follow", "100", "200", "300").block()
    commands.srem("users:500:follow", "100").block()

    commands.smembers("users:500:follow").map{
        println(it)
    }.blockLast()

    println(commands.sismember("users:500:follow", "200").block()!!)
    println(commands.sismember("users:500:follow", "120").block()!!)

    println(commands.scard("users:500:follow").block()!!)

    commands.sadd("users:100:follow", "100", "200", "300").block()

    commands.sinter("users:500:follow", "users:100:follow").map{
        println(it)
    }.blockLast()

}