package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;


public class Main extends Application {
	
	@Override
	public void start(Stage stage) {

		try {

					
			AnchorPane root = (AnchorPane)FXMLLoader.load(getClass().getResource("Expresiones.fxml"));
			Scene scene= new Scene(root);		
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());		
			stage.setResizable(false);
			stage.setScene(scene);
			stage.show();
				
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);

	}
	
}
