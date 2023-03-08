//classe che visualizza l'interfaccia testuale (menu), 
//inizializza un nuovo oggetto registro e chiede
//all'utente di scegliere fra le opzioni presenti. 

import java.util.*;

public class GestioneRegistroPrenotazioni {
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);
		RegistroPrenotazioni registro = new RegistroPrenotazioni();
		
		//menu che permette di scegliere le opzioni del programma
		String scelta;
		do {
			System.out.println("SCEGLIERE UNA OPERAZIONE FRA QUELLE DISPONIBILI:");
			System.out.println("   [P]renotare il locale");
			System.out.println("   [V]isualizzare il calendario delle prenotazioni");
			System.out.println("   [C]atering: elenco prenotazioni");
			System.out.println("   [A]nimazione: elenco prenotazioni");
			System.out.println("   [E]liminare una prenotazione");
			System.out.println("   [D]ate disponibili");
			System.out.println("   [N]umero prenotazioni effettuate per nome");
			System.out.println("   [S]alva ed esci");
			System.out.println();
			System.out.println("Inserire la propria scelta: ");
				
			scelta = input.next();
			//trasforma in maiuscola anche un input minuscolo
			scelta = scelta.toUpperCase();
			
			switch (scelta) {
			case "P": registro.nuovaPrenotazione(); break;
			case "V": registro.calendarioPrenotazioni(); break;
			case "C": registro.prenotazioniCatering(); break;
			case "A": registro.prenotazioniAnimazione(); break;
			case "E": registro.eliminaPrenotazione(); break;
			case "D": registro.primaDataDisponibile(); break;
			case "N": registro.numeroPrenotazioni(); break;
			case "S": registro.salvaPrenotazioni(); break;
			default: {
				System.out.println();
				System.out.println("ATTENZIONE: i valori inseriti non sono validi");
				break;
			}
			}
			System.out.println();
		} while (!scelta.equals("S"));

		input.close(); 

	} 
}