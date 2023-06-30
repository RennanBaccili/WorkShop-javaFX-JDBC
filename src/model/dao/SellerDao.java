package model.dao;

import java.util.List;

import model.entities.Department;
import model.entities.Seller;

public interface SellerDao {
	void insert(Seller obj); // operação responsavel para inserir no banco de dados o department
	void update(Seller obj); // atualizar department
	void deleteById(Integer id);// deletar department
	Seller findById(Integer id); // ele pega o id e consulta o id no banco de dados
	List <Seller> findAll();
	List <Seller> findByDepartment(Department department);
}
