package com.vferneda.restwithspringboot.security.jwt;

import com.vferneda.restwithspringboot.exception.InvalidJwtAuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key:secret}")
    private String secretKey = "secret";

    @Value("${security.jwt.token-lenght:3600000}")
    private long validityInMilliseconds = 3600000; //1h

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    public void init() {
        this.secretKey = Base64.getEncoder().encodeToString((secretKey.getBytes()));
    }

    public String createToken(String userName, List<String> roles) {
        final Claims claims = Jwts.claims().setSubject(userName);
        claims.put("roles", roles);

        final LocalDateTime now = LocalDateTime.now();
        final LocalDateTime validity = now.plusSeconds(TimeUnit.MILLISECONDS.toSeconds(validityInMilliseconds));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(Date.from(now.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(validity.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        final UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUserName(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUserName(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        final String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            final Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (Exception exc) {
            throw new InvalidJwtAuthenticationException("Expired or invalid token!");
        }
    }

}
