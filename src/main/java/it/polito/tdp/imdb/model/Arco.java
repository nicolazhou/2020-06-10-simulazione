package it.polito.tdp.imdb.model;

public class Arco {

	private Actor actor1;
	private Actor actor2;
	private Integer peso;
	
	
	public Arco(Actor actor1, Actor actor2, Integer peso) {
		super();
		this.actor1 = actor1;
		this.actor2 = actor2;
		this.peso = peso;
	}


	public Actor getActor1() {
		return actor1;
	}


	public void setActor1(Actor actor1) {
		this.actor1 = actor1;
	}


	public Actor getActor2() {
		return actor2;
	}


	public void setActor2(Actor actor2) {
		this.actor2 = actor2;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
	
	
	
}
