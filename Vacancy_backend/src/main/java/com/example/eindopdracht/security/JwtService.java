/**\
 * --------------------------------------------------------------------------------
 * Vacancy Web Application
 * Java Spring Boot Backend project
 * Student Name: Mustafa Dogan
 * Date:05 February 2025
 * --------------------------------------------------------------------------------
 * Security class : It is security related concern.
 */
package com.example.eindopdracht.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {
        private final static String SECRET_KEY = "yabbadabbadoo";

        public String extractUsername(String token) {
            return extractClaim(token, Claims::getSubject);
        }

        private Date extractExpiration(String token) {
            return extractClaim(token, Claims::getExpiration);
        }

        private <T> T extractClaim(String token, Function<Claims, T>
                claimsResolver) {
            final Claims claims = extractAllClaims(token);
            return claimsResolver.apply(claims);
        }

        private Claims extractAllClaims(String token) {
            return
                    Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        }

        private Boolean isTokenExpired(String token) {
            return extractExpiration(token).before(new Date());
        }

        public String generateToken(UserDetails userDetails) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("username", userDetails.getUsername());
            claims.put("roles", userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
            return createToken(claims, userDetails.getUsername());
        }
        private String createToken(Map<String, Object> claims, String
                subject) {
            long validPeriod = 1000 * 60 * 60 * 24 * 10; // 10 days in ms
            long currentTime = System.currentTimeMillis();
            return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(subject)
                    .setIssuedAt(new Date(currentTime))
                    .setExpiration(new Date(currentTime + validPeriod))
                    .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                    .compact();
        }

        public Boolean validateToken(String token, UserDetails
                userDetails) {
            final String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) &&
                    !isTokenExpired(token);
        }
}
