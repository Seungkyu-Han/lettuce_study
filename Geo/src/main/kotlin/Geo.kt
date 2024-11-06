package seungkyu

import io.lettuce.core.GeoArgs
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

    commands.geoadd("stores2:geo", 128.61345648765564, 35.893430729254554, "nuri").block()
    commands.geoadd("stores2:geo", 128.61152529716492, 35.888120029245506, "bock").block()

    val dist = commands.geodist("stores2:geo", "nuri", "bock", GeoArgs.Unit.m).block()

    println(dist!!)

}