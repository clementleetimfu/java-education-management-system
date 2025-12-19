package io.clementleetimfu.educationmanagementsystem.filter;

import io.clementleetimfu.educationmanagementsystem.utils.jwt.JwtUtil;
import io.clementleetimfu.educationmanagementsystem.utils.thread.CurrentEmployee;
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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Get jwtUtil from Spring application context
        this.jwtUtil = WebApplicationContextUtils
                .getRequiredWebApplicationContext(filterConfig.getServletContext())
                .getBean(JwtUtil.class);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;

            String requestUri = request.getRequestURI();

            if (requestUri.contains("/auth")) {
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
            CurrentEmployee.set(claims.get("id", Integer.class));

            filterChain.doFilter(request, response);
        } finally {
            CurrentEmployee.remove();
        }
    }
}