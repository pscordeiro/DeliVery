package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.edu.opet.entity.Endereco;

public class EnderecoDAO  {
	
	protected boolean inserir(Endereco end, Connection conn){
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn. prepareStatement(
				"INSERT INTO PI_Endereco "
				+ "(Num_CEP"
				+ ",Des_Logradouro"
				+ ",Num_Endereco"
				+ ",Des_Complemento"
				+ ",Des_Bairro"
				+ ",Idf_Cidade"
				+ ",Flg_Inativo"
				+ ",Dta_Cadastro)"
				+ " VALUES(?,?,?,?,?,?,0,CURRENT_TIMESTAMP)",
			Statement.RETURN_GENERATED_KEYS);
				
			stmt.setString(1,end.getNum_CEP());
			stmt.setString(2,end.getDesc_Logradouro());
			stmt.setString(3,end.getNum_Endereco());
			stmt.setString(4,end.getDesc_Complemento());
			stmt.setString(5,end.getDes_Bairro());
			stmt.setInt(6,end.getIdf_Cidade());
		
			int rowAffected = stmt.executeUpdate();
			
			if(rowAffected == 1){
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int id = rs.getInt(1);
				end.setIdf_Endereco(id);
				stmt.close();
				return true;
			}
			else {
				conn.rollback();
				stmt.close();
				return false;
			}
			
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
			} catch (SQLException e1) {
				return false;
			}
		}
		return false;	
	}
	protected boolean atualizar(Connection conn, Endereco end) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
				    "update end set "
				    + "end.Num_CEP = ?"
				    + ",end.Des_Logradouro = ? "
				    + ",end.Num_Endereco = ? "
				    + ",end.Des_Complemento = ? "
				    + ",end.Des_Bairro = ? "
				    + ",end.Idf_Cidade = ? "
				    + "from PI_Endereco as end "
				    + "where end.Idf_Endereco = ? ");
			
			stmt.setString(2,end.getNum_CEP());
			stmt.setString(3,end.getNum_Endereco());
			stmt.setString(4,end.getDesc_Complemento());
			stmt.setString(5,end.getDes_Bairro());
			stmt.setInt(6,end.getIdf_Cidade());
			stmt.setInt(7,end.getIdf_Endereco());

			int rowAffected = stmt.executeUpdate();
			
			if(rowAffected == 1){				
				conn.commit();
				stmt.close();
				conn.close();
				return true;
			}
			else {
				conn.rollback();
				stmt.close();
				conn.close();
				return false;
			}										
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}
		}
		return false;		
		
	}
}
