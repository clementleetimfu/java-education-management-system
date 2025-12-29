package io.clementleetimfu.educationmanagementsystem.filter;

import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import io.clementleetimfu.educationmanagementsystem.utils.redis.RedisUtil;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentEmployee;
import io.clementleetimfu.educationmanagementsystem.utils.threadLocal.CurrentRole;
import io.jsonwebtoken.Claims;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class TokenFilter implements Filter {

    private JwtUtil jwtUtil;

    private RedisUtil redisUtil;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Get jwtUtil from Spring application context
        this.jwtUtil = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(JwtUtil.class);
        // Get redisUtil from Spring application context
        this.redisUtil = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(RedisUtil.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            String requestUri = request.getRequestURI();

            if (requestUri.contains("/auth/login") || requestUri.contains("/auth/update-password")) {
                filterChain.doFilter(request, response);
                return;
            }

            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.trim().startsWith("Bearer ")) {
                log.warn("Unauthorized request to {}: missing or invalid token", requestUri);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = authHeader.substring(7).trim(); // remove "Bearer "

            if (!jwtUtil.validateToken(token)) {
                log.warn("Unauthorized request to {}: invalid token", requestUri);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            Claims claims = jwtUtil.parseToken(token);
            Integer employeeId = claims.get("id", Integer.class);

            if (redisUtil.isTokenBlacklisted(token, employeeId)) {
                log.warn("Unauthorized request to {}: blacklisted token", requestUri);
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            CurrentEmployee.set(employeeId);
            CurrentRole.set(claims.get("roleName", String.class));

            filterChain.doFilter(request, response);
        } finally {
            CurrentEmployee.remove();
            CurrentRole.remove();
        }
    }
}