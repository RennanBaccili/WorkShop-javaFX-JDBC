package model.services;

import java.util.ArrayList;
import java.util.List;

import model.entities.Department;

public class DepartmentService {

	//função para buscar dados do servidor 
	public List<Department> findAll(){
		
		//dados mockados
		List<Department> list = new ArrayList<>();
		list.add(new Department(1,"books"));
		list.add(new Department(2,"COmputers"));
		list.add(new Department(3,"Eletronics"));
		return list;
	}
}
