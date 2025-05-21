package it.regione.campania.api_gestionali.controllers;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import it.regione.campania.api_gestionali.models.StruttureApiKey;
import it.regione.campania.api_gestionali.repositories.StruttureApiKeyRepository;
import it.regione.campania.api_gestionali.requests.LoginRequest;
import it.regione.campania.api_gestionali.responses.LoginResponse;

@RestController
@RequestMapping("/v1/auth")
public class AuthController {
    Logger logger = Logger.getLogger(AuthController.class.getName());

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    private StruttureApiKeyRepository struttureApiKeyRepository;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String cusr = loginRequest.getCusr();
        String apiKey = loginRequest.getApiKey();

        logger.info("[START] login() Login attempt with cusr: " + cusr);
        Optional<StruttureApiKey> apiKeyOptional = struttureApiKeyRepository.findByCusrAndApikey(cusr, apiKey);

        if (apiKeyOptional.isPresent()) {
            // Genera il token JWT con cusr come claim
            String token = generateJwtToken(cusr);
            
            logger.info("[END] login() Login successful for cusr: " + cusr);
            return ResponseEntity.ok(new LoginResponse(token, 3600L));
        } else {
            logger.warning("[END] login() Invalid credentials for cusr: " + cusr);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse("Invalid credentials"));
        }
    }

    private String generateJwtToken(String cusr) {
        long expirationTime = 3600 * 1000; // 1 ora in millisecondi
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationTime);

        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(cusr) 
                .claim("cusr", cusr)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key)
                .compact();
    }
}