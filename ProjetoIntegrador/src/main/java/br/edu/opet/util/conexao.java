package br.edu.opet.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class conexao {

	public static Connection getConnection(boolean autoCommit) throws SQLException {
		
		String url = "jdbc:sqlserver://191.177.248.100;databaseName=ProjetoPI";
		String user = "sa";
		String password = "senha123";
		
		Connection conn = DriverManager.getConnection(url, user, password);
		conn.setAutoCommit(false);
		return conn;
	}
	
}
