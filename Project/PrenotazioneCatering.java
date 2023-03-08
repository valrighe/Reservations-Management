//classe che definisce una prenotazione con catering
//implementa Serializable per essere salvata su file
//estende PrenotazioneBase aggiungendo il numero di 
//bambini alla festa

import java.text.*;
import java.util.*;
import java.io.*;

public class PrenotazioneCatering extends PrenotazioneBase implements Serializable {	
	//numero dei bambini alla festa
	protected int numeroBambini;
	
	//variabile richiesta da Serializable
	static final long serialVersionUID = 1;
	
	//costruttore di default
	//le variabili d'istanza sono inizalizzate per evitare
	//una eventuale NullPointerException
	public PrenotazioneCatering() {
		super();
		this.numeroBambini = 0;
	}
	
	//costruttore
	public PrenotazioneCatering(Date data, String nome, int bambini) {
		super(data, nome);
		this.numeroBambini = bambini;
		
	}
	
	//restituisce il numero di bambini alla festa
	public int getNumeroBambini() {
		return numeroBambini;
	}
	
	//imposta il numero di bambini alla festa
	public void setNumeroBambini(int numero) {
		this.numeroBambini = numero;
	}

	//visualizza tutte le informazioni sulla prenotazione
	public void visualizza() {
		DateFormat formatoData = DateFormat.getDateInstance(DateFormat.LONG, Locale.ITALY);
		System.out.println("DATA: " + formatoData.format(getDataPrenotazione()));
		System.out.println("TIPO PRENOTAZIONE: Prenotazione con catering");
		System.out.println("NUMERO BAMBINI: " + numeroBambini);
		System.out.println("NOME: " + getNomePrenotazione());
		System.out.println();
	}
}
