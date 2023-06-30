package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemSeller; // variavel menu
	@FXML
	private MenuItem menuItemDepartment;
	@FXML
	private MenuItem menuItemAbout;
	
	@FXML
	private void onMenuItemSellerAction() { // eventos
		System.out.println("onMenuItemSellerAction");
	}
	@FXML
	private void onMenuItemDepartmentAction() { // eventos
		loadView2("/gui/DepartmentList.fxml");
	}
	@FXML
	private void onMenuItemAboutAction() { // eventos
		loadView("/gui/About.fxml");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	// função loadView recebe o nome da da view o caminho completo do nome
	private synchronized void loadView(String absoluteName) {
		try {
		//padrão da classe
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); 
		VBox mainVBox =(VBox)((ScrollPane)mainScene.getRoot()).getContent(); // metodo para pegar o primeiro elemento da view
		//buscando o ScrollPane
		Node mainMenu = mainVBox.getChildren().get(0); // o primeiro filho da janela principal
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu);
		mainVBox.getChildren().addAll(newVBox.getChildren());
		
		 
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
	
	private synchronized void loadView2(String absoluteName) {
		try {
		//padrão da classe
		FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
		VBox newVBox = loader.load();
		
		Scene mainScene = Main.getMainScene(); 
		VBox mainVBox =(VBox)((ScrollPane)mainScene.getRoot()).getContent(); // metodo para pegar o primeiro elemento da view
		//buscando o ScrollPane
		Node mainMenu = mainVBox.getChildren().get(0); // o primeiro filho da janela principal
		mainVBox.getChildren().clear();
		mainVBox.getChildren().add(mainMenu);
		mainVBox.getChildren().addAll(newVBox.getChildren());
		//uso o loader para pegar a referencia do controller
		DepartmentListController controller = loader.getController();
		controller.setDepartmentService(new DepartmentService());
		controller.updateTableView();
		 
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Loading view", e.getMessage(), AlertType.ERROR);
		}
	}
}
