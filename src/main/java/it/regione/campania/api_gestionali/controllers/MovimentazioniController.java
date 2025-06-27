package it.regione.campania.api_gestionali.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import it.regione.campania.api_gestionali.responses.UlimaRilevazioneResponse;
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
     * Ottiene l'archivio delle movimentazioni per una struttura ricettiva.
     *
     * @param dataInizio     La data di inizio per l'archivio (formato: ddMMyyyy).
     * @param offset         Il numero di giorni da includere nell'archivio.
     * @param authentication L'oggetto di autenticazione contenente il nome utente.
     * @return Una risposta contenente l'archivio delle movimentazioni.
     */
    @GetMapping()
    public ResponseEntity<Object> getArchivioMovimentazioni(
            @RequestParam(name = "dataInizio", required = true) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate dataInizio,
            @RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
            Authentication authentication) {

        String cusr = authentication.getName();
        logger.info("Richiesta archivio movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOpt = struttureRicettiveRepository.getStrutturaFromCir(cusr);
        if (strutturaOpt.isEmpty()) {
            return badRequest("Struttura non trovata");
        }

        StruttureRicettive struttura = strutturaOpt.get();
        if (struttura.getDatafineattivita() != null) {
            return badRequest("Struttura cessata");
        }

        MovimentazioniResponse response = new MovimentazioniResponse();
        response.setGiornate(new ArrayList<>());

        for (int i = 0; i < offset; i++) {
            MovimentazioniResponseItem item = createMovimentazioniItem(struttura, dataInizio);
            response.getGiornate().add(item);
            dataInizio = dataInizio.plusDays(1);
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Inserisce nuove movimentazioni per una struttura ricettiva.
     *
     * @param request        L'oggetto contenente i dati delle movimentazioni da
     *                       inserire.
     * @param authentication L'oggetto di autenticazione contenente il nome utente.
     * @return Una risposta HTTP con lo stato dell'operazione.
     */
    @PostMapping()
    @Transactional
    public ResponseEntity<Object> inserimentoMovimentazione(
            @RequestBody MovimentazioniRequest request,
            Authentication authentication) {

        String cusr = authentication.getName();
        logger.info("Richiesta inserimento movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOpt = struttureRicettiveRepository.getStrutturaFromCir(cusr);
        if (strutturaOpt.isEmpty()) {
            return badRequest("Nessuna struttura trovata con cusr: " + cusr);
        }

        StruttureRicettive struttura = strutturaOpt.get();
        if (struttura.getDatafineattivita() != null) {
            return badRequest("Struttura cessata con cusr: " + cusr);
        }

        Optional<String> ultimoMeseValidato = modc59Repository.getUltimoMeseValidato();
        int idx = 0;
        for (MovimentazioniRequestItem giornata : request.getGiornate()) {
            ResponseEntity<Object> dateValidationResponse = validateDateSequence(giornata, request, idx);
            if (dateValidationResponse != null) {
                return dateValidationResponse;
            }
            ResponseEntity<Object> validationResponse = validateGiornata(struttura, giornata, ultimoMeseValidato, idx > 0);
            if (validationResponse != null) {
                return validationResponse;
            } if (checkIfExists(giornata, struttura)) {
                return badRequest("Movimentazioni già esistenti per la data: " + giornata.getDataRilevazione());
            }
            idx++;
        }

        for (MovimentazioniRequestItem giornata : request.getGiornate()) {
            processGiornata(giornata, struttura);
        }

        return ResponseEntity.status(201).build();
    }

    /**
     * Modifica le movimentazioni esistenti per una struttura ricettiva.
     *
     * @param request        L'oggetto contenente i dati delle movimentazioni da
     *                       modificare.
     * @param authentication L'oggetto di autenticazione contenente il nome utente.
     * @return Una risposta HTTP con lo stato dell'operazione.
     */
    @PutMapping()
    @Transactional
    public ResponseEntity<Object> modificaMovimentazione(
            @RequestBody MovimentazioniRequest request,
            Authentication authentication) {

        String cusr = authentication.getName();
        logger.info("Richiesta inserimento movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOpt = struttureRicettiveRepository.getStrutturaFromCir(cusr);
        if (strutturaOpt.isEmpty()) {
            return badRequest("Nessuna struttura trovata con cusr: " + cusr);
        }

        StruttureRicettive struttura = strutturaOpt.get();
        if (struttura.getDatafineattivita() != null) {
            return badRequest("Struttura cessata con cusr: " + cusr);
        }

        Optional<String> ultimoMeseValidato = modc59Repository.getUltimoMeseValidato();
        int idx = 0;
        for (MovimentazioniRequestItem giornata : request.getGiornate()) {
            ResponseEntity<Object> dateValidationResponse = validateDateSequence(giornata, request, idx);
            if (dateValidationResponse != null) {
                return dateValidationResponse;
            }
            ResponseEntity<Object> validationResponse = validateGiornata(struttura, giornata, ultimoMeseValidato, idx > 0);
            if (validationResponse != null) {
                return validationResponse;
            }

            processGiornata(giornata, struttura);
            idx++;
        }

        return ResponseEntity.status(200).build();
    }

    /**
     * Elimina le movimentazioni per una struttura ricettiva.
     *
     * @param dataInizio     La data di inizio per l'eliminazione (formato:
     *                       ddMMyyyy).
     * @param offset         Il numero di giorni da eliminare.
     * @param authentication L'oggetto di autenticazione contenente il nome utente.
     * @return Una risposta HTTP con lo stato dell'operazione.
     */
    @DeleteMapping()
    @Transactional
    public ResponseEntity<Object> eliminaMovimentazioni(
            @RequestParam(name = "dataInizio", required = true) @DateTimeFormat(pattern = "ddMMyyyy") LocalDate dataInizio,
            @RequestParam(name = "offset", required = false, defaultValue = "1") Integer offset,
            Authentication authentication) {

        String cusr = authentication.getName();
        logger.info("Eliminazione movimentazioni per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOpt = struttureRicettiveRepository.getStrutturaFromCir(cusr);
        if (strutturaOpt.isEmpty()) {
            return badRequest("Struttura non trovata");
        }

        StruttureRicettive struttura = strutturaOpt.get();
        if (struttura.getDatafineattivita() != null) {
            return badRequest("Struttura cessata");
        }

        Optional<String> ultimoMeseValidato = modc59Repository.getUltimoMeseValidato();

        for (int i = 0; i < offset; i++) {
            ResponseEntity<Object> validationResponse = validateGiornata(dataInizio, ultimoMeseValidato);
            if (validationResponse != null) {
                return validationResponse;
            }

            deleteMovimentazioni(struttura, dataInizio);
            dataInizio = dataInizio.plusDays(1);
        }

        return ResponseEntity.status(204).build();
    }

    private Void deleteMovimentazioni(StruttureRicettive struttura, LocalDate data) {
        c59ItalianiRepository.deleteByStrutturaAndDate(struttura, data.getYear(), data.getMonthValue(),
                data.getDayOfMonth());
        c59StranieriRepository.deleteByStrutturaAndDate(struttura, data.getYear(), data.getMonthValue(),
                data.getDayOfMonth());
        List<Modc59> modc59DaEliminare = modc59Repository.findModC59ForAllDate(struttura, data.getMonthValue(),
                data.getYear(), data.getDayOfMonth());
        modc59Repository.deleteAll(modc59DaEliminare);
        return null;
    }

    private MovimentazioniResponseItem createMovimentazioniItem(StruttureRicettive struttura, LocalDate data) {
        MovimentazioniResponseItem item = new MovimentazioniResponseItem();
        item.setDataRilevazione(
                String.format("%02d%02d%04d", data.getDayOfMonth(), data.getMonthValue(), data.getYear()));
        item.setMovimentazioni(new ArrayList<>());

        List<Modc59> modc59 = modc59Repository.findModC59ForAllDate(struttura, data.getMonthValue(),
                data.getYear(), data.getDayOfMonth());
        if (modc59.isEmpty()) {
            item.setCamereOccupate(0);
            item.setStrutturaChiusa(false);
        } else {
            Modc59 lastModC59 = modc59.getLast();
            item.setCamereOccupate(lastModC59.getCamerelibere());
            if (lastModC59.getFilename() != null && lastModC59.getFilename().equals("STRUTTUA_CHIUSA")) {
                item.setStrutturaChiusa(true);
            } else {
                item.setStrutturaChiusa(false);
            }
        }

        List<C59Italiani> italiani = c59ItalianiRepository.findAllByStrutturaAndDate(
                struttura.getIdstrutturaricettiva(), data.getYear(), data.getMonthValue(), data.getDayOfMonth());
        fillItaliani(italiani, item);

        List<C59Stranieri> stranieri = c59StranieriRepository.findAllByStrutturaAndDate(
                struttura.getIdstrutturaricettiva(), data.getYear(), data.getMonthValue(), data.getDayOfMonth());
        fillStranieri(stranieri, item);

        return item;
    }

    private ResponseEntity<Object> validateDateSequence(MovimentazioniRequestItem giornata, MovimentazioniRequest request, int idx) {
        LocalDate dataRilevazione = LocalDate.parse(giornata.getDataRilevazione(),
                java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));
        if (idx > 0 && dataRilevazione.minusDays(1).isAfter(LocalDate.parse(request.getGiornate().get(idx - 1).getDataRilevazione(),
                java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy")))) {
            logger.warning("Non è possibile saltare date nell'invio della movimentazione");
            return badRequest("Non è possibile saltare date nell'invio della movimentazione");
        }
        return null;
    }

    private ResponseEntity<Object> validateGiornata(StruttureRicettive struttura, MovimentazioniRequestItem giornata,
            Optional<String> ultimoMeseValidato, boolean isMassive) {
        if (giornata.getDataRilevazione() == null || giornata.getDataRilevazione().isEmpty()) {
            logger.warning("Data di rilevazione non fornita");
            return badRequest("Il campo dataRilevazione è obbligatorio");
        }
        LocalDate dataRilevazione = LocalDate.parse(giornata.getDataRilevazione(),
                java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));

        Integer ultimoMeseValidatoInteger = Integer.parseInt(ultimoMeseValidato.get().split("-")[0]);

        if (ultimoMeseValidato.isPresent()
                && ultimoMeseValidatoInteger >= dataRilevazione.getMonthValue()) {
            logger.warning("Non è possibile inserire, modificare o eliminare movimentazioni per un mese già validato: "
                    + giornata.getDataRilevazione());
            return badRequest(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per un mese già validato: "
                            + giornata.getDataRilevazione());
        }

        LocalDate dataRilevazionePrecedente = dataRilevazione.minusDays(1);
        if (!isMassive && dataRilevazionePrecedente.getMonthValue() > ultimoMeseValidatoInteger) {
            List<Modc59> modc59 = modc59Repository.findModC59ForAllDate(
                    struttura, dataRilevazionePrecedente.getMonthValue(), dataRilevazionePrecedente.getYear(),
                    dataRilevazionePrecedente.getDayOfMonth());
            if (modc59.isEmpty()) {
                logger.warning("Non è possibile saltare date nell'invio della movimentazione");
                return badRequest("Non è possibile saltare date nell'invio della movimentazione");
            }
        }

        if (dataRilevazione.isAfter(LocalDate.now())) {
            logger.warning(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per una data futura: "
                            + giornata.getDataRilevazione());
            return badRequest(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per una data futura: "
                            + giornata.getDataRilevazione());
        }

        return null;
    }

    private ResponseEntity<Object> validateGiornata(LocalDate dataRilevazione,
            Optional<String> ultimoMeseValidato) {

        if (ultimoMeseValidato.isPresent()
                && Integer.parseInt(ultimoMeseValidato.get().split("-")[0]) >= dataRilevazione.getMonthValue()) {
            logger.warning("Non è possibile inserire, modificare o eliminare movimentazioni per un mese già validato: "
                    + dataRilevazione);
            return badRequest(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per un mese già validato: "
                            + dataRilevazione);
        }

        if (dataRilevazione.isAfter(LocalDate.now())) {
            logger.warning(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per una data futura: "
                            + dataRilevazione);
            return badRequest(
                    "Non è possibile inserire, modificare o eliminare movimentazioni per una data futura: "
                            + dataRilevazione);
        }

        return null;
    }

    private boolean checkIfExists(MovimentazioniRequestItem giornata, StruttureRicettive struttura) {
        LocalDate dataRilevazione = LocalDate.parse(giornata.getDataRilevazione(),
                java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));
        List<Modc59> modc59DaEliminare = modc59Repository.findModC59ForAllDate(struttura,
                dataRilevazione.getMonthValue(), dataRilevazione.getYear(), dataRilevazione.getDayOfMonth());
        return !modc59DaEliminare.isEmpty();
    }

    private void processGiornata(MovimentazioniRequestItem giornata, StruttureRicettive struttura) {
        LocalDate dataRilevazione = LocalDate.parse(giornata.getDataRilevazione(),
                java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"));

        c59ItalianiRepository.deleteByStrutturaAndDate(struttura, dataRilevazione.getYear(),
                dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
        c59StranieriRepository.deleteByStrutturaAndDate(struttura, dataRilevazione.getYear(),
                dataRilevazione.getMonthValue(), dataRilevazione.getDayOfMonth());
        List<Modc59> modc59DaEliminare = modc59Repository.findModC59ForAllDate(struttura,
                dataRilevazione.getMonthValue(), dataRilevazione.getYear(), dataRilevazione.getDayOfMonth());
        modc59Repository.deleteAll(modc59DaEliminare);

        List<C59Italiani> italiani = new ArrayList<>();
        List<C59Stranieri> stranieri = new ArrayList<>();
        int totArrivi = 0, totPartenze = 0, totPresenti = 0, totPresentiNottePrecedente = 0;

        for (MovimentazioneRequestItemMovimentazione movimentazione : giornata.getMovimentazioni()) {
            if (movimentazione.getCodiceProvincia() != null) {
                totArrivi += processItaliani(movimentazione, struttura, dataRilevazione, italiani);
            } else if (movimentazione.getCodiceNazione() != null) {
                totArrivi += processStranieri(movimentazione, struttura, dataRilevazione, stranieri);
            }
        }

        saveMovimentazioni(struttura, dataRilevazione, giornata.getCamereOccupate(), totArrivi, totPartenze,
                totPresenti, totPresentiNottePrecedente, italiani, stranieri, giornata.isStrutturaChiusa());
    }

    private int processItaliani(MovimentazioneRequestItemMovimentazione movimentazione, StruttureRicettive struttura,
            LocalDate dataRilevazione, List<C59Italiani> italiani) {
        Optional<Province> provincia = provinceRepository
                .findFirstByCodiceistatIgnoreCaseAllIgnoreCase(movimentazione.getCodiceProvincia());
        if (provincia.isEmpty()) {
            throw new IllegalArgumentException("Provincia non trovata: " + movimentazione.getCodiceProvincia());
        }

        C59Italiani i = new C59Italiani();
        i.setArrivati(movimentazione.getArrivi());
        i.setPartiti(movimentazione.getPartenze());
        i.setPresenti(movimentazione.getPresentiNottePrecedente());
        i.setStrutturaid(struttura);
        i.setProvinciaid(provincia.get());
        i.setAnno(dataRilevazione.getYear());
        i.setMese(dataRilevazione.getMonthValue());
        i.setGiorno(dataRilevazione.getDayOfMonth());
        italiani.add(i);

        return movimentazione.getArrivi();
    }

    private int processStranieri(MovimentazioneRequestItemMovimentazione movimentazione, StruttureRicettive struttura,
            LocalDate dataRilevazione, List<C59Stranieri> stranieri) {
        Optional<Nazioni> nazione = nazioniRepository
                .findFirtsByCodiceEpt(Integer.parseInt(movimentazione.getCodiceNazione()));
        if (nazione.isEmpty()) {
            throw new IllegalArgumentException("Nazione non trovata: " + movimentazione.getCodiceNazione());
        }

        C59Stranieri s = new C59Stranieri();
        s.setArrivati(movimentazione.getArrivi());
        s.setPartiti(movimentazione.getPartenze());
        s.setPresenti(movimentazione.getPresentiNottePrecedente());
        s.setStrutturaid(struttura);
        s.setNazioneid(nazione.get());
        s.setAnno(dataRilevazione.getYear());
        s.setMese(dataRilevazione.getMonthValue());
        s.setGiorno(dataRilevazione.getDayOfMonth());
        stranieri.add(s);

        return movimentazione.getArrivi();
    }

    private void saveMovimentazioni(StruttureRicettive struttura, LocalDate dataRilevazione, int camereOccupate,
            int totArrivi, int totPartenze, int totPresenti, int totPresentiNottePrecedente, List<C59Italiani> italiani,
            List<C59Stranieri> stranieri, boolean strutturaChiusa) {
        Modc59 modc59 = new Modc59();
        modc59.setAnno(dataRilevazione.getYear());
        modc59.setMese(dataRilevazione.getMonthValue());
        modc59.setGiorno(dataRilevazione.getDayOfMonth());
        modc59.setStrutturaid(struttura);
        modc59.setCamerelibere(camereOccupate);
        modc59.setTotarrivati(totArrivi);
        modc59.setTotpartiti(totPartenze);
        modc59.setTotpresenti(totPresenti);
        modc59.setPresentinotte(totPresentiNottePrecedente);
        if (strutturaChiusa) {
            modc59.setFilename("STRUTTUA_CHIUSA");
        }

        c59ItalianiRepository.saveAll(italiani);
        c59StranieriRepository.saveAll(stranieri);
        modc59Repository.save(modc59);
    }

    private ResponseEntity<Object> badRequest(String message) {
        logger.warning(message);
        return ResponseEntity.badRequest().body(new MovimentazioniResponse(message));
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

    @GetMapping("/ultima-rilevazione")
    public ResponseEntity<Object> getUltimaRilevazione(Authentication authentication) {
        String cusr = authentication.getName();
        logger.info("Richiesta ultima rilevazione per cusr: " + cusr);

        Optional<StruttureRicettive> strutturaOpt = struttureRicettiveRepository.getStrutturaFromCir(cusr);
        if (strutturaOpt.isEmpty()) {
            return badRequest("Nessuna struttura trovata con cusr: " + cusr);
        }

        StruttureRicettive struttura = strutturaOpt.get();
        if (struttura.getDatafineattivita() != null) {
            return badRequest("Struttura cessata con cusr: " + cusr);
        }

        Optional<LocalDate> ultimaRilevazione = modc59Repository.findMaxC59ByStruttura(struttura);
        if (ultimaRilevazione.isEmpty()) {
            logger.warning("Nessuna rilevazione trovata per la struttura: " + struttura.getIdstrutturaricettiva());
            return ResponseEntity.ok(new UlimaRilevazioneResponse());
        } else {
            return ResponseEntity.ok(
                    new UlimaRilevazioneResponse(
                            ultimaRilevazione.get().format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy"))));
        }
    }
}