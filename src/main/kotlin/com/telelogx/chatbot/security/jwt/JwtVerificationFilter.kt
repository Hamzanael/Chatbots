package com.telelogx.chatbot.security.jwt

import io.jsonwebtoken.JwtException
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtVerificationFilter(
    private var verificationProcessor: JwtVerificationProcessor
) : OncePerRequestFilter() {

    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filter: FilterChain) {
        val authorizationHeader = request.getHeader("Authorization")
        if (verificationProcessor.isValidAuthorizationHeader(authorizationHeader)) {
            verificationProcessor.authenticateUser(getToken(authorizationHeader), request)
        }
        filter.doFilter(request, response)
    }

    private fun getToken(authorizationHeader: String) = try {
        verificationProcessor.parsingToken(
            verificationProcessor.extractTokenFromHeader(authorizationHeader)
        )
    } catch (jwtException: JwtException) {
        throw IllegalStateException(
            "Token cannot be trusted due to following exception $jwtException"
        )
    }

}