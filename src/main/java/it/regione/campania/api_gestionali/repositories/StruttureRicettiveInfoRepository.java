package it.regione.campania.api_gestionali.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import it.regione.campania.api_gestionali.models.StruttureRicettive;
import it.regione.campania.api_gestionali.models.Strutturericettiveinfo;

import java.util.List;
import java.util.Optional;

@Repository
public interface StruttureRicettiveInfoRepository extends JpaRepository<Strutturericettiveinfo, Integer> {

    @Query("SELECT st FROM Strutturericettiveinfo st " +
            "WHERE (:strutturaricettivaid IS NULL OR st.strutturaricettivaid = :strutturaricettivaid)")
    List<Strutturericettiveinfo> findByFilters(@Param("strutturaricettivaid") StruttureRicettive strutturaricettivaid);

    @Query("SELECT st.numstanzeperdisabili FROM Strutturericettiveinfo st WHERE st.strutturaricettivaid.idstrutturaricettiva = :strutturaricettivaid")
    Optional<Integer> findNumStanzePerDisabiliById(@Param("strutturaricettivaid") Integer strutturaricettivaid);

    Strutturericettiveinfo save(Strutturericettiveinfo strutturericettiveinfo);

    @Query("SELECT st FROM Strutturericettiveinfo st WHERE st.strutturaricettivaid.idstrutturaricettiva = :strutturaricettivaid")
    Optional<Strutturericettiveinfo> findByStrutturaricettivaidId(@Param("strutturaricettivaid") Integer strutturericettiveid);

}
