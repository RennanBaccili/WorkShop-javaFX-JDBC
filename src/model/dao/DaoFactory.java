package model.dao;

import db.DB;
import model.dao.impl.DepartmentDaoJDBC;
import model.dao.impl.SellerDaoJDBC;

public class DaoFactory {
	public static SellerDao createSellerDao() { // A CLASSE VAI EXPOR A INTER
		return new SellerDaoJDBC(DB.getConnection()); //INTERNAMENTE TEM A IMPLEMENTAÇÃO
	}
	public static DepartmentDao createDapartmentDao() {
		return new DepartmentDaoJDBC(DB.getConnection());
	}
}
