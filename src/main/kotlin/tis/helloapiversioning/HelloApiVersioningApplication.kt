package tis.helloapiversioning

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class HelloApiVersioningApplication

fun main(args: Array<String>) {
    runApplication<HelloApiVersioningApplication>(*args)
}
