package it.polito.tdp.imdb.model;

import java.util.Map;

public class SimulationResult {

	private Map<Integer, Actor> attoriIntervistati;
	private int giorniPause;
	public SimulationResult(Map<Integer, Actor> attoriIntervistati, int giorniPause) {
		super();
		this.attoriIntervistati = attoriIntervistati;
		this.giorniPause = giorniPause;
	}
	public Map<Integer, Actor> getAttoriIntervistati() {
		return attoriIntervistati;
	}
	public void setAttoriIntervistati(Map<Integer, Actor> attoriIntervistati) {
		this.attoriIntervistati = attoriIntervistati;
	}
	public int getGiorniPause() {
		return giorniPause;
	}
	public void setGiorniPause(int giorniPause) {
		this.giorniPause = giorniPause;
	}
	
	
	@Override
	public String toString() {
		
		String s = "Simulazione completata \nGiorni di pausa: " + this.giorniPause + "\nAttori intervistati: \n";
		
		for(Integer i : this.attoriIntervistati.keySet()) {
			
			s += "Giorno " + i + " -> " + this.attoriIntervistati.get(i) + "\n";
			
		}
		
		return s;
	}
	
	
	
	
}
