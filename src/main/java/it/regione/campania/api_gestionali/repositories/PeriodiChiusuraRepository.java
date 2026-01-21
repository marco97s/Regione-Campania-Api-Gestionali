package it.regione.campania.api_gestionali.repositories;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.regione.campania.api_gestionali.models.PeriodiChiusura;


@Repository
public interface PeriodiChiusuraRepository extends JpaRepository<PeriodiChiusura, Integer> {
    
    /**
     * Verifica se esistono periodi di chiusura sovrapposti per una struttura
     */
    @Query("SELECT COUNT(p) > 0 FROM PeriodiChiusura p WHERE p.strutturaId.idstrutturaricettiva = :strutturaId " +
           "AND p.periodoEliminato = false " +
           "AND ((p.dataInizioPeriodiChiusura <= :dataFine AND p.dataFinePeriodiChiusura >= :dataInizio))")
    boolean existsOverlappingPeriod(@Param("strutturaId") Integer strutturaId, 
                                   @Param("dataInizio") Date dataInizio, 
                                   @Param("dataFine") Date dataFine);

    /**
     * Trova tutti i periodi di chiusura attivi per una struttura
     */
    @Query("SELECT p FROM PeriodiChiusura p WHERE p.strutturaId.idstrutturaricettiva = :strutturaId " +
           "AND p.periodoEliminato = false ORDER BY p.dataInizioPeriodiChiusura")
    List<PeriodiChiusura> findActivePeriodiByStrutturaId(@Param("strutturaId") Integer strutturaId);

    /**
     * Trova un periodo di chiusura specifico non eliminato
     */
    @Query("SELECT p FROM PeriodiChiusura p WHERE p.idPeriodiChiusura = :id AND p.periodoEliminato = false")
    Optional<PeriodiChiusura> findActiveById(@Param("id") Integer id);

    /**
     * Elimina logicamente un periodo di chiusura
     */
    @Modifying
    @Query("UPDATE PeriodiChiusura p SET p.periodoEliminato = true WHERE p.idPeriodiChiusura = :id")
    void softDeleteById(@Param("id") Integer id);

    /**
     * Trova tutti i periodi di chiusura non eliminati per una struttura con filtro opzionale sull'anno
     */
    @Query("SELECT p FROM PeriodiChiusura p WHERE p.strutturaId.idstrutturaricettiva = :strutturaId " +
           "AND p.periodoEliminato = false " +
           "AND (:anno IS NULL OR YEAR(p.dataInizioPeriodiChiusura) = :anno OR YEAR(p.dataFinePeriodiChiusura) = :anno) " +
           "ORDER BY p.dataInizioPeriodiChiusura")
    List<PeriodiChiusura> findActivePeriodiByStrutturaIdAndYear(@Param("strutturaId") Integer strutturaId, 
                                                               @Param("anno") Integer anno);

    /**
     * Trova tutti i periodi di chiusura non eliminati per tutte le strutture con filtro opzionale sull'anno
     */
    @Query("SELECT p FROM PeriodiChiusura p WHERE p.periodoEliminato = false " +
           "AND (:anno IS NULL OR YEAR(p.dataInizioPeriodiChiusura) = :anno OR YEAR(p.dataFinePeriodiChiusura) = :anno) " +
           "ORDER BY p.strutturaId.idstrutturaricettiva, p.dataInizioPeriodiChiusura")
    List<PeriodiChiusura> findAllActivePeriodiByYear(@Param("anno") Integer anno);

    /**
     * Verifica se una data specifica rientra in un periodo di chiusura per una struttura
     * @param strutturaId ID della struttura ricettiva
     * @param anno Anno della data da verificare
     * @param mese Mese della data da verificare (1-12)
     * @param giorno Giorno della data da verificare
     * @return Il periodo di chiusura se la data rientra in un periodo, null altrimenti
     */
    @Query(value = "SELECT * FROM periodichiusurastruttura p WHERE p.strutturaid = :strutturaId " +
           "AND p.periodoeliminato = false " +
           "AND (DATE(CAST(:anno AS TEXT) || '-' || LPAD(CAST(:mese AS TEXT), 2, '0') || '-' || LPAD(CAST(:giorno AS TEXT), 2, '0')) " +
           "     BETWEEN p.datainizioperiodochiusura AND p.datafineperiodochiusura " +
           "     OR (p.datainizioperiodochiusurastagionale IS NOT NULL " +
           "         AND p.datafineperiodochiusurastagionale IS NOT NULL " +
           "         AND DATE(CAST(:anno AS TEXT) || '-' || LPAD(CAST(:mese AS TEXT), 2, '0') || '-' || LPAD(CAST(:giorno AS TEXT), 2, '0')) " +
           "         BETWEEN p.datainizioperiodochiusurastagionale AND p.datafineperiodochiusurastagionale)) " +
           "ORDER BY p.datainizioperiodochiusura LIMIT 1", 
           nativeQuery = true)
    Optional<PeriodiChiusura> findPeriodoChiusuraByDate(@Param("strutturaId") Integer strutturaId,
                                                        @Param("anno") Integer anno, 
                                                        @Param("mese") Integer mese, 
                                                        @Param("giorno") Integer giorno);
}
