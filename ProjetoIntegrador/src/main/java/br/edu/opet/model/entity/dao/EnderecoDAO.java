package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.opet.entity.model.Endereco;
import br.edu.opet.util.conexao;

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
	protected boolean atualizar(Endereco end, Connection conn) {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(
				    "update PI_Endereco set "
				    + " Num_CEP = ?"
				    + ",Des_Logradouro = ? "
				    + ",Num_Endereco = ? "
				    + ",Des_Complemento = ? "
				    + ",Des_Bairro = ? "
				    + ",Idf_Cidade = ? "
				    + " where Idf_Endereco = ? ");
			
			stmt.setString(1,end.getNum_CEP());
			stmt.setString(2,end.getDesc_Logradouro());
			stmt.setString(3,end.getNum_Endereco());
			stmt.setString(4,end.getDesc_Complemento());
			stmt.setString(5,end.getDes_Bairro());
			stmt.setInt(6,end.getIdf_Cidade());
			stmt.setInt(7,end.getIdf_Endereco());

			int rowAffected = stmt.executeUpdate();
			
			if(rowAffected == 1){				
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
				System.err.println(e1);
				return false;
			}
		}
		return false;		
		
	}
	protected ArrayList<Endereco> listar() {
		
		ArrayList<Endereco> alEndereco = new ArrayList<Endereco>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
					 ("SELECT "
					+ "PIE.Idf_Endereco,"
					+ "PIE.Num_CEP,"
					+ "PIE.Des_Logradouro,"
					+ "PIE.Num_Endereco,"
					+ "PIE.Des_Complemento,"
					+ "PIE.Des_Bairro,"
					+ "PIE.Idf_Cidade"
					+ " FROM PI_Endereco PIE");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Endereco end = new Endereco();	
				
				end.setIdf_Endereco(rs.getInt("Idf_Endereco"));
				end.setNum_CEP(rs.getString("Num_CEP"));
				end.setDesc_Logradouro(rs.getString("Des_Logradouro"));
				end.setNum_Endereco(rs.getString("Num_Endereco"));
				end.setDes_Bairro(rs.getString("Des_Bairro"));	
				end.setDesc_Complemento(rs.getString("Des_Complemento"));				
				end.setIdf_Cidade(rs.getInt("Idf_Cidade"));

				alEndereco.add(end);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alEndereco;
			
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return null;
			}

		}
		return alEndereco;	
	}


}
