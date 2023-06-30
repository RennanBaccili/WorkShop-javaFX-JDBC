package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils { // para acessar o stage que o controler está, se clicar no botão ele pega o stage do botão
	public static Stage currentStage(ActionEvent event) {
		return (Stage) ((Node) event.getSource()).getScene().getWindow();
	}
	//conversor da textField
	public static Integer tryParserToInt(String str) {
		try {
		return Integer.parseInt(str);
		}
		catch (NumberFormatException e) {
			return null;
		}
	}
}

