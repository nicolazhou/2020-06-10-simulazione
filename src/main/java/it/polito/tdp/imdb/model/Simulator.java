package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;

import it.polito.tdp.imdb.model.Event.EventType;

public class Simulator {
	
	/*
	 * SIMULAZIONE
	 * 
	 * ---- INPUT -----
	 * N = giorni
	 * 
	 * 
	 * Ogni giorno il produttore sceglie un attore a caso tra quelli presenti nel grafo
	 *  ( Non intervista mai lo stesso attore due volte )
	 * 
	 * 
	 * 1 Giorno --> scelta casuale
	 * 
	 * 2 giorno in poi:
	 * - 60% casuale da quelli presenti nel grafo
	 * - 40% vicino di grado max dell'attore intervistato (Se ci sono di più, si sceglie a caso, in caso contrario --> 60%)
	 * 
	 * 
	 * Se per 2 giorni consecutivi il produttore intervista attori dello stesso genere (gender) --> 90% si prende un giorno di pausa
	 * 
	 * 
	 * Dopo pausa sceglie un attore a caso
	 * 
	 * 
	 * 
	 * ------ OUTPUT -------
	 * Attori intervistati
	 * numero di giorni di pausa
	 * 
	 * 
	 */
	
	
	
	// Input
	private int N;
	
	
	// Parametri
	private Graph<Actor, DefaultWeightedEdge> grafo;
	private List<Actor> attoriDaIntervistare;
	private List<Actor> intervistati;
	
	
	// Output
	private int giorniPause;
	private Map<Integer, Actor> attoriIntervistati; // mappa giorno - attore
	
	
	// Coda degli eventi
	private PriorityQueue<Event> queue;


	public Simulator(int n, Graph<Actor, DefaultWeightedEdge> grafo) {
		super();
		N = n;
		this.grafo = grafo;
	}
	
	
	public void init() {
		
		// OUTPUT
		this.giorniPause = 0;
		this.attoriIntervistati = new HashMap<>();
		
		
		// Inizializzazione
		this.intervistati = new ArrayList<>();
		this.attoriDaIntervistare = new ArrayList<>(this.grafo.vertexSet());
		this.queue = new PriorityQueue<Event>();
		
		this.queue.add(new Event(EventType.SCELTA, 1, null));
		
	}
	

	public void run() {
		
		while(!this.queue.isEmpty()) {
			
			Event e = this.queue.poll();
			
			int time = e.getTime();
			Actor ultimo = e.getActor();
			
			if(time > this.N) {
				return;
			}
			
			System.out.println(e);
			
			
			switch(e.getType()) {
			case SCELTA:
				
				if(time == 1 || ultimo == null) { // Primo giorno
					
					int pos = (int)Math.random()*this.attoriDaIntervistare.size();
					
					this.queue.add(new Event(EventType.INTERVISTA, time, this.attoriDaIntervistare.get(pos)));
					
					this.attoriDaIntervistare.remove(pos); // Rimuovere da quelli da intervistare --> (intervista una sola volta)
					
				} else { // Dal secondo giorno in poi
					
					double prob = Math.random();
					
					if(prob < 0.6) { // 60%
						
						int pos = (int)Math.random()*this.attoriDaIntervistare.size();
						
						this.queue.add(new Event(EventType.INTERVISTA, time, this.attoriDaIntervistare.get(pos)));
						
						this.attoriDaIntervistare.remove(pos); // Rimuovere da quelli da intervistare --> (intervista una sola volta)
						
					} else { // 40%
						
						Actor raccomandato = this.getRaccomandato(ultimo);
						
						if(raccomandato != null && attoriDaIntervistare.contains(raccomandato)) { // Raccomandato
							
							this.queue.add(new Event(EventType.INTERVISTA, time, raccomandato));	
							this.attoriDaIntervistare.remove(raccomandato); // Rimuovere da quelli da intervistare --> (intervista una sola volta)
							
						} else {
							
							int pos = (int)Math.random()*this.attoriDaIntervistare.size();
							
							this.queue.add(new Event(EventType.INTERVISTA, time, this.attoriDaIntervistare.get(pos)));
							
							this.attoriDaIntervistare.remove(pos); // Rimuovere da quelli da intervistare --> (intervista una sola volta)
							
						}
						
					}
					
					
				}
				
				
				break;
				
			case INTERVISTA:
				
				this.attoriIntervistati.put(time, ultimo);
				this.intervistati.add(ultimo);
				
				if(this.attoriIntervistati.size() > 1) { // Controllare gender per la pausa
					
					System.out.println("Controllo gender");
					
					Actor ultimo1 = this.attoriIntervistati.get(time-1);
					
					if(ultimo1 != null) {
						
						if(ultimo1.getGender().compareTo(ultimo.getGender())==0) { // Gender uguali
							
							double prob = Math.random();
							
							if(prob < 0.9) { // 90% si prende una pausa
								
								this.queue.add(new Event(EventType.PAUSA, time+1, ultimo));
								
							} else {
								
								this.queue.add(new Event(EventType.SCELTA, time+1, ultimo));
								
							}
							
						}
						
					} else {
						
						this.queue.add(new Event(EventType.SCELTA, time+1, ultimo));
						
					}
					
					
					
				} else {
					
					this.queue.add(new Event(EventType.SCELTA, time+1, ultimo));
					
				}
				
				break;
				
			case PAUSA:
				
				this.giorniPause ++;
				
				this.queue.add(new Event(EventType.SCELTA, time+1, null));
				
				break;
				
			}
			
			
			
			
			
		}
	
		
	}
	
	
	public Actor getRaccomandato(Actor ultimo) {
		
		Actor actor = null;
		int peso = 0;
		
		
		for(Actor vicino : Graphs.neighborListOf(this.grafo, ultimo)) {
			
			DefaultWeightedEdge e = this.grafo.getEdge(ultimo, vicino);
			
			if(this.grafo.getEdgeWeight(e) > peso) {
				
				peso = (int)this.grafo.getEdgeWeight(e);
				actor = vicino;
					
			}
			
		}
		
		return actor;
	}


	public int getGiorniPause() {
		return giorniPause;
	}


	public Map<Integer, Actor> getAttoriIntervistati() {
		return attoriIntervistati;
	}
	
	
	

}
