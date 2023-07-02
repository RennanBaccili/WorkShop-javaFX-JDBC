package model.exceptions;

import java.util.HashMap;
import java.util.Map;

public class ValidationException extends RuntimeException {
	//uso do map para guardar os erros possiveis dos campos, nome do campo e mensagem de erro
	private Map<String,String> errors = new HashMap<>();
	
	private static final long serialVersionUID = 1L;
	//exceção para validar um formulario
	public ValidationException(String msg) {
		super(msg);
	}
	
	public Map<String,String> getErrors(){
		return errors;
	}
	public void addError(String fieldName,String erroMessage) {
		errors.put(fieldName, erroMessage);
	}
}
