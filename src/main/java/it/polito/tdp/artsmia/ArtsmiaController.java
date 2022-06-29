package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenza;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ArtsmiaController {
	
	private Model model ;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola artisti connessi");
    	if(!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	for (Adiacenza a : model.getArtistiConnessi()) {
    		txtResult.appendText("\n"+a.toString());
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Calcola percorso");
    	if(!model.grafoCreato()) {
    		txtResult.setText("Creare un grafo\n");
    		return;
    	}
    	int id;
    	try {
    		id = Integer.parseInt(txtArtista.getText());
    	} catch(NumberFormatException e) {
    		txtResult.setText("Inserire un id intero\n");
    		e.printStackTrace();
    		return;
    	}
    	if (!model.grafoContainsVertex(id)) {
    		txtResult.setText("Artista non presente nel grafo\n");
    		return;
    	}
    	List<Artist> percorso = model.cercaLista(id);
    	txtResult.setText("Artisti coinvolti:\n");
    	for (Artist a : percorso) {
    		txtResult.appendText("    "+a+"\n");
    	}
    	txtResult.appendText("Numero di esposizioni per cui il percorso risulta massimo:\n    "+model.getPesoPercorso(percorso));
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtResult.appendText("Crea grafo");
    	String ruolo = boxRuolo.getValue();
    	if(ruolo==null) {
    		txtResult.setText("Selezionare un ruolo\n");
    		return;
    	}
    	model.creaGrafo(ruolo);
    	txtResult.setText("Grafo creato!\n");
    	txtResult.appendText("# Vertici : " + this.model.nVertici() + "\n");
    	txtResult.appendText("# Archi : " + this.model.nArchi() + "\n");
    }

    public void setModel(Model model) {
    	this.model = model;
    	boxRuolo.getItems().setAll(model.getRuoli());
    }

    
    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }
}
