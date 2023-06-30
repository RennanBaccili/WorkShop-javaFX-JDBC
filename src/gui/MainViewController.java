package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
	private void onMenuItemDepartmentAction() { // ação de inicialização do controle
		loadView("/gui/DepartmentList.fxml", (DepartmentListController controller) -> {
			controller.setDepartmentService(new DepartmentService());
			controller.updateTableView();
		});
	}
	@FXML
	private void onMenuItemAboutAction() { // eventos
		loadView("/gui/About.fxml", x ->{});
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}
	// função loadView recebe o nome da da view o caminho completo do nome
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializingAction) {
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
		
		//função para execultar o controle passado na função
		T controller = loader.getController();
		initializingAction.accept(controller);
		/*forma feita antes de apagar loadView2
		 * uso o loader para pegar a referencia do controller
		DepartmentListController controller = loader.getController();
		controller.setDepartmentService(new DepartmentService());
		controller.updateTableView();
		*/ 
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Error Loading view", e.getMessage(), AlertType.ERROR);
		}
	}
	
}
