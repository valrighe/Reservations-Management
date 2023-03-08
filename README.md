# Reservations-Management
Progetto d'esame di Programmazione e Analisi Di Dati, Mod. A - UNIPI, magistrale di Informatica Umanistica, 2020

## Il progetto
Il programma è stato scritto interamente in Java. E' stato pensato per essere utilizzato dal proprietario/gestore di un locale per l'organizzazione di compleanni di bambini, in modo da tenere traccia delle prenotazioni (ognuna con le sue caratteristiche) che vengono effettuate.

### Specifiche di progetto
Si può organizzare un solo compleanno al giorno. Sono previste diverse forme di prenotazione: 
- semplice affitto del locale (cibo e animazione a carico dei genitori); 
- affitto del locale con servizio di catering. Bisogna conoscere il numero di bambini presenti;
- affitto del locale con servizio di catering e animazione. Bisogna conoscere il numero di bambini e il tipo di animazione fra organizzazione di giochi, spettacolo di magia o spettacolo di burattini.

Il programma dovrà: 
- aggiungere nuove prenotazioni;
- visualizzare l'elenco delle prenotazioni in ordine di data;
- visualizzare solo le prenotazioni che prevedono catering (ed eventualmente animazione);
- visualizzare solo le prenotazioni che prevedono animazione;
- eliminare una prenotazione.
In più, dovrà: 
- consentire di visualizzare la prima data disponibile a partire dal giorno corrente;
- visualizzare le prenotazioni effettuate da un certo cliente sulla base del suo nome o una porzione di esso;
- salvare l'elenco delle prenotazioni su un file binario e di caricarla da un file precedentemente salvato sfruttando la serializzazione di oggetti.

Il programma dovrà essere dotato di un'interfaccia testuale (un menù) che consenta di utilizzare tutte le funzionalità implementate. Facoltativamente, potrà essere prevista (in aggiunta) un'interfaccia grafica (in fase di implementazione, *ndr*).

## Contenuto della repository
- File *README.md* con le specifiche di progetto;
- Relazione *Relazione_ValentinaRighetti* sulla realizzazione del progetto;
- Cartella *Project* con i file Java.
