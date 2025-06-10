package it.regione.campania.api_gestionali.config;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(0) // Prima di tutto
public class SecurityDebugFilter implements Filter {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        System.out.println("=== PRIMA DELLA CATENA FILTRI ===");
        System.out.println("URI: " + httpRequest.getRequestURI());
        System.out.println("Status prima: " + httpResponse.getStatus());
        
        chain.doFilter(request, response);
        
        System.out.println("=== DOPO LA CATENA FILTRI ===");
        System.out.println("Status dopo: " + httpResponse.getStatus());
        System.out.println("Response committed: " + httpResponse.isCommitted());
    }
}