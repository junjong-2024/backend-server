package com.debait.debait.auth;

import com.debait.debait.user.entity.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.lang.reflect.Member;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
    private static final String AUTHORITIES_KEY = "auth";
    private final String secret;
    private final long tokenValidityInMilliseconds;
    private Key key;

    public TokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds) {

        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
    }

    // 빈이 생성되고 주입을 받은 후에 secret값을 Base64 Decode해서 key 변수에 할당하기 위해
    @Override
    public void afterPropertiesSet() {
        // Key 생성 방법 변경
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    }


    public String createToken(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Authentication object cannot be null");
        }

        // 사용자의 ID를 추출
        String user_id = user.getId();

        // 토큰의 expire 시간을 설정
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        Map<String, String> claims = new HashMap<>();
        claims.put("userId", user_id);

        return Jwts.builder()
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())
                        , SignatureAlgorithm.HS512)
                .setIssuer("debait")
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .setSubject(user_id)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            // 사용자의 식별자 추출
            String username = claims.getSubject();

            // 사용자의 권한 정보 추출
            Collection<? extends GrantedAuthority> authorities =
                    Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toList());

            // 사용자의 Authentication 객체 생성하여 반환
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public TokenUserInfo validateToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();

        System.out.println("claims : " + claims);

        String userId = claims.get("sub", String.class);

        System.out.println("user id : " + userId);
        if (userId == null) return null;

        return TokenUserInfo.builder().userId(userId).build();

    }

}