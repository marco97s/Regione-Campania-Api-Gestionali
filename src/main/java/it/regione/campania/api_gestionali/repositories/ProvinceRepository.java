package it.regione.campania.api_gestionali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.regione.campania.api_gestionali.models.Province;

import java.util.Optional;

public interface ProvinceRepository extends JpaRepository<Province, Integer> {

    Optional<Province> findFirstByCodiceistatIgnoreCaseAllIgnoreCase(String codiceistat);

    @Query("SELECT pr FROM Province pr " +
            "WHERE (:descrizione IS NULL OR pr.descrizione = :descrizione)")
    Optional<Province> findByDenominazione(@Param("descrizione") String descrizione);

}