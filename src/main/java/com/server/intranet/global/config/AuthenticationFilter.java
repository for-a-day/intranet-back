package com.server.intranet.global.config;

import com.server.intranet.login.service.impl.LoginServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class AuthenticationFilter extends OncePerRequestFilter {

    private final LoginServiceImpl loginService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(LoginServiceImpl loginService, JwtUtil jwtUtil) {
        this.loginService = loginService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        String token = null;
        String employeeId = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            token = authorizationHeader.substring(7);
            System.out.println("Extracted token: " + token); // 토큰 출력
            try {
                Claims claims = jwtUtil.parseToken(token);
                employeeId = claims.getSubject();
                String roles = (String) claims.get("roles"); // 토큰에서 권한 정보 추출

                UserDetails userDetails = new User(employeeId, "", AuthorityUtils.commaSeparatedStringToAuthorityList(roles));
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                logger.error("JWT Token validation error: ", e);
            }
        } else {
            logger.error("Authorization header is missing or does not start with Bearer");
        }

        filterChain.doFilter(request, response);
    }
}
