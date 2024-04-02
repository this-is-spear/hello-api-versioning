package tis.helloapiversioning.version

import org.springframework.web.servlet.mvc.condition.RequestCondition
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import java.lang.reflect.Method

/**
 * @see <a href="https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-requestmapping.html#mvc-ann-requestmapping-composed">Custom Request Mapping Condition</a>
 */
class CustomRequestMappingHandlerMapping : RequestMappingHandlerMapping() {

    /**
     * 요청 받는 메소드에 따라서 버전을 결정하는 커스텀 메소드 조건을 반환합니다.
     * 만약 메소드에 [HeaderApiVersion] 어노테이션이 존재한다면 [HeaderVersionRequestCondition]을 반환합니다.
     *
     * @param method 요청 받는 메소드
     */
    override fun getCustomMethodCondition(method: Method): RequestCondition<*>? {
        if (method.isAnnotationPresent(HeaderApiVersion::class.java)) {
            val annotation = method.getAnnotation(HeaderApiVersion::class.java)
            return HeaderVersionRequestCondition(annotation.value)
        }
        return super.getCustomMethodCondition(method)
    }

    private val prefix = "v"

    /**
     * 요청 받을 때 url prefix 가 v0, v1, ... 등으로 시작하면 버전을 결정하는 커스텀 메소드 조건을 반환합니다.
     * [UrlApiVersion] 애너테이션이 존재하는 경우만 동작하고 추가된 prefix로 버전 값이 결정됩니다.
     */
    override fun getMappingForMethod(method: Method, handlerType: Class<*>): RequestMappingInfo? {
        val mappingInfo = super.getMappingForMethod(method, handlerType)

        if (method.isAnnotationPresent(UrlApiVersion::class.java)) {
            val methodAnnotation = method.getAnnotation(UrlApiVersion::class.java)
            val pattern = methodAnnotation.let { "${prefix}${it.value}" }
            UrlVersionRequestCondition(methodAnnotation.value).let {
                return RequestMappingInfo.paths(pattern).customCondition(it).build().combine(mappingInfo!!)
            }
        }

        return mappingInfo
    }
}
