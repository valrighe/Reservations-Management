//classe che definisce una prenotazione con animazione
//implementa Serializable per essere salvata su file
//estende PrenotazioneCatering aggiungendo il tipo di 
//animazione

import java.text.*;
import java.util.*;
import java.io.*;

public class PrenotazioneAnimazione extends PrenotazioneCatering implements Serializable {
	//tipo di animazione
	private String tipoAnimazione;
	
	//variabile richiesta da Serializable
	static final long serialVersionUID = 1;
	
	//costruttore di default
	//le variabili d'istanza sono inizalizzate per evitare
	//una eventuale NullPointerException
	public PrenotazioneAnimazione() {
		super();
		this.tipoAnimazione = "";
	}
	
	//costruttore
	public PrenotazioneAnimazione(Date data, String nome, int bambini, String animazione) {
		super(data, nome, bambini);
		this.tipoAnimazione = animazione;
		
	}

	//restituisce il tipo di animazione
	public String getTipoAnimazione() {
		return tipoAnimazione;
	}

	//imposta il tipo di animazione in base alla scelta dell'utente
	public void setTipoAnimazione(String animazione) {
		if (animazione.equals("G")) this.tipoAnimazione = "Organizzazione di giochi";
		else if (animazione.equals("S")) this.tipoAnimazione = "Spettacolo di magia";
		else this.tipoAnimazione = "Spettacolo di burattini";	
	}
	
	//visualizza tutte le informazioni sulla prenotazione
	public void visualizza() {
		DateFormat formatoData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		System.out.println("DATA: " + formatoData.format(getDataPrenotazione()));
		System.out.println("TIPO PRENOTAZIONE: Prenotazione con catering e animazione");
		System.out.println("NUMERO BAMBINI: " + getNumeroBambini());
		System.out.println("TIPO ANIMAZIONE: " + tipoAnimazione);
		System.out.println("NOME: " + getNomePrenotazione());
		System.out.println();
	}
}
