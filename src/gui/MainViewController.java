package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

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
		System.out.println("onMenuItemDepartmentAction");
	}
	@FXML
	private void onMenuItemAboutAction() { // eventos
		System.out.println("onMenuItemAboutAction");
	}
	
	
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
		// TODO Auto-generated method stub
		
	}

}
