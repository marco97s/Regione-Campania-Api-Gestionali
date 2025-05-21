package it.regione.campania.api_gestionali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.regione.campania.api_gestionali.models.C59Italiani;
import it.regione.campania.api_gestionali.models.Province;
import it.regione.campania.api_gestionali.models.StruttureRicettive;

import java.util.List;

@Repository
public interface C59ItalianiRepository extends JpaRepository<C59Italiani, Integer> {
        @Modifying
        @Query("DELETE FROM C59Italiani c WHERE c.strutturaid.idstrutturaricettiva = :idStruttura AND ((c.anno > :anno) OR (c.anno = :anno AND c.mese > :mese) OR (c.anno = :anno AND c.mese = :mese AND c.giorno > :giorno))")
        void deleteItalianiByDate(@Param("anno") int anno, @Param("mese") int mese, @Param("giorno") int giorno, @Param("idStruttura") int idStruttura);

        @Query("SELECT c59 FROM C59Italiani c59 JOIN StruttureRicettive st ON c59.strutturaid = st WHERE st.provubicazioneid = :provubicazioneid AND c59.mese = :mese AND c59.anno = :anno")
        List<C59Italiani> findByStrutturaAndDate(@Param("provubicazioneid") Province provubicazioneid,
                        @Param("mese") Integer mese, @Param("anno") Integer anno);

        @Query("DELETE FROM C59Italiani c " +
                        "WHERE (:strutturaid IS NULL OR c.strutturaid = :strutturaid) " +
                        "AND (:anno IS NULL OR c.anno = :anno) " +
                        "AND (:mese IS NULL OR c.mese = :mese) " +
                        "AND (:giorno IS NULL OR c.giorno = :giorno)")
        @Modifying
        Integer deleteByStrutturaAndDate(@Param("strutturaid") StruttureRicettive struttureRicettive,
                        @Param("anno") Integer anno,
                        @Param("mese") Integer mese,
                        @Param("giorno") Integer giorno);

        @Query("SELECT c FROM C59Italiani c " +
        "WHERE (:strutturaid IS NULL OR c.strutturaid.idstrutturaricettiva = :strutturaid) " +
        "AND (:anno IS NULL OR c.anno = :anno) " +
        "AND (:mese IS NULL OR c.mese = :mese) " +
        "AND (:giorno IS NULL OR c.giorno = :giorno)")
        @Modifying
        List<C59Italiani> findAllByStrutturaAndDate(@Param("strutturaid") Integer struttureRicettive,
                        @Param("anno") Integer anno,
                        @Param("mese") Integer mese,
                        @Param("giorno") Integer giorno);

}
