package it.regione.campania.api_gestionali.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.regione.campania.api_gestionali.models.Nazioni;
import it.regione.campania.api_gestionali.models.Province;
import it.regione.campania.api_gestionali.repositories.NazioniRepository;
import it.regione.campania.api_gestionali.repositories.ProvinceRepository;
import it.regione.campania.api_gestionali.responses.CodiciIstatResponse;

@RestController
@RequestMapping("/v1/codici-istat")
public class CodiciIstatController {
        @Autowired
    private ProvinceRepository provinceRepository;

    @Autowired
    private NazioniRepository nazioniRepository;

    @GetMapping("")
    public CodiciIstatResponse getCodiciIstat() {
        List<Province> province = provinceRepository.findAll();
        List<Nazioni> nazioni = nazioniRepository.findAll();

        CodiciIstatResponse response = new CodiciIstatResponse();
        response.setCodiciIstatProvince(new ArrayList<>());
        response.setCodiciIstatNazioni(new ArrayList<>());

        for (Province p : province) {
            response.getCodiciIstatProvince()
                    .add(new it.regione.campania.api_gestionali.responses.CodiciIstatResponseItem(p.getCodiceistat(), p.getDescrizione()
                            ));
        }

        for (Nazioni n : nazioni) {
            response.getCodiciIstatNazioni()
                    .add(new it.regione.campania.api_gestionali.responses.CodiciIstatResponseItem(String.format("%03d", n.getCodiceEpt()), n.getDescrizione()
                            ));
        }

        return response;
    }
}
