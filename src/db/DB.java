package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB { // CLASSE PARA CONNECTAR E DESCONECTAR COM O BANCO DE DADOS
	
	private static Connection conn = null;
	
	public static Connection getConnection() {
		if(conn == null) {
			try {
			Properties props = loadProperties(); // peguei o load properties la de baixo
			String url = props.getProperty("dburl");// propriedades
			conn = DriverManager.getConnection(url, props); //instanciamos a conexão no objeto conn
			} catch (SQLException e) {
				throw new DbExcepetion(e.getMessage());
			}
		}
		return conn;
		
	}
	public static void closeConnection() {
		if(conn!= null){
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DbExcepetion(e.getMessage());
			}
			
		}
	}
	
	private static Properties loadProperties() {
		try (FileInputStream fs = new FileInputStream("db.properties")){
			Properties props = new Properties();
			props.load(fs);
			return props;
		} catch (IOException e) {
			throw new DbExcepetion(e.getMessage());
		}
	}
	public static void closeStatement(Statement st) {
		if(st != null) {
			try {
				st.close();
			} catch (SQLException e) {
				throw new DbExcepetion(e.getMessage());
			}
		}
	}
	public static void closeResultSet(ResultSet rs) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DbExcepetion(e.getMessage());
			}
		}
	}
}
