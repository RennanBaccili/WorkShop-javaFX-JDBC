package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try { //objeto fml instanciado loader
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));//carrega a vuew
			Parent parent = loader.load();
			
			//cena principal
			Scene mainScene = new Scene(parent);
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
