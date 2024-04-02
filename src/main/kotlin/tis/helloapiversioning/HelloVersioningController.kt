package tis.helloapiversioning

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import tis.helloapiversioning.version.HeaderApiVersion
import tis.helloapiversioning.version.UrlApiVersion

@RestController
class HelloVersioningController {
    @GetMapping("/hello/header")
    @HeaderApiVersion(1)
    fun exampleHeaderVersionV1(): String = "Header version is 1"
    @GetMapping("/hello/header")
    @HeaderApiVersion(2)
    fun exampleHeaderVersionV2(): String = "Header version is 2"
    @GetMapping("/hello/url")
    @UrlApiVersion(1)
    fun exampleUrlVersionV1(): String = "URL version is 1"
    @GetMapping("/hello/url")
    @UrlApiVersion(2)
    fun exampleUrlVersionV2(): String = "URL version is 2"
}
