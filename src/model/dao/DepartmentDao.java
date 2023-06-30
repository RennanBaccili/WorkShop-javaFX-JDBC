package model.dao;

import java.util.List;

import model.entities.Department;

public interface DepartmentDao { // criação da interface dao

	void insert(Department obj); // operação responsavel para inserir no banco de dados o department
	void update(Department obj); // atualizar department
	void deleteById(Integer id);// deletar department
	Department findById(Integer id); // ele pega o id e consulta o id no banco de dados
	List <Department> findAll();
}
