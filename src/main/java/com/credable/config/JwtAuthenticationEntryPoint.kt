package com.credable.config;

import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.Serializable
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class JwtAuthenticationEntryPoint : AuthenticationEntryPoint, Serializable {

    private val serialVersionUID = -7858869558953243875L

    @Throws(java.io.IOException::class)
    override fun commence(
        request: HttpServletRequest?, response: HttpServletResponse,
        authException: AuthenticationException?
    ) {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized")
    }
}
