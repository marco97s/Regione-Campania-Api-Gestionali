package it.regione.campania.api_gestionali.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final String jwtSecret;

    public JwtAuthenticationFilter(String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        System.out.println("=== INIZIO FILTRO JWT ===");
        System.out.println("URL richiesta: " + request.getRequestURL());
        System.out.println("Metodo: " + request.getMethod());

        String token = extractToken(request);
        System.out.println("Token estratto: " + token);

        if (token != null) {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                System.out.println("Claims decodificati: " + claims);

                String cusr = claims.get("cusr", String.class);
                System.out.println("Utente autenticato: " + cusr);

                // Crea authentication con almeno un'autority
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        cusr, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));

                SecurityContextHolder.getContext().setAuthentication(auth);

                System.out.println("SecurityContext impostato: " +
                        SecurityContextHolder.getContext().getAuthentication());

            } catch (Exception e) {
                System.err.println("ERRORE durante l'autenticazione JWT: " + e.getMessage());
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        } else {
            System.out.println("Nessun token JWT trovato");
        }

        System.out.println("Prima di filterChain.doFilter");
        filterChain.doFilter(request, response);
        System.out.println("Dopo filterChain.doFilter");
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}