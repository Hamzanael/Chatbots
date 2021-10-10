package com.telelogx.chatbot.security.jwt

import com.fasterxml.jackson.databind.ObjectMapper
import com.telelogx.chatbot.service.exceptions.FailureAuthenticationException
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.sql.Date
import java.time.LocalDate
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class JwtAuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private var jwtConfiguration: JwtConfiguration
) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    override fun attemptAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse
    ): Authentication {
        try {
            val authenticationRequest: UsernameAndPasswordRequest = ObjectMapper()
                .readValue(request.inputStream, UsernameAndPasswordRequest::class.java)
            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    authenticationRequest.username,
                    authenticationRequest.password
                )
            )
        } catch (exception: IOException) {
            throw RuntimeException(exception)
        } catch (failureAuthentication: AuthenticationException) {
            throw FailureAuthenticationException("Authentication failed", failureAuthentication)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest,
        response: HttpServletResponse,
        chain: FilterChain,
        authResult: Authentication
    ) {
        val token = Jwts.builder()
            .setSubject(authResult.name)
            .claim("authorities", authResult.authorities)
            .setIssuedAt(java.util.Date())
            .setExpiration(Date.valueOf(LocalDate.now().plusDays(jwtConfiguration.tokenExpirationAfterDays.toLong())))
            .signWith(jwtConfiguration.getSecretKey())
            .compact()
        setAuthorizationHeaders(response, token)
    }

    private fun setAuthorizationHeaders(response: HttpServletResponse, token: String) {
        response.setHeader("Access-Control-Expose-Headers", "token")
        response.addHeader("token", token)
    }
}