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

    //1. stack
    commands.rpush("stack1", "aaa").block()
    commands.rpush("stack1", "bbb").block()
    commands.rpush("stack1", "ccc").block()

    commands.lrange("stack1", 0, -1).map{ println(it) }.blockLast()
    println("========================================================================================")

    println(commands.rpop("stack1").block())
    println(commands.rpop("stack1").block())
    println(commands.rpop("stack1").block())

    println("========================================================================================")

    // 2. queue
    commands.rpush("queue1", "aaa").block()
    commands.rpush("queue1", "bbb").block()
    commands.rpush("queue1", "ccc").block()

    commands.lrange("queue1", 0, -1).map{ println(it) }.blockLast()
    println("========================================================================================")

    println(commands.lpop("queue1").block())
    println(commands.lpop("queue1").block())
    println(commands.lpop("queue1").block())

    println("========================================================================================")

    // 3. block brpop, blpopqueue
    commands.rpush("queue1", "aaa").block()
    commands.rpush("queue1", "bbb").block()
    commands.rpush("queue1", "ccc").block()

    commands.lrange("queue1", 0, -1).map{ println(it) }.blockLast()
    println("========================================================================================")

    println(commands.blpop(10, "queue1").block())
    println(commands.blpop(10, "queue1").block())
    println(commands.blpop(10, "queue1").block())

    println("========================================================================================")
}