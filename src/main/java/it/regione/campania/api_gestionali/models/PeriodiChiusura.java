package it.regione.campania.api_gestionali.models;

import java.sql.Date;
import java.sql.Timestamp;

import org.hibernate.annotations.ColumnDefault;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "periodichiusurastruttura")
public class PeriodiChiusura {
    
    @Id
    @Column(name = "idperiodichiusura")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('periodichiusurastruttura_idperiodichiusura_seq'::regclass)")
    private Integer idPeriodiChiusura;

    @ManyToOne
    @JoinColumn(name = "strutturaid")
    private StruttureRicettive strutturaId;

    @Column(name = "datainizioperiodochiusura")
    private Date dataInizioPeriodiChiusura;

    @Column(name = "datafineperiodochiusura")
    private Date dataFinePeriodiChiusura;

    @Column(name = "datainizioperiodochiusurastagionale")
    private Date dataInizioPeriodiChiusuraStagionale;

    @Column(name = "datafineperiodochiusurastagionale")
    private Date dataFinePeriodiChiusuraStagionale;

    @Column(name = "motivazionechiusura", columnDefinition = "TEXT")
    private String motivazioneChiusura;

    @Column(name = "annoriferimento", columnDefinition = "CHAR(4)")
    private String annoRiferimento;

    @Column(name = "periodoeliminato")
    private Boolean periodoEliminato;

    @Column(name = "datainserimento")
    private Timestamp dataInserimento;

    @Column(name = "strutturaapertatuttolanno")
    private Boolean strutturaApertaTuttoLAnno;

    // Constructors
    public PeriodiChiusura() {}

    // Getters and Setters
    public Integer getIdPeriodiChiusura() {
        return idPeriodiChiusura;
    }

    public void setIdPeriodiChiusura(Integer idPeriodiChiusura) {
        this.idPeriodiChiusura = idPeriodiChiusura;
    }

    public StruttureRicettive getStrutturaId() {
        return strutturaId;
    }

    public void setStrutturaId(StruttureRicettive strutturaId) {
        this.strutturaId = strutturaId;
    }

    public Date getDataInizioPeriodiChiusura() {
        return dataInizioPeriodiChiusura;
    }

    public void setDataInizioPeriodiChiusura(Date dataInizioPeriodiChiusura) {
        this.dataInizioPeriodiChiusura = dataInizioPeriodiChiusura;
    }

    public Date getDataFinePeriodiChiusura() {
        return dataFinePeriodiChiusura;
    }

    public void setDataFinePeriodiChiusura(Date dataFinePeriodiChiusura) {
        this.dataFinePeriodiChiusura = dataFinePeriodiChiusura;
    }

    public Date getDataInizioPeriodiChiusuraStagionale() {
        return dataInizioPeriodiChiusuraStagionale;
    }

    public void setDataInizioPeriodiChiusuraStagionale(Date dataInizioPeriodiChiusuraStagionale) {
        this.dataInizioPeriodiChiusuraStagionale = dataInizioPeriodiChiusuraStagionale;
    }

    public Date getDataFinePeriodiChiusuraStagionale() {
        return dataFinePeriodiChiusuraStagionale;
    }

    public void setDataFinePeriodiChiusuraStagionale(Date dataFinePeriodiChiusuraStagionale) {
        this.dataFinePeriodiChiusuraStagionale = dataFinePeriodiChiusuraStagionale;
    }

    public String getAnnoRiferimento() {
        return annoRiferimento;
    }

    public void setAnnoRiferimento(String annoRiferimento) {
        this.annoRiferimento = annoRiferimento;
    }

    public String getMotivazioneChiusura() {
        return motivazioneChiusura;
    }

    public void setMotivazioneChiusura(String motivazioneChiusura) {
        this.motivazioneChiusura = motivazioneChiusura;
    }

    public Boolean getPeriodoEliminato() {
        return periodoEliminato;
    }

    public void setPeriodoEliminato(Boolean periodoEliminato) {
        this.periodoEliminato = periodoEliminato;
    }

    public Timestamp getDataInserimento() {
        return dataInserimento;
    }

    public void setDataInserimento(Timestamp dataInserimento) {
        this.dataInserimento = dataInserimento;
    }

    public Boolean getStrutturaApertaTuttoLAnno() {
        return strutturaApertaTuttoLAnno;
    }

    public void setStrutturaApertaTuttoLAnno(Boolean strutturaApertaTuttoLAnno) {
        this.strutturaApertaTuttoLAnno = strutturaApertaTuttoLAnno;
    }

    // Builder pattern
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Integer idPeriodiChiusura;
        private StruttureRicettive strutturaId;
        private Date dataInizioPeriodiChiusura;
        private Date dataFinePeriodiChiusura;
        private Date dataInizioPeriodiChiusuraStagionale;
        private Date dataFinePeriodiChiusuraStagionale;
        private String motivazioneChiusura;
        private String annoRiferimento;
        private Boolean periodoEliminato;
        private Timestamp dataInserimento;
        private Boolean strutturaApertaTuttoLAnno;

        public Builder idPeriodiChiusura(Integer idPeriodiChiusura) {
            this.idPeriodiChiusura = idPeriodiChiusura;
            return this;
        }

        public Builder strutturaId(StruttureRicettive strutturaId) {
            this.strutturaId = strutturaId;
            return this;
        }

        public Builder dataInizioPeriodiChiusura(Date dataInizioPeriodiChiusura) {
            this.dataInizioPeriodiChiusura = dataInizioPeriodiChiusura;
            return this;
        }

        public Builder dataFinePeriodiChiusura(Date dataFinePeriodiChiusura) {
            this.dataFinePeriodiChiusura = dataFinePeriodiChiusura;
            return this;
        }

        public Builder dataInizioPeriodiChiusuraStagionale(Date dataInizioPeriodiChiusuraStagionale) {
            this.dataInizioPeriodiChiusuraStagionale = dataInizioPeriodiChiusuraStagionale;
            return this;
        }

        public Builder dataFinePeriodiChiusuraStagionale(Date dataFinePeriodiChiusuraStagionale) {
            this.dataFinePeriodiChiusuraStagionale = dataFinePeriodiChiusuraStagionale;
            return this;
        }

        public Builder annoRiferimento(String annoRiferimento) {
            this.annoRiferimento = annoRiferimento;
            return this;
        }

        public Builder motivazioneChiusura(String motivazioneChiusura) {
            this.motivazioneChiusura = motivazioneChiusura;
            return this;
        }

        public Builder periodoEliminato(Boolean periodoEliminato) {
            this.periodoEliminato = periodoEliminato;
            return this;
        }

        public Builder dataInserimento(Timestamp dataInserimento) {
            this.dataInserimento = dataInserimento;
            return this;
        }

        public Builder strutturaApertaTuttoLAnno(Boolean strutturaApertaTuttoLAnno) {
            this.strutturaApertaTuttoLAnno = strutturaApertaTuttoLAnno;
            return this;
        }

        public PeriodiChiusura build() {
            PeriodiChiusura periodi = new PeriodiChiusura();
            periodi.setIdPeriodiChiusura(this.idPeriodiChiusura);
            periodi.setStrutturaId(this.strutturaId);
            periodi.setDataInizioPeriodiChiusura(this.dataInizioPeriodiChiusura);
            periodi.setDataFinePeriodiChiusura(this.dataFinePeriodiChiusura);
            periodi.setDataInizioPeriodiChiusuraStagionale(this.dataInizioPeriodiChiusuraStagionale);
            periodi.setDataFinePeriodiChiusuraStagionale(this.dataFinePeriodiChiusuraStagionale);
            periodi.setMotivazioneChiusura(this.motivazioneChiusura);
            periodi.setAnnoRiferimento(this.annoRiferimento);
            periodi.setPeriodoEliminato(this.periodoEliminato);
            periodi.setDataInserimento(this.dataInserimento);
            periodi.setStrutturaApertaTuttoLAnno(this.strutturaApertaTuttoLAnno);
            return periodi;
        }
    }
}
