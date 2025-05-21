package it.regione.campania.api_gestionali.repositories;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.regione.campania.api_gestionali.models.Modc59;
import it.regione.campania.api_gestionali.models.StruttureRicettive;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface Modc59Repository extends JpaRepository<Modc59, Integer> {

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (m.validato = true) " +
            "ORDER BY m.anno DESC, m.mese DESC")
    List<Modc59> findValidi();

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (m.validato = false OR m.validato IS NULL)")
    List<Modc59> findNonValidiByStruttura();

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (:strutturaid IS NULL OR m.strutturaid = :strutturaid) " +
            "AND (:mese IS NULL OR m.mese = :mese)" +
            "AND (:anno IS NULL OR m.anno = :anno)")
    List<Modc59> findForMonthAndYearWithStruttura(@Param("strutturaid") StruttureRicettive struttureRicettive,
                                                  @Param("mese") Integer mese,
                                                  @Param("anno") Integer anno);

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (:mese IS NULL OR m.mese = :mese)" +
            "AND (:anno IS NULL OR m.anno = :anno)")
    List<Modc59> findForMonthAndYear(@Param("mese") Integer mese,
                                     @Param("anno") Integer anno);

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (:strutturaid IS NULL OR m.strutturaid = :strutturaid) " +
            "AND (:mese IS NULL OR m.mese = :mese) " +
            "AND (:anno IS NULL OR m.anno = :anno) " +
            "AND (:giorno IS NULL OR m.giorno = :giorno)" +
            "AND (m.validato = false OR m.validato IS NULL)")
    List<Modc59> findModC59ForAllDate(@Param("strutturaid") StruttureRicettive struttureRicettive,
                                      @Param("mese") Integer mese,
                                      @Param("anno") Integer anno,
                                      @Param("giorno") Integer giorno);

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (:mese IS NULL OR m.mese = :mese) " +
            "AND (:anno IS NULL OR m.anno = :anno) " +
            "AND (m.validato = false OR m.validato IS NULL)")
    List<Modc59> findModC59ForMonthAndYear(@Param("mese") Integer mese,
                                           @Param("anno") Integer anno);


    @Modifying
    @Transactional
    @Query("UPDATE Modc59 m SET m.validato = true WHERE m.id = :id")
    Integer validateModC59(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("UPDATE Modc59 m SET m.validato = true WHERE m.anno = :anno AND m.mese = :mese")
    Integer validateModC59AnnoEMese(@Param("anno") Integer anno, 
                                     @Param("mese") Integer mese);

    @Query("SELECT m FROM Modc59 m " +
            "WHERE (:strutturaid IS NULL OR m.strutturaid = :strutturaid)" +
            "ORDER BY m.id ASC")
    List<Modc59> findLastEntry(@Param("strutturaid") StruttureRicettive struttureRicettive);

    @Modifying
    @Transactional
    @Query("DELETE FROM Modc59 m WHERE m.strutturaid.idstrutturaricettiva = :idStruttura AND ((m.anno > :anno) OR (m.anno = :anno AND m.mese > :mese) OR (m.anno = :anno AND m.mese = :mese AND m.giorno >= :giorno))")
    Integer deleteByDate(@Param("anno") Integer anno,
                         @Param("mese") Integer mese,
                         @Param("giorno") Integer giorno,
                         @Param("idStruttura") Integer idStruttura
                         );


    @Query("SELECT MAX(TO_DATE(m.anno || '-' || m.mese || '-' || m.giorno, 'YYYY-MM-DD')) FROM Modc59 m WHERE m.strutturaid = :strutturaid")
    Optional<LocalDate> findMaxC59ByStruttura(@Param("strutturaid") StruttureRicettive struttureRicettive);

    @Query("SELECT MAX(CONCAT(m.mese, '-', m.anno)) FROM Modc59 m WHERE m.validato = true")
    Optional<String> getUltimoMeseValidato();
}
