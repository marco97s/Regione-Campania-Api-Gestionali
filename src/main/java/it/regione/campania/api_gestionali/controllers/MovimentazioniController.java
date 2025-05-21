package it.regione.campania.api_gestionali.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.regione.campania.api_gestionali.models.C59Italiani;
import it.regione.campania.api_gestionali.models.C59Stranieri;
import it.regione.campania.api_gestionali.models.StruttureRicettive;
import it.regione.campania.api_gestionali.repositories.C59ItalianiRepository;
import it.regione.campania.api_gestionali.repositories.C59StranieriRepository;
import it.regione.campania.api_gestionali.repositories.StruttureRicettiveRepository;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponse;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponseItem;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponseItemMovimentazione;

@RestController
@RequestMapping("/v1/movimentazione")
public class MovimentazioniController {
    private static final Logger logger = Logger.getLogger(MovimentazioniController.class.getName());

    @Autowired
    private C59ItalianiRepository c59ItalianiRepository;

    @Autowired
    private C59StranieriRepository c59StranieriRepository;

    @Autowired
    private StruttureRicettiveRepository struttureRicettiveRepository;

    /**
     * Restituisce l'archivio delle movimentazioni per la struttura dell'utente
     * autenticato.
     *
     * @param dataInizio     La data di inizio per il filtro delle movimentazioni
     *                       (formato ddMMyyyy).
     * @param offset         L'offset per la paginazione (default: 1).
     * @param authentication L'oggetto Authentication contenente le informazioni
     *                       sull'utente autenticato.
     * @return Un oggetto ResponseEntity contenente la risposta con le
     *         movimentazioni.
     */
    @GetMapping()
    public ResponseEntity<MovimentazioniResponse> getArchivioMovimentazioni(
            @RequestParam(name = "dataInizio", required = true) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate dataInizio,
            @RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
            Authentication authentication) {

        String cusr = authentication.getName();
        logger.info("Richiesta archivio movimentazioni per cusr: " + cusr +
                " - Data inizio: " + dataInizio +
                " - Offset: " + offset);

        MovimentazioniResponse response = new MovimentazioniResponse();
        Optional<StruttureRicettive> struttureRicettive = struttureRicettiveRepository.getStrutturaFromCir(cusr);

        if (struttureRicettive.isEmpty()) {
            logger.warning("Struttura non trovata per cusr: " + cusr);
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Struttura non trovata"));
        } else if (struttureRicettive.get().getDatafineattivita() != null) {
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Struttura cessata"));
        }

        for (int i = 0; i < offset; i++) {
            MovimentazioniResponseItem item = new MovimentazioniResponseItem();
            item.setDataRilevazione(String.format("%02d%02d%04d", dataInizio.getDayOfMonth(),
                    dataInizio.getMonthValue(), dataInizio.getYear()));
            item.setMovimentazioni(new ArrayList<>());
            item.setCamereOccupate(0);
            List<C59Italiani> italiani = c59ItalianiRepository.findAllByStrutturaAndDate(
                    struttureRicettive.get().getIdstrutturaricettiva(), dataInizio.getYear(),
                    dataInizio.getMonthValue(), dataInizio.getDayOfMonth());
            fillItaliani(italiani, item);
            List<C59Stranieri> stranieri = c59StranieriRepository.findAllByStrutturaAndDate(
                struttureRicettive.get().getIdstrutturaricettiva(), dataInizio.getYear(),
                dataInizio.getMonthValue(), dataInizio.getDayOfMonth());
            fillStranieri(stranieri, item);
            if (response.getGiornate() != null) {
                response.getGiornate().add(item);
            } else {
                response.setGiornate(new ArrayList<>());
                response.getGiornate().add(item);
            }
            dataInizio.plusDays(1);
        }
        return ResponseEntity.ok(response);
    }

    private void fillItaliani(List<C59Italiani> italiani, MovimentazioniResponseItem item) {
        for (C59Italiani i : italiani) {
            item.getMovimentazioni().add(new MovimentazioniResponseItemMovimentazione(
                null,
                i.getProvinciaid().getCodiceistat(),
                i.getArrivati(),
                i.getPresenti(),
                i.getPartiti()
            ));
            if (i.getCamere() != null) {
                item.setCamereOccupate(item.getCamereOccupate() + i.getCamere());
            }
        }
    }

    private void fillStranieri(List<C59Stranieri> stranieri, MovimentazioniResponseItem item) {
        for (C59Stranieri s : stranieri) {
            item.getMovimentazioni().add(new MovimentazioniResponseItemMovimentazione(
                String.format("%03d",s.getNazioneid().getCodiceEpt()),
                null,
                s.getArrivati(),
                s.getPresenti(),
                s.getPartiti()
            ));
            if (s.getCamere() != null) {
                item.setCamereOccupate(item.getCamereOccupate() + s.getCamere());
            }
        }
    }
}