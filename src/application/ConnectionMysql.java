package application;

import java.sql.*;

public class ConnectionMysql {
	public Connection cn = null;
	public static Connection connectionDB() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection cn= DriverManager.getConnection("jdbc:mysql://localhost:3306/gestion_pharmacie","root","");
			System.out.println("connection établie");
			return cn;
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("connection échouée");
			e.printStackTrace();
			return null;
		}
		
		
	}
}
