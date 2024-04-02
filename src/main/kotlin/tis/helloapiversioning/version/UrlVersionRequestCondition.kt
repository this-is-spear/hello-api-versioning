package tis.helloapiversioning.version

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.mvc.condition.RequestCondition
import org.springframework.web.servlet.mvc.method.RequestMappingInfo


class UrlVersionRequestCondition(private val value: Int) : RequestCondition<UrlVersionRequestCondition> {
    /**
     * [RequestMappingInfo.combine]을 통해 조합할 때 [RequestCondition.combine]을 호출하게 됩니다.
     * 이런 경우를 대비해 combine 메소드를 구현합니다.
     */
    override fun combine(other: UrlVersionRequestCondition): UrlVersionRequestCondition {
        return UrlVersionRequestCondition(other.value)
    }

    override fun getMatchingCondition(request: HttpServletRequest): UrlVersionRequestCondition? {
        request.requestURI?.let { uri ->
            val last = uri.split("/")[1].substring(1)
            val version = last.toIntOrNull()

            if (version == value) {
                return this
            }
        }

        return null
    }

    override fun compareTo(other: UrlVersionRequestCondition, request: HttpServletRequest): Int {
        throw UnsupportedOperationException("Not supported")
    }
}
