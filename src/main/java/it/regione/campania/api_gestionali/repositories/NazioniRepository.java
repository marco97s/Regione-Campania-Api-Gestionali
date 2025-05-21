package it.regione.campania.api_gestionali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import it.regione.campania.api_gestionali.models.Nazioni;

import java.util.List;
import java.util.Optional;

public interface NazioniRepository extends JpaRepository<Nazioni, Integer>, JpaSpecificationExecutor<Nazioni> {

    Optional<Nazioni> findFirstByCodeiso3IgnoreCaseAllIgnoreCase(String codeiso3);

    Optional<Nazioni> findFirtsByCodiceArchivio(String codiceArchivio);

    Optional<Nazioni> findFirtsByCodiceEpt(Integer codiceEpt);

    Optional<Nazioni> findByIdnazione(Integer idnazione);

    @Query("select n from Nazioni n order by n.descrizione")
    List<Nazioni> findByOrderByDescrizioneAsc();

    @Query("select n from Nazioni n where n.descrizione = :descrizione")
    Optional<Nazioni> findByDescrizione(@Param("descrizione") String descrizione);

}