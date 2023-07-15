package it.polito.tdp.imdb.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.imdb.db.ImdbDAO;


public class Model {

	private ImdbDAO dao;
	
	private List<String> generi;
	
	private Graph<Actor, DefaultWeightedEdge> grafo;
	
	private List<Actor> vertici;
	private Map<Integer, Actor> mappaAttori;
	
	private List<Arco> archi;
	
	
	public Model() {
		
		this.dao = new ImdbDAO();
		
		this.generi = new ArrayList<>(this.dao.listAllGenre());
		
	}
	
	
	
	
	public List<String> getGeneri() {
		return generi;
	}




	public void creaGrafo(String genre) {
		
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		
		this.vertici = new ArrayList<>(this.dao.getVertici(genre));
		
		this.mappaAttori = new HashMap<>();
		for(Actor a : this.vertici) {
			
			this.mappaAttori.put(a.getId(), a);
			
		}
		
		
		// Vertici:
		Graphs.addAllVertices(this.grafo, this.vertici);

		
		
		// Archi
		this.archi = new ArrayList<>(this.dao.getArchi(genre, mappaAttori));
		
		for(Arco a : this.archi) {
			
			Graphs.addEdgeWithVertices(this.grafo, a.getActor1(), a.getActor2(), a.getPeso());
			
		}
		
		
	}
	
	
	public int getNNodes() {
		return this.grafo.vertexSet().size();
	}
	
	public int getNArchi() {
		return this.grafo.edgeSet().size();
	}
	
	
	public boolean isGrafoLoaded() {
		
		if(this.grafo == null)
			return false;
		
		return true;
	}



	
	
	public List<Actor> getActors() {
		List<Actor> actors = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(actors, new Comparator<Actor>() {

			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.lastName.compareTo(o2.lastName);
			}
			
		});
		return actors;
	}
	
	
	public List<Actor> getSimili(Actor actor) {
		
		ConnectivityInspector<Actor, DefaultWeightedEdge> ci = new ConnectivityInspector<Actor, DefaultWeightedEdge>(this.grafo);
		
		List<Actor> result = new ArrayList<>(ci.connectedSetOf(actor));
		
		Collections.sort(result, new Comparator<Actor>() {

			@Override
			public int compare(Actor o1, Actor o2) {
				return o1.lastName.compareTo(o2.lastName);
			}
			
		});
		
		result.remove(actor);
		
		return result;
	}
	
	
	public SimulationResult simula(int giorni) {
		
		Simulator sim = new Simulator(giorni, this.grafo);
		
		sim.init();
		sim.run();
		
		
		
		return new SimulationResult(sim.getAttoriIntervistati(), sim.getGiorniPause());
		
	}
	

}
