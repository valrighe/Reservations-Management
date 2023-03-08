//classe che implementa il menu e le principali operazioni
//relative alla gestione del locale

import java.io.*;
import java.text.*;
import java.util.*;

public class RegistroPrenotazioni {
	
	Scanner input;
	private ArrayList<PrenotazioneBase> prenotazioni;
	
	//costruttore che legge il file binario e lo associa ad un array prenotazioni
	public RegistroPrenotazioni() {
		input = new Scanner(System.in);
		
		try {
			prenotazioni = new ArrayList<>();
			ObjectInputStream in = new ObjectInputStream (
					new BufferedInputStream(new FileInputStream("registro.txt")));

			prenotazioni = (ArrayList<PrenotazioneBase>) in.readObject();
			in.close();
			
		} catch (FileNotFoundException e) {
			//gestisce il caso in cui il file non esiste
			//se il file non esiste viene creato automaticamente, 
			//quindi non servono segnalazioni all'utente
		} catch (ClassNotFoundException e) {
			//gestisce il caso in cui non ci siano oggetti contenuti nel file
			System.out.println("ERRORE di lettura");
			System.out.println(e);
		} catch (IOException e) {
			//gestisce errori di input e output
			System.out.println("ERRORE di I/O");
			System.out.println(e);
		}
		
		//elimina dall'elenco le prenotazioni in date già passate
		eliminaPrenotazioniPassate();
	}
		
	//elimina le prenotazioni già passate rispetto alla data corrente
	private void eliminaPrenotazioniPassate() {
		//esegue un controllo inverso per poterlo effettuare su 
		//tutti gli elementi dell'array
		for (int i=prenotazioni.size()-1; i>=0; i--) {
			if (prenotazioni.get(i).getDataPrenotazione().before(getDataCorrente())) {
				prenotazioni.remove(prenotazioni.get(i));
			}
		}
	}
	
	//permette l'inserimento nell'array di una nuova prenotazione
	public void nuovaPrenotazione() {
		PrenotazioneBase pren = null;
		Date dataPren = null;
		
		//datePren avra' il valore inserito dall'utente
		dataPren = dataPrenotazione();
		
		//gestisce la scelta del tipo di prenotazione	
		String scelta;
		do {
			System.out.println();
			System.out.println("FORME DI PRENOTAZIONE:");
			System.out.println("   [L]ocale");
			System.out.println("   [C]atering (locale incluso)");
			System.out.println("   [A]nimazione (locale e catering inclusi)");
			System.out.println();
			System.out.println("Inserire la propria scelta: ");
			
			scelta = input.next();
			scelta = scelta.toUpperCase();

			if (!scelta.equals("L") && !scelta.equals("C") && !scelta.equals("A")) {
				System.out.println();
				System.out.println("ATTENZIONE: valori inseriti non corretti");
			}
				
		} while (!scelta.equals("L") && !scelta.equals("C") && !scelta.equals("A"));
		
		//a seconda della scelta crea un nuovo oggetto prenotazione
		switch (scelta) {
		case "L": pren = new PrenotazioneBase(); break;
		case "C": pren = new PrenotazioneCatering(); break;
		case "A": pren = new PrenotazioneAnimazione(); break;
		default: System.out.println("ERRORE"); break;
		}
		
		//aggiunge la data della prenotazione
		pren.setDataPrenotazione(dataPren);
		
		//se la prenotazione prevede catering o animazione vengono
		//gestiti il numero di bambini e il tipo di animazione
		if (pren instanceof PrenotazioneAnimazione) {
			((PrenotazioneAnimazione) pren).setNumeroBambini(gestoreNumeroBambini());
			((PrenotazioneAnimazione) pren).setTipoAnimazione(menuTipoAnimazione());
		} else if (pren instanceof PrenotazioneCatering) {
			((PrenotazioneCatering) pren).setNumeroBambini(gestoreNumeroBambini());
		}
		
		//aggiunge il nome della prenotazione
		pren.setNomePrenotazione(gestoreNomePrenotazione());
		//aggiunge la prenotazione all'ArrayList
		prenotazioni.add(pren);
	}
	
	//ordina l'array per data e visualizza il calendario ordinato
	public void calendarioPrenotazioni() {
		//se l'array è vuoto avverte l'utente
		if (prenotazioni.isEmpty()) {
			System.out.println();
			System.out.println("Non ci sono prenotazioni registrate");
			return;
		}
		
		//restituisce l'array ordinato per data
		Collections.sort(prenotazioni, new SortPrenotazioni());
		//visualizza l'elenco delle prenotazioni
		System.out.println();
		System.out.println("Elenco prenotazioni registrate: ");
		for (int i=0; i<prenotazioni.size(); i++) {
			prenotazioni.get(i).visualizza();
		}
	}
	
	//restituisce solo le prenotazioni che prevedono catering
	public void prenotazioniCatering() {
		boolean trovato = false;
		
		//se l'array è vuoto avverte l'utente
		if (prenotazioni.isEmpty()) {
			System.out.println();
			System.out.println("Non ci sono prenotazioni registrate");
			return;
		}
		
		//l'array viene ordinato per data
		Collections.sort(prenotazioni, new SortPrenotazioni());
		System.out.println();
		//se l'oggetto all'indice i e' del solo tipo PrenotazioneCatering
		//viene visualizzato
		for (int i=0; i<prenotazioni.size(); i++) {
			if (prenotazioni.get(i) instanceof PrenotazioneCatering
					&& !(prenotazioni.get(i) instanceof PrenotazioneAnimazione)) {
				((PrenotazioneCatering) prenotazioni.get(i)).visualizza();
				trovato = true;
			}
		}

		if (!trovato) {
			System.out.println("Non sono presenti prenotazioni con catering");
		}
	}
	
	//restituisce solo le prenotazioni che prevedono animazione
	public void prenotazioniAnimazione() {
		boolean trovato = false;
		
		//se l'array è vuoto avverte l'utente
		if (prenotazioni.isEmpty()) {
			System.out.println();
			System.out.println("Non ci sono prenotazioni registrate");
			return;
		}
		
		//l'array viene ordinato per data
		Collections.sort(prenotazioni, new SortPrenotazioni());
		//visualizza le prenotazioni con animazione
		System.out.println();
		//se l'oggetto all'indice i e' di tipo PrenotazioneAnimazione
		//viene visualizzato
		for (int i=0; i<prenotazioni.size(); i++) {
			if (prenotazioni.get(i) instanceof PrenotazioneAnimazione) {
				((PrenotazioneAnimazione) prenotazioni.get(i)).visualizza();
				trovato = true;
			}
		}
		
		if (!trovato) {
			System.out.println("Non sono presenti prenotazioni con animazione");
		}
	}
		
	//elimina una prenotazione scelta dall'utente in base al nome e 
	//alla data (inseriti dall'utente stesso)
	public void eliminaPrenotazione() {
		String nome = "";
		int giorno = 0;
		int mese = 0;
		int anno = 0;
		Date dataPren = getDataCorrente();
		
		//se l'array e' vuoto non ci sono prenotazioni da eliminare
		if(prenotazioni.isEmpty()) {
			System.out.println("Non ci sono prenotazioni registrate");
			System.out.println();
			return;
		}
		
		//chiede di inserire il nome e fa i dovuti controlli
		do {
			System.out.println();
			System.out.println("Inserire il nome della prenotazione (in parte o completo):");
			System.out.println("[Il nome deve essere composto da sole lettere]");
			nome = input.next();
			//il nome viene convertito in maiuscolo per comodita'
			nome = nome.toUpperCase();
		} while (!controllaNomePrenotazione(nome));
		
		//chiede di inserire la data e fa i dovuti controlli
		do {
			try {
				System.out.println();
				System.out.println("Inserire la data della prenotazione:");
				System.out.println("[Inserire valori numerici]");
				System.out.println();
				System.out.print("Giorno: ");
				giorno = input.nextInt();
				System.out.print("Mese: ");
				mese = input.nextInt();
				System.out.print("Anno: ");
				anno = input.nextInt();
				
			} catch (InputMismatchException e) {
				//gestisce il caso in cui il tipo inserito dall'utente non sia 
				//quello richiesto
				input.next(); //in caso di eccezione prosegue col programma
			}
			
			//controlla che gli interi in input corrispondano ad una data
			if (controllaValiditaData(giorno, mese, anno)) {
				Calendar dataCal = Calendar.getInstance();
				//(mese-1) perche' Calendar conta i mesi partendo da 
				//Gennaio=0
				dataCal.set(anno, mese-1, giorno);
				//Time impostato a 0 per poterlo confrontare con la data inserita
				dataCal.set(Calendar.HOUR_OF_DAY, 0);
				dataCal.set(Calendar.MINUTE, 0);
				dataCal.set(Calendar.SECOND, 0);
				dataCal.set(Calendar.MILLISECOND, 0);
				dataPren = dataCal.getTime();
			} else {
				System.out.println();
				System.out.println("ATTENZIONE: i valori inseriti non sono validi");
				System.out.println("Inserire un'altra data:");
			}
			
			//controlla che la data in input sia uguale o successiva alla corrente
			//per essere tale deve essere >=0
			if (controllaSuccessioneDate(dataPren)<0) {
				System.out.println();
				System.out.println("La data inserita è già passata");
				System.out.println("Scegliere un'altra data");
			}
			
		} while (!controllaValiditaData(giorno, mese, anno) 
				|| controllaSuccessioneDate(dataPren)<0);
		
		//verifica che i dati inseriti corrispondano ad una prenotazione
		boolean trovato = false;
		int c = 0;
		//il metodo contains funziona con <stringa>.contains(CharSequence)
		CharSequence substring = nome; 
		for (int i=0; i<prenotazioni.size(); i++) {
			if (prenotazioni.get(i).getDataPrenotazione().equals(dataPren)
					&& prenotazioni.get(i).getNomePrenotazione().contains(substring)) {
				trovato = true;
				c = i;
				break;
			}
		}
		
		//se la prenotazione esiste viene visualizzata e si richiede all'utente
		//un'ultima conferma per eliminarla
		if (trovato) {
			System.out.println();
			System.out.println("La prenotazione che si vuole eliminare e' la seguente:");
			System.out.println();
			prenotazioni.get(c).visualizza();
			String scelta = "";
			
			do {
				System.out.print("Procedere con l'eliminazione [S/N]? ");
				scelta = input.next();
				scelta = scelta.toUpperCase();
				
				if (!scelta.equals("S") && !scelta.equals("N")) {
					System.out.println();
					System.out.println("ATTENZIONE: i valori inseriti non sono corretti");
					System.out.println();
				}
			} while (!scelta.equals("S") && !scelta.equals("N"));
			
			if (scelta.equals("S")) {
				prenotazioni.remove(c);
				System.out.println();
				System.out.println("Operazione avvenuta con successo");
				return;
			} else if (scelta.equals("N")) {
				System.out.println();
				System.out.print("Operazione annullata");
				System.out.println();
				return;
			}
		} else {
			System.out.println();
			System.out.print("ATTENZIONE: non sono presenti prenotazioni con i dati inseriti");
			System.out.println();
		}	
	}
	
	//restituisce la prima data disponibile per una prenotazione
	public void primaDataDisponibile() {
		Date data = getDataCorrente();
		
		//se l'array è vuoto la prima data disponibile è la data corrente
		if (prenotazioni.isEmpty()) {
			DateFormat formatoDataOggi = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
			String s = formatoDataOggi.format(data);
			System.out.println();
			System.out.println("Non ci sono prenotazioni registrate.");
			System.out.println("La prima data disponibile e' quella di oggi, " + s);
			return;
		}
		
		//ordina l'array delle prenotazioni per data
		Collections.sort(prenotazioni, new SortPrenotazioni());
		
		//se la prima prenotazione e' successiva alla corrente
		//restituisce la data corrente
		if(prenotazioni.get(0).getDataPrenotazione().after(data)) {
			DateFormat formatoDataOggi = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
			String s = formatoDataOggi.format(data);
			System.out.println("La prima data disponibile e' quella di oggi, " + s);
			return;
		}
		
		//restituisce la prima data disponibile se le prenotazioni non
		//sono in date consecutive
		Calendar calDataDisp = Calendar.getInstance();
		calDataDisp.set(Calendar.HOUR_OF_DAY, 0);
		calDataDisp.set(Calendar.MINUTE, 0);
		calDataDisp.set(Calendar.SECOND, 0);
		calDataDisp.set(Calendar.MILLISECOND, 0);
		calDataDisp.add(Calendar.DATE, 1);
		Date dataDisponibile = calDataDisp.getTime();
		for (int i=1; i<prenotazioni.size(); i++) {
			if (!dataDisponibile.equals(prenotazioni.get(i).getDataPrenotazione())
					&& dataDisponibile.before(prenotazioni.get(i).getDataPrenotazione())) {
				DateFormat formatoDataDomani = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
				String s = formatoDataDomani.format(dataDisponibile);
				System.out.println("La prima data disponibile e' il " + s);
				return;
			}
			else {
				calDataDisp.add(Calendar.DATE, 1);
				dataDisponibile = calDataDisp.getTime();
			}
		}
		
		//se l'array presenta date prenotate consecutive, la prima data disponibile
		//e' la successiva all'ultima prenotata
		Calendar calUltimaData = Calendar.getInstance();
		Date ultimaDataPrenotata = prenotazioni.get(prenotazioni.size()-1).getDataPrenotazione();
		calUltimaData.add(Calendar.DATE, prenotazioni.size());
		ultimaDataPrenotata = calUltimaData.getTime();
		DateFormat formatoUltimaData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		String s = formatoUltimaData.format(ultimaDataPrenotata);
		System.out.println("La prima data disponibile e' il " + s);
	}
	
	//restituisce le prenotazioni di un cliente in base al nome
	public void numeroPrenotazioni() {
		//se l'array e' vuoto avverte l'utente
		if(prenotazioni.isEmpty()) {
			System.out.println();
			System.out.println("Non ci sono prenotazioni registrate");
			return;
		}
		
		String nome = "";
		//chiede di inserire il nome e fa i dovuti controlli
		do {
			System.out.println();
			System.out.println("Inserire il nome della prenotazione (in parte o completo):");
			System.out.println("[Il nome deve essere composto da sole lettere]");
			nome = input.next();
			//il nome viene convertito in maiuscolo per comodita'
			nome = nome.toUpperCase();
		} while (!controllaNomePrenotazione(nome));
		
		//il metodo contains richiede l'uso di una CharSequence
		CharSequence substring = nome;
		boolean trovato = false;
		//ordina l'array delle prenotazioni per data
		Collections.sort(prenotazioni, new SortPrenotazioni());
		//visualizza tutte le prenotazioni che contengono il nome 
		//o la porzione del nome in input
		System.out.println();
		for (int i=0; i<prenotazioni.size(); i++) {
			if (prenotazioni.get(i).getNomePrenotazione().contains(substring)) {
				prenotazioni.get(i).visualizza();
				trovato = true;
			}
		}
		
		//se non ci sono corrispondenze l'utente viene avvertito
		if (!trovato) {
			System.out.println("ATTENZIONE: non sono presenti prenotazioni con questo nome");
			return;
		}
	}
	
	//gestisce il nome inserito dall'utente e restituisce il nome
	private String gestoreNomePrenotazione() {
		String nome = "";
		
		do {
			System.out.println();
			System.out.println("Inserire il nome della prenotazione");
			System.out.println("[Il nome deve essere composto da sole lettere]");
			nome = input.next();
			//il nome viene convertito in maiuscolo per comodita'
			nome = nome.toUpperCase();
		} while (!controllaNomePrenotazione(nome));

		return nome;
	}
	
	//controlla che il nome inserito non sia una stringa vuota o numeri
	private boolean controllaNomePrenotazione(String nome) {
		//controllo sulla stringa vuota o null
		if (nome.isEmpty() || nome.equals(null)) {
			System.out.println("ATTENZIONE: il nome deve contenere almeno un carattere");
			return false;
		}
		
		//controlla che non sia formata da numeri
		for (int j=0; j<nome.length(); j++) {
			char k = nome.charAt(j);
			if (!Character.isLetter(k)) {
				System.out.println("ATTENZIONE: il nome deve essere composto solo da lettere");
				return false;
			}
		}
		
		//se va tutto bene restituisce true
		return true;	
	}
	
	//gestisce il numero di bambini al locale e lo restituisce
	private int gestoreNumeroBambini() {
		int numero = 0;
		boolean tipoOk;
		
		do {
			do {
				tipoOk = true;
				try {
					System.out.println();
					System.out.println("Quanti bambini saranno presenti alla festa?");
					System.out.println("[Il numero deve essere compreso fra 1 e 500]");
					numero = input.nextInt(); 
				} 
				catch (InputMismatchException e) {
					//gestisce il caso in cui il tipo inserito dall'utente non sia 
					//quello richiesto
					
					//annulla l'input ricevuto
					input.nextLine();
					
					System.out.println();
					System.out.println("ATTENZIONE: i valori inseriti non sono validi"); 
					tipoOk = false;
				}
			} while (!tipoOk);
		} while (controllaNumeroBambini(numero));

		return numero;
	}

	//controlla i valori inseriti dall'utente
	private boolean controllaNumeroBambini(int num) {		
		if (num <= 0) {
			System.out.println();
			System.out.println("ATTENZIONE: il numero deve essere maggiore di zero");
			return true;
		} else if (num > 500) {
			System.out.println();
			System.out.println("ATTENZIONE: il numero massimo consentito e' 500");
			return true;
		}
		
		return false;
	}
	
	//avvia il menu sulla scelta del tipo di animazione e la resituisce
	private String menuTipoAnimazione() {
		String scelta;
		do {
			System.out.println();
			System.out.println("Scegliere il tipo di animazione:");
			System.out.println("[G]iochi");
			System.out.println("[S]pettacolo di magia");
			System.out.println("[B]urattini");
			System.out.println();
			System.out.println("Inserire la propria scelta: ");
			scelta = input.next();
			//converte in maiuscolo per comodita'
			scelta = scelta.toUpperCase();
			
			//se va tutto bene restituisce la scelta
			if (scelta.equals("G") || scelta.equals("S") || scelta.equals("B")) {
				return scelta;
			}
			
			System.out.println();
			System.out.println("ATTENZIONE: i valori inseriti non sono validi");
			
		} while	(!scelta.equals("G") && !scelta.equals("S") && !scelta.equals("B"));
		
		return scelta;
	}

	//gestisce l'inserimento della data e controlla che i valori siano corretti
	private Date dataPrenotazione() {
		int giorno = 0;
		int mese = 0;
		int anno = 0;
		Date dataPren = getDataCorrente();
		boolean tipoOk;
		
		System.out.println();
		System.out.println("DATA PRENOTAZIONE");
		do {
			do {
				tipoOk = true;
				try {
					System.out.println("[inserire valori numerici]");
					System.out.print("Giorno: ");
					giorno = input.nextInt();
					System.out.print("Mese: ");
					mese = input.nextInt();
					System.out.print("Anno: ");
					anno = input.nextInt();
				
				} catch (InputMismatchException e) {
					//gestisce il caso in cui il tipo inserito dall'utente non sia 
					//quello richiesto
				
					//annulla l'input ricevuto
					input.nextLine();
				
					System.out.println();
					System.out.println("ATTENZIONE: i valori inseriti non sono validi");
					System.out.println("Inserire un'altra data");
					System.out.println();
					tipoOk = false;
				}		
			} while(!tipoOk);
			
			//controlla che gli interi in input corrispondano ad una data
			if (controllaValiditaData(giorno, mese, anno)) {
				Calendar dataCal = Calendar.getInstance();
				//(mese-1) perche' Calendar conta i mesi partendo
				//da Gennaio=0
				dataCal.set(anno, mese-1, giorno);
				dataCal.set(Calendar.HOUR_OF_DAY, 0);
				dataCal.set(Calendar.MINUTE, 0);
				dataCal.set(Calendar.SECOND, 0);
				dataCal.set(Calendar.MILLISECOND, 0);
				dataPren = dataCal.getTime();
				
				//controlla che la data in input sia uguale o successiva alla corrente
				if (controllaSuccessioneDate(dataPren)<0) {
					System.out.println();
					System.out.println("ATTENZIONE: la data inserita è già passata");
					System.out.println("Scegliere un'altra data");
					System.out.println();
				}
				
				//controlla che il locale sia libero in quella data
				else if (controllaDataPrenotazione(dataPren)) {
					System.out.println();
					System.out.println("ATTENZIONE: il locale è già prenotato in quella data");
					System.out.println("Scegliere un'altra data");
					System.out.println();
				}
				
			} 
			else {
				System.out.println();
				System.out.println("ATTENZIONE: i valori inseriti non sono validi");
				System.out.println("Inserire un'altra data");
				System.out.println();
			}
			
		} while (!controllaValiditaData(giorno, mese, anno) || controllaSuccessioneDate(dataPren)<0
				|| controllaDataPrenotazione(dataPren));
		
		return dataPren;
	}
	
	//controlla che i valori inseriti dall'utente corrispondano ad una data
	private boolean controllaValiditaData (int g, int m, int a) {
		//il giorno va bene se e' maggiore di uno ed esistente per
		//il mese inserito
		boolean giornoOk = (g >= 1) && (g <= controllaGiorniDelMese(a, m));
		boolean meseOk = (m >= 1) && (m <= 12);	
		//le date scelte sono convezionali
		boolean annoOk = (a >= 2010) && (a <= 2500);
		
		//restituisce true se la data esiste
		return (giornoOk && meseOk && annoOk);
	}
	
	//restituisce il numero di giorni di un determinato mese
	private int controllaGiorniDelMese(int a, int m) {
		int giorniDelMese;
		
		//definisce il numero di giorni per ogni mese
		switch (m) {
		case 1: case 3: case 5: case 7: case 8:
		case 10: case 12: {
			giorniDelMese = 31;
			break;
		}
		//nel caso di febbraio il numero di giorni cambia
		//a seconda dell'anno (bisestile o no)
		case 2: {
			if (((a % 4 == 0) && (a % 100 != 0)) 
					|| a % 400 == 0) {
				giorniDelMese = 29;
			} else {
				giorniDelMese = 28;
			}
			break;
		}
		default: {
			giorniDelMese = 30; 
			break;
		}
		}
		
		return giorniDelMese;
	}
	
	//controlla che la data inserita dall'utente sia successiva alla corrente
	private int controllaSuccessioneDate(Date data) {
		//restituisce 0 se le due date sono uguali, un valore <0 se
		//data e' precedente alla corrente e un valore >0 se successiva
		return (data.compareTo(getDataCorrente()));
	}
	
	//controlla che nella data inserita dall'utente non ci sia già una prenotazione
	private boolean controllaDataPrenotazione(Date data) {
		//se l'array e' vuoto puo' prenotare
		if (prenotazioni.isEmpty()) {
			return false;
		} else {
			//controlla che non ci sia gia' una prenotazione
			for (int i=0; i<prenotazioni.size(); i++) {
				if (prenotazioni.get(i).getDataPrenotazione().equals(data)) 
					return true;
			}
		}
		
		//se va tutto bene restituisce false
		return false;
	}
	
	//restituisce la data corrente
	private Date getDataCorrente() {
		Date dataOggi = new Date();
		//rimuove il time dalla data per fare i controlli tra date
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dataOggi = cal.getTime();
		
		return dataOggi;
	}
	
	//scrive l'elenco delle prenotazioni su un file binario
	public void salvaPrenotazioni() {
		try {
			ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(
					new FileOutputStream("registro.txt")));
			out.writeObject(prenotazioni);
			out.close();
					
		} catch (IOException e) {
			//gestisce errori di input e output
			System.out.println("ERRORE di I/O");
			System.out.println(e);
		}	
		
		System.out.println();
		System.out.println("Grazie e arrivederci");
	}
}