package it.regione.campania.api_gestionali.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.regione.campania.api_gestionali.models.StruttureRicettive;
import it.regione.campania.api_gestionali.models.Strutturericettiveindirizzi;
import it.regione.campania.api_gestionali.models.Strutturericettiveinfo;
import it.regione.campania.api_gestionali.repositories.StruttureRicettiveInfoRepository;
import it.regione.campania.api_gestionali.repositories.StruttureRicettiveRepository;
import it.regione.campania.api_gestionali.responses.StrutturaRicettivaItem;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/v1/anagrafica")
public class StrutturaRicettivaController {
    private static final Logger logger = Logger.getLogger(StrutturaRicettivaController.class.getName());

    @Autowired
    private StruttureRicettiveRepository struttureRicettiveRepository;

    @Autowired
    private StruttureRicettiveInfoRepository struttureRicettiveInfoRepository;

    @GetMapping()
    public ResponseEntity<Object> getStrutturaRicettiva(Authentication authentication) {
        String cusr = authentication.getName();
        logger.info("Richiesta dati struttura per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOptional = struttureRicettiveRepository.getStrutturaFromCir(cusr);

        StrutturaRicettivaItem response = new StrutturaRicettivaItem();

        if (strutturaOptional.isPresent()) {
            StruttureRicettive struttura = strutturaOptional.get();
            response.setDenominazione(struttura.getDenominazione());
            response.setCin(struttura.getCin());
            response.setCusr(cusr);
            if(struttura.getIndirizzi() != null && !struttura.getIndirizzi().isEmpty()) {
                Strutturericettiveindirizzi indirizzo = struttura.getIndirizzi().getFirst();
                response.setIndirizzo(indirizzo.getIndirizzoubicazione());
                response.setCivico(indirizzo.getCivicoubicazione());
                response.setCap(indirizzo.getCapubicazione());
            }
            response.setComune(struttura.getComuneubicazioneid().getDescrizione());
            response.setProvincia(struttura.getProvubicazioneid().getDescrizione());
            response.setMacroCategoria(struttura.getCodmacrocategclassifnazid().getCodicedenominazione());
            response.setCategoria(struttura.getCodcategclassifnazid().getCodicedenominazione());
            response.setSottoCategoria(struttura.getCodsottocategclassifnazid().getCodicedenominazione());
            response.setNumeroCamere(struttura.getNumcamere());
            response.setNumeroUnitaAbitative(struttura.getNumunitaabitative());
            response.setNumeroPiazzole(struttura.getNumpiazzole());
            response.setNumeroPostiLetto(struttura.getNumpostiletto());
            response.setNumeroBagni(struttura.getNumBagni());
            response.setEmail(struttura.getEmail());
            Strutturericettiveinfo info = struttureRicettiveInfoRepository.findByStrutturaricettivaidId(struttura.getIdstrutturaricettiva())
                    .orElse(new Strutturericettiveinfo());
            response.setSitoWeb(info.getWebsite());
            response.setCodiceFiscale(info.getOwnerstrutturaid() != null ? info.getOwnerstrutturaid().getCf() : null);
        }
        return ResponseEntity.ok(response);
    }
    
}
