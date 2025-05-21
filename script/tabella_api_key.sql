-- Crea la tabella
CREATE TABLE struttureapikey (
    id BIGSERIAL PRIMARY KEY,
    cusr VARCHAR(50) NOT NULL,
    apikey VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT uc_apikey UNIQUE (apikey)
);

-- Commenti sui campi (opzionali ma utili per documentazione)
COMMENT ON TABLE struttureapikey IS 'Tabella per la gestione delle API key delle strutture turistiche';
COMMENT ON COLUMN struttureapikey.id IS 'ID univoco autoincrementale';
COMMENT ON COLUMN struttureapikey.cusr IS 'Codice utente/struttura associato alla API key';
COMMENT ON COLUMN struttureapikey.apikey IS 'Chiave API per autenticazione';
COMMENT ON COLUMN struttureapikey.created_at IS 'Data e ora di creazione del record';
COMMENT ON COLUMN struttureapikey.updated_at IS 'Data e ora di ultimo aggiornamento del record';

-- Concede i privilegi all'owner
GRANT ALL PRIVILEGES ON TABLE struttureapikey TO portaleturismo_owner;

-- Concede i privilegi sulla sequenza per l'autoincrement
GRANT ALL PRIVILEGES ON SEQUENCE struttureapikey_id_seq TO portaleturismo_owner;

-- Aggiungi indice per migliorare le ricerche per apikey (opzionale ma consigliato)
CREATE INDEX idx_struttureapikey_apikey ON struttureapikey(apikey);

-- Se vuoi concedere anche i privilegi per gli indici
GRANT ALL PRIVILEGES ON INDEX idx_struttureapikey_apikey TO portaleturismo_owner;