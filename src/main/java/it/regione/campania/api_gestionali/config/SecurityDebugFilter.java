package it.regione.campania.api_gestionali.config;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
@Order(0) // Prima di tutto
public class SecurityDebugFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        
        System.out.println("=== SECURITY DEBUG ===");
        System.out.println("URI da verificare: " + requestURI);
        System.out.println("Dovrebbe matchare: /turismoweb/api-gestionali/v1/auth/**");
        
        // Test del pattern matching
        AntPathMatcher matcher = new AntPathMatcher();
        boolean shouldMatch = matcher.match("/turismoweb/api-gestionali/v1/auth/**", requestURI);
        System.out.println("Pattern match result: " + shouldMatch);
        
        chain.doFilter(request, response);
    }
}