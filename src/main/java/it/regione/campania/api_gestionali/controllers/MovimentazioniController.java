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
import it.regione.campania.api_gestionali.models.Modc59;
import it.regione.campania.api_gestionali.models.Nazioni;
import it.regione.campania.api_gestionali.models.Province;
import it.regione.campania.api_gestionali.models.StruttureRicettive;
import it.regione.campania.api_gestionali.repositories.C59ItalianiRepository;
import it.regione.campania.api_gestionali.repositories.C59StranieriRepository;
import it.regione.campania.api_gestionali.repositories.Modc59Repository;
import it.regione.campania.api_gestionali.repositories.NazioniRepository;
import it.regione.campania.api_gestionali.repositories.ProvinceRepository;
import it.regione.campania.api_gestionali.repositories.StruttureRicettiveRepository;
import it.regione.campania.api_gestionali.requests.MovimentazioneRequestItemMovimentazione;
import it.regione.campania.api_gestionali.requests.MovimentazioniRequest;
import it.regione.campania.api_gestionali.requests.MovimentazioniRequestItem;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponse;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponseItem;
import it.regione.campania.api_gestionali.responses.MovimentazioniResponseItemMovimentazione;
import jakarta.transaction.Transactional;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private NazioniRepository nazioniRepository;

    @Autowired
    private Modc59Repository modc59Repository;

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
            dataInizio = dataInizio.plusDays(1);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * Inserisce le movimentazioni per la struttura dell'utente autenticato.
     *
     * @param request        L'oggetto MovimentazioniRequest contenente le
     *                       movimentazioni da inserire.
     * @param authentication L'oggetto Authentication contenente le informazioni
     *                       sull'utente autenticato.
     * @return Un oggetto ResponseEntity con lo stato della richiesta.
     */
    @PostMapping()
    @Transactional
    public ResponseEntity<Object> inserimentoMovimentazione(
            @RequestBody MovimentazioniRequest request,
            Authentication authentication) {
        String cusr = authentication.getName();
        logger.info("Richiesta inserimento movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> struttureRicettive = struttureRicettiveRepository.getStrutturaFromCir(cusr);

        if (struttureRicettive.isEmpty()) {
            logger.warning("Struttura non trovata per cusr: " + cusr);
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Nessuna struttura trovata con cusr: " + cusr));
        } else if (struttureRicettive.get().getDatafineattivita() != null) {
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Struttura cessata con cusr: " + cusr));
        }

        Optional<String> ultimoMeseValidato = modc59Repository.getUltimoMeseValidato();

        for (MovimentazioniRequestItem g : request.getGiornate()) {
            logger.info("[START] Elaborazione giornata: " + g.getDataRilevazione());
            int totArrivi = 0;
            int totPartenze = 0;
            int totPresenti = 0;
            int totPresentiNottePrecedente = 0;
            int totCamereOccupate = g.getCamereOccupate();
            List<C59Italiani> italiani = new ArrayList<>();
            List<C59Stranieri> stranieri = new ArrayList<>();
            LocalDate dataRilevazione = LocalDate.parse(g.getDataRilevazione(), java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));
            if (ultimoMeseValidato.isPresent() && Integer.parseInt(ultimoMeseValidato.get().split("-")[0]) >= dataRilevazione.getMonthValue()) {
                logger.warning("Non è possibile inserire movimentazioni per un mese già validato: " + g.getDataRilevazione());
                return ResponseEntity.badRequest().body(new MovimentazioniResponse("Non è possibile inserire movimentazioni per un mese già validato: " + g.getDataRilevazione()));
            }
            if (dataRilevazione.isAfter(LocalDate.now())) {
                logger.warning("Non è possibile inserire movimentazioni per una data futura: " + g.getDataRilevazione());
                return ResponseEntity.badRequest().body(new MovimentazioniResponse("Non è possibile inserire movimentazioni per una data futura: " + g.getDataRilevazione()));
            }
            c59ItalianiRepository.deleteByStrutturaAndDate(struttureRicettive.get(), dataRilevazione.getYear(), dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
            c59StranieriRepository.deleteByStrutturaAndDate(struttureRicettive.get(), dataRilevazione.getYear(), dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
            for (MovimentazioneRequestItemMovimentazione movimentazione : g.getMovimentazioni()) {
                if (movimentazione.getCodiceProvincia() != null) {
                    Optional<Province> provincia = provinceRepository.findFirstByCodiceistatIgnoreCaseAllIgnoreCase(
                            movimentazione.getCodiceProvincia());
                    if (provincia.isEmpty()) {
                        logger.warning("Provincia non trovata: " + movimentazione.getCodiceProvincia());
                        return ResponseEntity.badRequest().body(new MovimentazioniResponse("Provincia non trovata: " + movimentazione.getCodiceProvincia()));
                    }
                    C59Italiani i = new C59Italiani();
                    i.setArrivati(movimentazione.getArrivi());
                    i.setPartiti(movimentazione.getPartenze());
                    i.setPresenti(movimentazione.getPresentiNottePrecedente());
                    i.setStrutturaid(struttureRicettive.get());
                    i.setProvinciaid(provincia.get());
                    i.setAnno(dataRilevazione.getYear());
                    i.setMese(dataRilevazione.getMonthValue());
                    i.setGiorno(dataRilevazione.getDayOfMonth());
                    italiani.add(i);
                    totArrivi += movimentazione.getArrivi();
                    totPartenze += movimentazione.getPartenze();
                    totPresenti += (movimentazione.getPresentiNottePrecedente() + movimentazione.getArrivi() - movimentazione.getPartenze());
                    totPresentiNottePrecedente += movimentazione.getPresentiNottePrecedente();
                } else if (movimentazione.getCodiceNazione() != null) {
                    Optional<Nazioni> nazione = nazioniRepository.findFirtsByCodiceEpt(Integer.parseInt(movimentazione.getCodiceNazione()));
                    if (nazione.isEmpty()) {
                        logger.warning("Nazione non trovata: " + movimentazione.getCodiceNazione());
                        return ResponseEntity.badRequest().body(new MovimentazioniResponse("Nazione non trovata: " + movimentazione.getCodiceNazione()));
                    }
                    C59Stranieri s = new C59Stranieri();
                    s.setArrivati(movimentazione.getArrivi());
                    s.setPartiti(movimentazione.getPartenze());
                    s.setPresenti(movimentazione.getPresentiNottePrecedente());
                    s.setStrutturaid(struttureRicettive.get());
                    s.setNazioneid(nazione.get());
                    s.setAnno(dataRilevazione.getYear());
                    s.setMese(dataRilevazione.getMonthValue());
                    s.setGiorno(dataRilevazione.getDayOfMonth());
                    stranieri.add(s);
                    totArrivi += movimentazione.getArrivi();
                    totPartenze += movimentazione.getPartenze();
                    totPresenti += (movimentazione.getPresentiNottePrecedente() + movimentazione.getArrivi() - movimentazione.getPartenze());
                    totPresentiNottePrecedente += movimentazione.getPresentiNottePrecedente();
                }
                List<Modc59> modc59DaEliminare = modc59Repository.findModC59ForAllDate(struttureRicettive.get(), dataRilevazione.getMonthValue(), dataRilevazione.getYear(), dataRilevazione.getDayOfMonth());
                modc59Repository.deleteAll(modc59DaEliminare);

                Modc59 modc59 = new Modc59();
                modc59.setAnno(dataRilevazione.getYear());
                modc59.setMese(dataRilevazione.getMonthValue());
                modc59.setGiorno(dataRilevazione.getDayOfMonth());
                modc59.setStrutturaid(struttureRicettive.get());
                modc59.setCamerelibere(totCamereOccupate); //il campo camerelibere sulla tabella viene usato da sempre per tracciare le camere occupate
                modc59.setTotarrivati(totArrivi);
                modc59.setTotpartiti(totPartenze);
                modc59.setTotpresenti(totPresenti);
                modc59.setPresentinotte(totPresentiNottePrecedente);

                c59ItalianiRepository.saveAll(italiani);
                c59StranieriRepository.saveAll(stranieri);
                modc59Repository.save(modc59);
            }
            logger.info("[END] Elaborazione giornata: " + g.getDataRilevazione());
        }
        return ResponseEntity.status(201).build();
    }

    /**
     * Modifica le movimentazioni per la struttura dell'utente autenticato.
     *
     * @param request        L'oggetto MovimentazioniRequest contenente le
     *                       movimentazioni da modificare.
     * @param authentication L'oggetto Authentication contenente le informazioni
     *                       sull'utente autenticato.
     * @return Un oggetto ResponseEntity con lo stato della richiesta.
     */
    @PutMapping()
    @Transactional
    public ResponseEntity<Object> modificaMovimentazione(
            @RequestBody MovimentazioniRequest request,
            Authentication authentication) {
        String cusr = authentication.getName();
        logger.info("Richiesta inserimento movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> struttureRicettive = struttureRicettiveRepository.getStrutturaFromCir(cusr);

        if (struttureRicettive.isEmpty()) {
            logger.warning("Struttura non trovata per cusr: " + cusr);
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Nessuna struttura trovata con cusr: " + cusr));
        } else if (struttureRicettive.get().getDatafineattivita() != null) {
            return ResponseEntity.badRequest().body(new MovimentazioniResponse("Struttura cessata con cusr: " + cusr));
        }

        Optional<String> ultimoMeseValidato = modc59Repository.getUltimoMeseValidato();

        for (MovimentazioniRequestItem g : request.getGiornate()) {
            logger.info("[START] Elaborazione giornata: " + g.getDataRilevazione());
            int totArrivi = 0;
            int totPartenze = 0;
            int totPresenti = 0;
            int totPresentiNottePrecedente = 0;
            int totCamereOccupate = g.getCamereOccupate();
            List<C59Italiani> italiani = new ArrayList<>();
            List<C59Stranieri> stranieri = new ArrayList<>();
            LocalDate dataRilevazione = LocalDate.parse(g.getDataRilevazione(), java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));
            if (ultimoMeseValidato.isPresent() && Integer.parseInt(ultimoMeseValidato.get().split("-")[0]) >= dataRilevazione.getMonthValue()) {
                logger.warning("Non è possibile inserire movimentazioni per un mese già validato: " + g.getDataRilevazione());
                return ResponseEntity.badRequest().body(new MovimentazioniResponse("Non è possibile inserire movimentazioni per un mese già validato: " + g.getDataRilevazione()));
            }
            if (dataRilevazione.isAfter(LocalDate.now())) {
                logger.warning("Non è possibile inserire movimentazioni per una data futura: " + g.getDataRilevazione());
                return ResponseEntity.badRequest().body(new MovimentazioniResponse("Non è possibile inserire movimentazioni per una data futura: " + g.getDataRilevazione()));
            }
            c59ItalianiRepository.deleteByStrutturaAndDate(struttureRicettive.get(), dataRilevazione.getYear(), dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
            c59StranieriRepository.deleteByStrutturaAndDate(struttureRicettive.get(), dataRilevazione.getYear(), dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
            for (MovimentazioneRequestItemMovimentazione movimentazione : g.getMovimentazioni()) {
                if (movimentazione.getCodiceProvincia() != null) {
                    Optional<Province> provincia = provinceRepository.findFirstByCodiceistatIgnoreCaseAllIgnoreCase(
                            movimentazione.getCodiceProvincia());
                    if (provincia.isEmpty()) {
                        logger.warning("Provincia non trovata: " + movimentazione.getCodiceProvincia());
                        return ResponseEntity.badRequest().body(new MovimentazioniResponse("Provincia non trovata: " + movimentazione.getCodiceProvincia()));
                    }
                    C59Italiani i = new C59Italiani();
                    i.setArrivati(movimentazione.getArrivi());
                    i.setPartiti(movimentazione.getPartenze());
                    i.setPresenti(movimentazione.getPresentiNottePrecedente());
                    i.setStrutturaid(struttureRicettive.get());
                    i.setProvinciaid(provincia.get());
                    i.setAnno(dataRilevazione.getYear());
                    i.setMese(dataRilevazione.getMonthValue());
                    i.setGiorno(dataRilevazione.getDayOfMonth());
                    italiani.add(i);
                    totArrivi += movimentazione.getArrivi();
                    totPartenze += movimentazione.getPartenze();
                    totPresenti += (movimentazione.getPresentiNottePrecedente() + movimentazione.getArrivi() - movimentazione.getPartenze());
                    totPresentiNottePrecedente += movimentazione.getPresentiNottePrecedente();
                } else if (movimentazione.getCodiceNazione() != null) {
                    Optional<Nazioni> nazione = nazioniRepository.findFirtsByCodiceEpt(Integer.parseInt(movimentazione.getCodiceNazione()));
                    if (nazione.isEmpty()) {
                        logger.warning("Nazione non trovata: " + movimentazione.getCodiceNazione());
                        return ResponseEntity.badRequest().body(new MovimentazioniResponse("Nazione non trovata: " + movimentazione.getCodiceNazione()));
                    }
                    C59Stranieri s = new C59Stranieri();
                    s.setArrivati(movimentazione.getArrivi());
                    s.setPartiti(movimentazione.getPartenze());
                    s.setPresenti(movimentazione.getPresentiNottePrecedente());
                    s.setStrutturaid(struttureRicettive.get());
                    s.setNazioneid(nazione.get());
                    s.setAnno(dataRilevazione.getYear());
                    s.setMese(dataRilevazione.getMonthValue());
                    s.setGiorno(dataRilevazione.getDayOfMonth());
                    stranieri.add(s);
                    totArrivi += movimentazione.getArrivi();
                    totPartenze += movimentazione.getPartenze();
                    totPresenti += (movimentazione.getPresentiNottePrecedente() + movimentazione.getArrivi() - movimentazione.getPartenze());
                    totPresentiNottePrecedente += movimentazione.getPresentiNottePrecedente();
                }
                List<Modc59> modc59DaEliminare = modc59Repository.findModC59ForAllDate(struttureRicettive.get(), dataRilevazione.getMonthValue(), dataRilevazione.getYear(), dataRilevazione.getDayOfMonth());
                modc59Repository.deleteAll(modc59DaEliminare);

                Modc59 modc59 = new Modc59();
                modc59.setAnno(dataRilevazione.getYear());
                modc59.setMese(dataRilevazione.getMonthValue());
                modc59.setGiorno(dataRilevazione.getDayOfMonth());
                modc59.setStrutturaid(struttureRicettive.get());
                modc59.setCamerelibere(totCamereOccupate); //il campo camerelibere sulla tabella viene usato da sempre per tracciare le camere occupate
                modc59.setTotarrivati(totArrivi);
                modc59.setTotpartiti(totPartenze);
                modc59.setTotpresenti(totPresenti);
                modc59.setPresentinotte(totPresentiNottePrecedente);

                c59ItalianiRepository.saveAll(italiani);
                c59StranieriRepository.saveAll(stranieri);
                modc59Repository.save(modc59);
            }
            logger.info("[END] Elaborazione giornata: " + g.getDataRilevazione());
        }
        return ResponseEntity.status(201).build();
    }

    private void fillItaliani(List<C59Italiani> italiani, MovimentazioniResponseItem item) {
        for (C59Italiani i : italiani) {
            item.getMovimentazioni().add(new MovimentazioniResponseItemMovimentazione(
                    null,
                    i.getProvinciaid().getCodiceistat(),
                    i.getArrivati(),
                    i.getPresenti(),
                    i.getPartiti()));
            if (i.getCamere() != null) {
                item.setCamereOccupate(item.getCamereOccupate() + i.getCamere());
            }
        }
    }

    private void fillStranieri(List<C59Stranieri> stranieri, MovimentazioniResponseItem item) {
        for (C59Stranieri s : stranieri) {
            item.getMovimentazioni().add(new MovimentazioniResponseItemMovimentazione(
                    String.format("%03d", s.getNazioneid().getCodiceEpt()),
                    null,
                    s.getArrivati(),
                    s.getPresenti(),
                    s.getPartiti()));
            if (s.getCamere() != null) {
                item.setCamereOccupate(item.getCamereOccupate() + s.getCamere());
            }
        }
    }
}