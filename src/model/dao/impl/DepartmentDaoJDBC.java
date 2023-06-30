package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbExcepetion;
import model.dao.DepartmentDao;
import model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao{

	private Connection conn;
	
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement("INSERT INTO department "
					+ "(Id, Name) "
					+ "VALUES "
					+ "(?,?) ",Statement.RETURN_GENERATED_KEYS);
			
			st.setInt(1, obj.getId());
			st.setString(2, obj.getName());
			
			int rowsAffected = st.executeUpdate(); 
			
			if (rowsAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if(rs.next()) { // se ele existir
					int id = rs.getInt(1);
					obj.setId(id);
				}
				DB.closeResultSet(rs);
			}
			else {
				throw new DbExcepetion("Erro! No rows Affected!");
			}
			System.out.println("Inserção concluida");
			
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}
		
	}

	@Override
	public void update(Department obj) {
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(
					"UPDATE department " +
					"SET Name = ? " +
					"WHERE Id = ?");
			
			st.setString(1, obj.getName());
			st.setInt(2, obj.getId());
				
			st.executeUpdate();
			
			
		}catch (Exception e) {
			throw new DbExcepetion(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}
	
	@Override
	public void deleteById(Integer id) {
		
		
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department "+ "WHERE Id = ?");
			
			st.setInt(1, id);
			
			int rows = st.executeUpdate();
			// caso escolha uma linha que não existe
			if (rows == 0) {
				throw new DbExcepetion("Linha escolhida não existe");
			}
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) { // metodo de procurar por id
		
		PreparedStatement st = null; //prepare statment é para estanciar codigo sql
		ResultSet rs = null;
		
		try {
			st = conn.prepareStatement("select * from department "
					+ "WHERE Id = ? ");
			st.setInt(1, id); // id que vou passar como argumento
			rs = st.executeQuery();
			
			if (rs.next()) {
				Department obj = instantiateDepartment(rs);
				return obj;
			}
			return null;
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
		
		
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("id"));
		dep.setName(rs.getString("name"));
		return dep;
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("select * from department");
		
			rs = st.executeQuery();
			List<Department> list = new ArrayList<>();
			while(rs.next()) {
				Department obj = instantiateDepartment(rs);
				list.add(obj);
			}
			return list;
			
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		} finally {
			DB.closeResultSet(rs);
			DB.closeStatement(st);
		}

	}

}
