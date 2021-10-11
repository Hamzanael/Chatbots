package com.telelogx.chatbot.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class JwtVerificationProcessor {

    @Autowired
    private lateinit var jwtConfiguration: JwtConfiguration

    fun isValidAuthorizationHeader(header: String?): Boolean {
        return !header.isNullOrBlank() && header.startsWith(jwtConfiguration.tokenPrefix)
    }

    fun extractTokenFromHeader(header: String): String {
        return header.replace("Bearer ", "")
    }

    fun parsingToken(token: String): Jws<Claims> {
        return Jwts
            .parserBuilder()
            .setSigningKey(jwtConfiguration.getSecretKey())
            .build()
            .parseClaimsJws(token)
    }
    @SuppressWarnings("unchecked")
    fun authenticateUser(parsedToken: Jws<Claims>, request: HttpServletRequest) {
        val jwtBody = parsedToken.body
        val authorizationList = (jwtBody["authorities"] as List<Map<String, String>>).map {
            SimpleGrantedAuthority(it["role"])
        }.toList()
        SecurityContextHolder.getContext().authentication = UsernamePasswordAuthenticationToken(
            jwtBody.subject,
            null,
            authorizationList
        )
    }
}