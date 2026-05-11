# Rubrica Touring 

Progetto Java Desktop per la gestione di contatti, sviluppato con architettura **MVC** e separazione netta tra **Frontend** (Swing) e **Backend**.

## Caratteristiche principali
- **Login di accesso**: Credenziali gestite tramite file di configurazione esterno.
- **Persistenza Doppia**: Possibilità di scegliere tra salvataggio su **Database MySQL** o su **File singoli (.txt)**.
- **Interfaccia Avanzata**: JTable con filtri di ricerca in tempo reale e JToolBar con icone.
- **Extra**: Integrazione con Google Maps per la visualizzazione degli indirizzi.

## Tecnologie utilizzate
- Java 17
- Maven (Gestione dipendenze)
- MySQL Connector
- Lombok 

## Setup
Configurare le credenziali nel file `src/main/resources/config.properties` e scegliere la modalità `DATABASE` o `FILE`.