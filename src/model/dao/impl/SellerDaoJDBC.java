package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbExcepetion;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;
	
	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	@Override
	public void insert(Seller obj) {
		
		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(" INSERT INTO seller "
					+" (Name, Email, BirthDate, BaseSalary, DepartmentId) "
					+" VALUES "
					+" (?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId()); // entro no department e pego o id
		
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
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void update(Seller obj) {

		PreparedStatement st = null;
		
		try {
			st = conn.prepareStatement(" UPDATE seller "
					+ "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
					+ "WHERE Id = ? ");
			
			st.setString(1, obj.getName());
			st.setString(2, obj.getEmail());
			st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
			st.setDouble(4, obj.getBaseSalary());
			st.setInt(5, obj.getDepartment().getId()); // entro no department e pego o id
			st.setInt(6,obj.getId());
		
			st.executeUpdate();
		}catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM seller "+ "WHERE Id = ?");
			
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
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+"FROM seller INNER JOIN department "
					+"ON seller.DepartmentId = department.Id "
					+"WHERE seller.Id = ? ");
		
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Department dep = instantiateDepartment(rs);
				Seller obj = instantiateSeller(rs,dep);// objg dep foi adicionado ao obj seller
				return obj;
			}
			return null;
		} catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Department dep) throws SQLException {
		Seller obj = new Seller();
		obj.setId(rs.getInt("Id"));
		obj.setName(rs.getString("Name"));
		obj.setEmail(rs.getString("Email"));
		obj.setBaseSalary(rs.getDouble("BaseSalary"));
		obj.setBirthDate(rs.getDate("BirthDate"));
		obj.setDepartment(dep); 
		return obj;
	}
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department dep = new Department();
		dep.setId(rs.getInt("DepartmentId"));
		dep.setName(rs.getString("DepName"));
		return dep;
				}
	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "ORDER BY Name ");
		
			rs = st.executeQuery();
			
			//assim eu vou ter só um departamento para varios vendedores
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>(); // map vazio
			while (rs.next()) {
				// vou guardar nesse map qualquer departamento que eu instanciar
				// eu vou no map e tento buscar o departamento que ta sendo buscado
				
				Department dep =map.get(rs.getInt("DepartmentId")); // se exist o if da falso
				
				// se o dep for igual a nulo significa que que ele não existe dentro do map
				// então ele pode ser instaciado pois não quero que se repita a instanciação de departamentos
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs,dep);// objg dep foi adicionado ao obj seller
				list.add(obj); // vou adiconar departamento e seller na list
			}
			return list;
		} catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.Id "
					+ "WHERE DepartmentId = ? "
					+ "ORDER BY Name ");
		
			st.setInt(1, department.getId());
			
			rs = st.executeQuery();
			
			//assim eu vou ter só um departamento para varios vendedores
			
			List<Seller> list = new ArrayList<>();
			Map<Integer, Department> map = new HashMap<>(); // map vazio
			while (rs.next()) {
				// vou guardar nesse map qualquer departamento que eu instanciar
				// eu vou no map e tento buscar o departamento que ta sendo buscado
				
				Department dep =map.get(rs.getInt("DepartmentId")); // se exist o if da falso
				
				// se o dep for igual a nulo significa que que ele não existe dentro do map
				// então ele pode ser instaciado pois não quero que se repita a instanciação de departamentos
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				
				Seller obj = instantiateSeller(rs,dep);// objg dep foi adicionado ao obj seller
				list.add(obj); // vou adiconar departamento e seller na list
			}
			return list;
		} catch (SQLException e) {
			throw new DbExcepetion(e.getMessage());
		}finally{
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
