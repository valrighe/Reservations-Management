//classe che gestisce le caratteristiche di una prenotazione
//del solo locale

import java.text.*;
import java.util.*;
import java.io.*;


public class PrenotazioneBase implements Serializable{	
	//data e nome della prenotazione
	protected Date dataPrenotazione;
	protected String nomePrenotazione;
	
	//variabile richiesta da Serializable
	static final long serialVersionUID = 1;
	
	//costruttore di default
	//le variabili d'istanza sono inizalizzate per evitare
	//una eventuale NullPointerException
	public PrenotazioneBase() {
		this(new Date(), "");
	}
	
	//costruttore
	public PrenotazioneBase(Date data, String nome) {
		this.dataPrenotazione = data;
		this.nomePrenotazione = nome;
	}
	
	//restituisce la data della prenotazione
	public Date getDataPrenotazione() {
		return dataPrenotazione;
	}
	
	//restituisce il nome a cui e' associata la prenotazione
	public String getNomePrenotazione() {
		return nomePrenotazione;
	}
	
	//imposta la data della prenotazione
	public void setDataPrenotazione(Date data) {
		this.dataPrenotazione = data;
	}
	
	//imposta il nome a cui e' associata la prenotazione
	public void setNomePrenotazione(String nome) {
		this.nomePrenotazione = nome;
	}
	
	//visualizza tutte le informazioni sulla prenotazione
	public void visualizza() {
		DateFormat formatoData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		System.out.println("DATA: " + formatoData.format(dataPrenotazione));
		System.out.println("TIPO PRENOTAZIONE: Prenotazione base");
		System.out.println("NOME: " + nomePrenotazione);
		System.out.println();
	}
}
