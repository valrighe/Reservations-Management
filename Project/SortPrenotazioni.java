//classe che implementa Comparator per ordinare l'array delle 
//prenotazioni secondo la variabile della data

import java.util.Comparator;

public class SortPrenotazioni implements Comparator <PrenotazioneBase> {
	public int compare(PrenotazioneBase data1, PrenotazioneBase data2) {
		return data1.getDataPrenotazione().compareTo(data2.getDataPrenotazione());
	}
}