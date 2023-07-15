/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Actor;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.SimulationResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimili"
    private Button btnSimili; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimulazione"
    private Button btnSimulazione; // Value injected by FXMLLoader

    @FXML // fx:id="boxGenere"
    private ComboBox<String> boxGenere; // Value injected by FXMLLoader

    @FXML // fx:id="boxAttore"
    private ComboBox<Actor> boxAttore; // Value injected by FXMLLoader

    @FXML // fx:id="txtGiorni"
    private TextField txtGiorni; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doAttoriSimili(ActionEvent event) {

    	this.txtResult.clear();

        
    	if(!this.model.isGrafoLoaded()) {
    		
    		this.txtResult.setText("Crea grafo prima!");
    		return;
    		
    	}
    	
    	Actor actor = this.boxAttore.getValue();
    	
    	if(actor == null) {
    		
    		this.txtResult.setText("Scegli un attore prima!");
    		return;
    	}
    	
    	List<Actor> simili = this.model.getSimili(actor);
    	
    	this.txtResult.appendText("ATTORI SIMILI A: " + actor + "\n");
    	
    	for(Actor a : simili) {
    		
    		this.txtResult.appendText(a + "\n");
    		
    	}
    	
    	
    	
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	this.txtResult.clear();
        
    	String genere = this.boxGenere.getValue();
    	
    	if(genere == null) {
    		this.txtResult.setText("Scegli un genere!");
    		return;
    		
    	}
    	
    	
    	this.model.creaGrafo(genere);
    	
    	
    	// VERSIONE A CAPO
    	this.txtResult.appendText("Grafo creato! \n");
    	this.txtResult.appendText("# Vertici: " + this.model.getNNodes() + "\n");
    	this.txtResult.appendText("# Archi: " + this.model.getNArchi() + "\n");
        
    	
    	this.boxAttore.getItems().clear();
    	this.boxAttore.getItems().addAll(this.model.getActors());
    	
    	
    }

    @FXML
    void doSimulazione(ActionEvent event) {
    	
    	this.txtResult.clear();
    	
    	String input = this.txtGiorni.getText();
    	
    	int giorni = 0;
    	
    	try {
    		
    		giorni = Integer.parseInt(input);
    	
    	} catch(NumberFormatException e ) {
    		
    		this.txtResult.setText("Inserisci un valore numerico al numero di giorni!");
    		return;
    		
    	}
    	
    	SimulationResult sim = this.model.simula(giorni);
    	
    	this.txtResult.appendText(sim.toString() + "\n");

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimili != null : "fx:id=\"btnSimili\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimulazione != null : "fx:id=\"btnSimulazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxGenere != null : "fx:id=\"boxGenere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert boxAttore != null : "fx:id=\"boxAttore\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtGiorni != null : "fx:id=\"txtGiorni\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	
    	this.boxGenere.getItems().addAll(this.model.getGeneri());
    }
}
