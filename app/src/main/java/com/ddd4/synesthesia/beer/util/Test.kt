package com.ddd4.synesthesia.beer.util

class Test {

    val inlineValue = 12

    fun main() {

        sum(1, 2,
            {
                println(it)
                it
            },
            {
                println(it)
                it
            })

    }

    private inline fun sum(a: Int, b: Int,crossinline action: (Int) -> Int,  description: (String) -> String) {
        action(a + b)
        description("description")
    }

    class Test2() {
        fun get() {
        }
    }
}