package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try { //objeto fml instanciado loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));//carrega a vuew
			ScrollPane scrollPane = loader.load(); // cena principal
			// resolvendo problema do scroll pane
			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);
			
			//cena principal
			Scene mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene); //palco da cena
			primaryStage.setTitle("Sample JavaFX application"); 
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
