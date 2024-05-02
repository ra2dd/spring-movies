package com.example.movies.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

// uncomment to make filter work
//@Component
public class TokenLoggingFilter extends GenericFilterBean {

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;

        // Extract the Authorization header
        String authorizationHeader = httpRequest.getHeader("Authorization");

        // Check if the header is present and starts with "Bearer"
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // Extract the token
            String token = authorizationHeader.substring(7);

            // Log the token
            String tokenLog = String.format("Bearer token received: %s", token);
            System.out.println(tokenLog);
        }

        // Continue the filter chain
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
