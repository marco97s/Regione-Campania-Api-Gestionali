package it.regione.campania.api_gestionali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import it.regione.campania.api_gestionali.models.StruttureRicettive;

import java.util.Optional;

@Repository
public interface StruttureRicettiveRepository extends JpaRepository<StruttureRicettive, Integer> {
    @Query(value = "SELECT sr FROM StruttureRicettive sr WHERE sr.cir = :cir")
    Optional<StruttureRicettive> getStrutturaFromCir(String cir);
}
