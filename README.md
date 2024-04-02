
## 버저닝 관리 방법

버저닝을 관리할 때 `RequestMapping`에 값을 적재하는 방법은 공통 관리가 어려워보였다.
그래서 애너테이션을 활용해 버저닝을 관리하는 방법을 찾았다.

아래처럼 버저닝 값이 들어온다고 가정해보자.

```http request
### header version v1

GET http://localhost:8080/hello/header
X-API-VERSION: 1

### header version v2

GET http://localhost:8080/hello/header
X-API-VERSION: 2

### url version v1

GET http://localhost:8080/v1/hello/url

### url version v2

GET http://localhost:8080/v2/hello/url
```

원하는 건 애너테이션으로 충분히 핸들링이 가능하면 좋겠다는 거다.

```kotlin
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
```

[spring mapping request # custom annotations](https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html#mvc-ann-requestmapping-composed)에서는 RequestMappingHandlerMapping 서브 클래스를 만들어 getCustomMethodCondition 메서드를 오버라이딩 한 후 커스텀 RequestCondition 로 제어하면 된다고 한다.

헤더를 비교하는 건 getCustomMethodCondition 로도 가능하지만 url 이 변경되는 건 getMappingForMethod 를 활용해 커스텀하게 제어해야 한다.

```kotlin
class CustomRequestMappingHandlerMapping : RequestMappingHandlerMapping() {

    // 헤더 버전 관리
    override fun getCustomMethodCondition(method: Method): RequestCondition<*>? {
        //...
    }

    // url 버전 관리
    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        //...
    }
}
```

그리고 `WebMvcConfigurer` 를 구현하여 `CustomRequestMappingHandlerMapping` 을 빈으로 등록해주면 된다.

```kotlin
@Configuration
class VersioningConfiguration {
    @Bean
    fun webRegistration(): WebMvcRegistrations {
        return object : WebMvcRegistrations {
            override fun getRequestMappingHandlerMapping(): RequestMappingHandlerMapping {
                return CustomRequestMappingHandlerMapping()
            }
        }
    }
}
```

## 마지막으로

버저닝 고민할 때 팀원이었던 [JoeCP17](https://github.com/JoeCP17/api-version-management/blob/main/src/main/kotlin/com/example/apiversionmanagement/mimetype/VersionResourceRequestCondition.kt)님의 조언으로 애너테이션 관리를 고민하게 됐고, 결과가 만들어졌다.
이것저것 시도할 수 있어서 재밌었다.
