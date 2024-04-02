package tis.helloapiversioning.version

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.servlet.mvc.condition.RequestCondition


class HeaderVersionRequestCondition(private val value: Int) : RequestCondition<HeaderVersionRequestCondition> {
    override fun combine(other: HeaderVersionRequestCondition): HeaderVersionRequestCondition {
        throw UnsupportedOperationException("Not supported")
    }

    override fun getMatchingCondition(request: HttpServletRequest): HeaderVersionRequestCondition? {
        val version = request.getHeader("X-API-VERSION")

        if (version == value.toString()) {
            return this
        }

        return null
    }

    override fun compareTo(other: HeaderVersionRequestCondition, request: HttpServletRequest): Int {
        throw UnsupportedOperationException("Not supported")
    }
}
