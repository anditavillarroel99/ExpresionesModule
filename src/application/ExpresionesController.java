package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.System;
import dk.brics.automaton.Automaton;
import dk.brics.automaton.RegExp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExpresionesController {

	@FXML
	private Button btn_evaluar;

	@FXML
	private Button btn_grafico;

	@FXML
	private TextField expresion_text;

	@FXML
	private Label evaluar_label;

	@FXML
	private ImageView lenguaje_image;

	@FXML
	private AnchorPane pane_lenguaje;
	
	@FXML
	private VBox v_box;

	private FileWriter archivo = null;
	private PrintWriter pw = null;

	String rutaDot = "C:\\Program Files (x86)\\Graphviz2.38\\bin\\dot.exe"; // Ejecutable para generar graficos

	String textoBase = "grafo1.txt";
	String imagen = "grafo.jpg";
	String parametroT = "-Tjpg";
	String parametroO = "-o";

	public ExpresionesController() {

		initComponents();

	}

	private void initComponents() {

		this.expresion_text = new TextField();
		this.evaluar_label = new Label();
		this.btn_evaluar = new Button();
		// lbEjemploCadena = new javax.swing.JLabel();

	}

	@FXML
	public void btn_evaluar_click() {

		this.pane_lenguaje.getChildren().clear();

		clear();
		borraArchivo(textoBase);
		borraArchivo(imagen);
		String expresion_convertida = getExpresion();
		revisaCadena(expresion_convertida);
		String lenguaje = getCadenas(expresion_convertida);
		String grafo = grafico(expresion_convertida);
		creaTexto(grafo);
		creaImagen();

		LatexFormat format = new LatexFormat();

		this.lenguaje_image = new ImageView(format.getLnguaje(lenguaje));

		this.pane_lenguaje.getChildren().add(this.lenguaje_image);

	}

	@FXML
	public void btn_grafico_click() {

		try {

			AnchorPane root = (AnchorPane) FXMLLoader.load(getClass().getResource("Graficos.fxml"));
			Scene sceneLambda = new Scene(root);
			Stage stage = new Stage();

			sceneLambda.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setResizable(true);
			stage.setScene(sceneLambda);
			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void clear() {
		this.evaluar_label.setText("");
		this.evaluar_label.setVisible(false);
		archivo = null;
		pw = null;
	}

	private String getExpresion() {

		String expresion_regular = "";
		expresion_regular = this.expresion_text.getText();
		return expresion_regular;
	}

	private void revisaCadena(String expresion) {
		String mensaje = "";

		if (expresion.length() == 0)
			mensaje = "La Expresion Regular representa al conjunto vacio";

		else {
			for (int i = 0; i < expresion.length(); i++) {
				char token = expresion.charAt(i);

				if (token == ' ') {
					mensaje = "La Expresion Regular representa un Automata finito No Determinista - lambda";
					break;
				} else {
					mensaje = "La Expresion Regular representa un Automata finito Determinista";
				}

			}
		}

		this.evaluar_label.setText(mensaje);
		this.evaluar_label.setVisible(true);

	}

	private String grafico(String expresion) {

		RegExp exp_reg = new RegExp(expresion);
		Automaton aut = exp_reg.toAutomaton();
		String grafo = aut.toDot();

		return grafo;
	}

	public void creaTexto(String grafo) {

		// char c = '\u03BB';
		String text = " lamba";

		try {
			archivo = new FileWriter("grafo1.txt");
			pw = new PrintWriter(archivo);
			grafo = grafo.replace("u0020", text);
			// grafo = grafo.replace("u0020", "\u03BB" );
			pw.write(grafo);
			archivo.close();
			grafo = "";
		} catch (IOException e) {
			System.err.println("ERROR en archivo: " + e.toString());
		}

	}

	public void creaImagen() {

		try {

			String[] cmd = new String[5];

			cmd[0] = rutaDot;
			cmd[1] = parametroT;
			cmd[2] = textoBase;
			cmd[3] = parametroO;
			cmd[4] = imagen;

			Runtime rt = Runtime.getRuntime();

			rt.exec(cmd);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void borraArchivo(String path) {

		File archivo = new File(path);
		archivo.delete();

	}

	public String getCadenas(String expresion) {

		char coma = ',';
		String ejemplo = "L = ";
		boolean verificador = false;

		this.v_box.setVisible(true);

		if (expresion.length() == 0)
			ejemplo += " \\emptyset ";

		else {
			ejemplo += "\\{";
			if (expresion.length() == 1 && expresion.charAt(0) == ' ')
				ejemplo += "\\lambda \\}";

			else {
				for (int i = 0; i < expresion.length(); i++) {

					char token = expresion.charAt(i);

					if (token == '*' || token == '+')
						verificador = true;

				}

				if (!verificador) {

					ejemplo += expresion;
					ejemplo = ejemplo.replace(" ", "");
					ejemplo = ejemplo.replace('|', ',');
					ejemplo += "  \\}";

				} else
					ejemplo += clausuras(expresion);
			}
		}
		return ejemplo;
	}

	public String clausuras(String cadena) {

		String kleene = "";
		Boolean parentesis = false;
		String resultado = "";
		int index = 0;
		int contador = 0;

		if (cadena.length() == 2) {
			String repetidos = cadena.charAt(0) + " , " + cadena.charAt(0) + cadena.charAt(0) + " , ... \\}";

			if (cadena.charAt(1) == '*')
				resultado += "\\lambda , " + repetidos;

			else {
				if (cadena.charAt(1) == '+')
					resultado = repetidos;
			}
		} else {
				this.v_box.setVisible(false);
			// cadena = cadena.replace("+", "");

			cadena = cadena.replace(" ", "");
		}
		return resultado;
	}

}
