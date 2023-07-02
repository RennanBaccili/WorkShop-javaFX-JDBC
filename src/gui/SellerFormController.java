package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;

public class SellerFormController implements Initializable{
	//instanciando Seller
	private Seller entity;
	//instanciando DepService
	private SellerService service;
	//lista de objetos para receber eventos
	private List<DataChangeListener> dataChangeListener = new ArrayList<>(); // subject
	
	@FXML
	private TextField txtId;
	
	@FXML
	private TextField txtName;
	
	@FXML
	private Label labelErrorName;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	//Controlador vai ter uma instancia do departamento
	public void setSeller(Seller entity) {
		this.entity = entity;
	}
	
	//instanciando DepService
	public void setSellerService(SellerService service) {
		this.service = service;
	}
	// objetos que implementam a class podem se inscrever nessa lista de evento
	public void subscribeDataChangeListenner(DataChangeListener listener) { 
		dataChangeListener.add(listener);
	}
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entiy was null");
		}
		if(service == null) {
			throw new IllegalStateException("Service was null");
		}
		try {
			entity = getFormDate(); //responsavel por pegar os dados do formulario
			service.saveOrUpdate(entity); // quando for salvar a entity
			notifyDataChangeListeners(); // ele notifica o evento
			Utils.currentStage(event).close();
		}catch (ValidationException e) {
			setErrorMessages(e.getErrors());
		}catch (DbException e) {
			Alerts.showAlert("Error saving object", null, e.getMessage(), AlertType.ERROR);
		}
	}
	//execulta o onDataChanged
	private void notifyDataChangeListeners() { // vou emitir o evento para os listeners
		for(DataChangeListener listener :dataChangeListener) {
			listener.onDataChanged(); // evento para observer
		}
	}

	private Seller getFormDate() {
		Seller obj = new Seller();
		ValidationException exception = new ValidationException("Validation Error");
		
		obj.setId(Utils.tryParserToInt(txtId.getText()));
		
		//verificação do campo name
		if(txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("name", "Field can't be empty");
		}
		
		obj.setName(txtName.getText());
		
		if(exception.getErrors().size()>0) {
			throw exception;
		}
		
		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();

	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 30);
	}
	//vou ter que adicionar o itens que estão na entid department aqui na upadte form
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("Entity was null");
		}
		// converster a string para Id
		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		
	}
	//mensagem de erro
	private void setErrorMessages(Map<String,String> errors) {
		Set<String> fields = errors.keySet();
		
		if (fields.contains("name")) {
			labelErrorName.setText(errors.get("name"));
		}
	}
}
