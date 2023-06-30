package model.services;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentService {

	private DepartmentDao dao = DaoFactory.createDapartmentDao();
	
	//função para buscar dados do servidor 
	public List<Department> findAll(){
		return dao.findAll();
	}
}
