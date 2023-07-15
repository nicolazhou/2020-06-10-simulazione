package it.polito.tdp.imdb.model;

import java.util.Objects;

public class Event implements Comparable<Event> {

	public enum EventType {
		
		INTERVISTA,
		PAUSA,
		SCELTA
		
	}
	
	private EventType type;
	private Integer time;
	private Actor actor;
	public Event(EventType type, int time, Actor actor) {
		super();
		this.type = type;
		this.time = time;
		this.actor = actor;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public Actor getActor() {
		return actor;
	}
	public void setActor(Actor actor) {
		this.actor = actor;
	}
	@Override
	public int hashCode() {
		return Objects.hash(actor, time, type);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(actor, other.actor) && time == other.time && type == other.type;
	}
	
	
	
	@Override
	public String toString() {
		return "Event [type=" + type + ", time=" + time + ", actor=" + actor + "]";
	}
	
	
	
	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.time.compareTo(o.getTime());
	}
	
	
}
