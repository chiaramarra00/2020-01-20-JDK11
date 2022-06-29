package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
    private ArtsmiaDAO dao;
	
	private Graph<Artist,DefaultWeightedEdge> grafo;
	private Map<Integer,Artist> idMap;
	
	
	private List<Artist> listaMigliore;
	
	public List<Artist> cercaLista(int id){
		Artist a = idMap.get(id);
		List<Artist> artistiValidi = Graphs.neighborListOf(grafo, a);
		
		List<Artist> parziale = new ArrayList<>();
		listaMigliore = new ArrayList<>();
		parziale.add(a);
		
		cerca(parziale,artistiValidi);
		
		return listaMigliore;
	}
	
	private void cerca(List<Artist> parziale, List<Artist> artistiValidi) {
	
		//controllo soluzione migliore
		if(parziale.size() > listaMigliore.size()) {
			listaMigliore = new ArrayList<>(parziale);
		}
		
		for(Artist a : artistiValidi) {
			if (parziale.size()==1) {
				parziale.add(a);
				cerca(parziale, Graphs.neighborListOf(grafo, a));
				parziale.remove(parziale.size()-1);
			}
			else if(!parziale.contains(a) && grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), a))==grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-2), parziale.get(parziale.size()-1)))) {
				parziale.add(a);
				cerca(parziale, Graphs.neighborListOf(grafo, a));
				parziale.remove(parziale.size()-1);
			}
		}
		
		
	}
	
	public Model() {
		dao = new ArtsmiaDAO();
		idMap = new HashMap<>();
		
		this.dao.listArtists(idMap);
	}
	
	public void creaGrafo(String role) {
		//creo il grafo
		this.grafo = 
				new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, 
				this.dao.getVertici(role, this.idMap));
		
		//aggiungo gli archi
		for(Adiacenza a : this.dao.getArchi(role, idMap)) {
			Graphs.addEdgeWithVertices(this.grafo, a.getA1(), 
					a.getA2(), a.getPeso());
		}
		
	}
	
	public List<Artist> getVertici(){
		return new ArrayList<>(this.grafo.vertexSet());
	}
	
	public boolean grafoCreato() {
		if(this.grafo == null)
			return false;
		else 
			return true;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getArtistiConnessi() {
		List<Adiacenza> result = new ArrayList<Adiacenza>();
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			int peso = (int) this.grafo.getEdgeWeight(e);
				result.add(new Adiacenza(this.grafo.getEdgeSource(e),
						this.grafo.getEdgeTarget(e), peso)); 
		}
		Collections.sort(result);
		return result;
	}
	
	public List<String> getRuoli(){
		return dao.getAllRoles();
	}

	public boolean grafoContainsVertex(int id) {
		if (!grafo.vertexSet().contains(idMap.get(id)))
			return false;
		return true;
	}

	public int getPesoPercorso(List<Artist> percorso) {
		return (int)grafo.getEdgeWeight(grafo.getEdge(percorso.get(0), percorso.get(1)));
	}
	
	
	
	
	
	

}
