package it.regione.campania.api_gestionali.repositories;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.regione.campania.api_gestionali.models.StruttureApiKey;

public interface StruttureApiKeyRepository extends JpaRepository<StruttureApiKey, Long> {
    Optional<StruttureApiKey> findByCusrAndApikey(String cusr, String apikey);
}
