package application;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import dk.brics.automaton.RegExp;
import dk.brics.automaton.Automaton;



public class GraficoController {
		

	
	@FXML
	private AnchorPane pane_image;
	
	@FXML
	private ImageView automata_image;
	
	

	public GraficoController() {
	     
		initComponents();
	     
	}

	private void initComponents() {
	
        this.automata_image = new ImageView();
        
	}
	


	private Image getImage() throws IOException {
	     BufferedImage img = null;
			
			 img = ImageIO.read(new File("grafo.jpg"));
		     Image fxImage = SwingFXUtils.toFXImage((BufferedImage) img, null);
		     return fxImage;
		
	        
	}
	
		@FXML
		public void setImage() {
		    
		try {
				this.pane_image.getChildren().clear();
		
				this.automata_image = new ImageView(getImage());
				this.pane_image.getChildren().add(this.automata_image);
		
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
}
