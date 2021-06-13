package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.opet.entity.model.Login;
import br.edu.opet.entity.model.Usuario;
import br.edu.opet.util.conexao;

public class LoginDAO {
	
	protected boolean buscarUsuario(Login login) {
		Connection conn = null;
		PreparedStatement stmt = null;
        boolean status = false;
				
		try {
			conn = conexao.getConnection(false);
						
			stmt = conn. prepareStatement("SELECT Idf_Usuario, Email_Pessoa, Senha, "
					+ "Idf_Tipo_Usuario from PI_Usuarios where Email_Pessoa = ? and Senha = ?");
					
			stmt.setString(1,login.getEmail());
			stmt.setString(2,login.getSenha());

			ResultSet rs = stmt.executeQuery();
			status = rs.next();	
			
			if(status) {
				Usuario us = new Usuario();
				us.setIdf_Tipo_Usuario(rs.getInt("Idf_Tipo_Usuario"));
				us.setIdf_Usuario(rs.getInt("Idf_Usuario"));	
				login.setUsuario(us);
			}
			
			rs.close();
			stmt.close();
			conn.close();
		
			return status;

		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return status;
			}

		}
		
		return status;	
	}

}
