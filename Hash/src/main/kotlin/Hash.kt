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

    commands.hset("users:2:info", "name", "hello").block()

    val map = mapOf("email" to "trust1204@gmail.com", "phone" to "123456789")

    commands.hset("users:2:info", map).block()

    commands.hset("users:2:info", "phone", "123333").block()

    println(commands.hget("users:2:info", "email").block())

    var userInfo = commands.hgetall("users:2:info")

    userInfo.map{
        println(it)
    }.blockLast()

    //hincr
    commands.hincrby("users:2:info", "phone", 11).block()

    userInfo = commands.hgetall("users:2:info")

    userInfo.map{
        println(it)
    }.blockLast()
}