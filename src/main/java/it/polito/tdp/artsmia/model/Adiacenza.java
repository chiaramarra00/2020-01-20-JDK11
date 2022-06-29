package it.polito.tdp.artsmia.model;

public class Adiacenza implements Comparable<Adiacenza>{
	
	private Artist a1;
	private Artist a2;
	private int peso;
	
	public Adiacenza(Artist a1, Artist a2, int peso) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso;
	}

	public Artist getA1() {
		return a1;
	}

	public Artist getA2() {
		return a2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public String toString() {
		return a1 + " - " + a2 + " (" + peso +")";
	}

	@Override
	public int compareTo(Adiacenza o) {
		return o.peso-peso;
	}
	
	
}
