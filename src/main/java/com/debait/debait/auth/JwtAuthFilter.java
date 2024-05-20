package com.debait.debait.auth;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    // Request Header 에서 토큰 정보를 꺼내오기 위한 메소드
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = resolveToken(request);

            if(token != null) {
                TokenUserInfo tokenUserInfo = tokenProvider.validateToken(token);
//                log.info("token user info : {}", tokenUserInfo);
                if(tokenUserInfo != null) {
                    List<SimpleGrantedAuthority> authorityList = new ArrayList<>();
                    authorityList.add(new SimpleGrantedAuthority(tokenUserInfo.getUserId()));

                    AbstractAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            tokenUserInfo,
                            null,
                            authorityList
                    );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                } else {
                    log.warn("토큰이 유효하지 않습니다.");
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                }
            }
        } catch (Exception e) {
            log.warn("예기치 않은 에러가 발생했습니다. : {}", e.getMessage());
        }
        filterChain.doFilter(request, response);
    }
}