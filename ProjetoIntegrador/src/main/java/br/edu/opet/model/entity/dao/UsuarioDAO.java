package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.opet.entity.Endereco;
import br.edu.opet.entity.Usuario;
import br.edu.opet.util.conexao;

public class UsuarioDAO{
	
	public UsuarioDAO() {
		
	}
		
	protected ArrayList<Usuario> listar() {
		
		ArrayList<Usuario> alUsuario = new ArrayList<Usuario>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
					("SELECT Idf_Usuario"
					+ ",Num_CPF"
					+ ",Nme_Pessoa"
					+ ",PIS.Desc_Sexo"
					+ ",PIC.Nme_Cidade"
					+ ",PIE.Des_Logradouro"
					+ ",PIE.Num_Endereco"
					+ ",PIE.Num_CEP"
					+ ",PIU.Dta_Nascimento"
					+ ",Num_DDD_Celular"
					+ ",Num_Celular"
					+ ",Email_Pessoa"
					+ ",PIU.Dta_Cadastro"
					+ ",Idf_Estado_Civil"
					+ ",PIU.Flg_Inativo"
					+ ",Idf_Tipo_Usuario"
					+ " FROM PI_Usuarios PIU "
					+ " JOIN PI_Cidade PIC ON PIC.Idf_Cidade = PIU.Idf_Cidade "
					+ " JOIN PI_Sexo PIS ON PIS.Idf_Sexo = PIU.Idf_Sexo "
					+ " JOIN PI_Endereco PIE ON PIE.Idf_Endereco = PIU.Idf_Endereco "
					+ " WHERE PIU.Flg_Inativo = 0");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Usuario us = new Usuario();	
				
				us.setIdf_Usuario(rs.getInt("Idf_Usuario"));
				us.setNum_CPF(rs.getString("Num_CPF"));
				us.setNme_Pessoa(rs.getString("Nme_Pessoa"));
				us.setDta_NascimentoDate(rs.getDate("Dta_Nascimento"));
				us.setNum_DDD_Celular(rs.getString("Num_DDD_Celular"));
				us.setNum_Celular(rs.getString("Num_Celular"));
				us.setEml_Pessoa(rs.getString("Email_Pessoa"));
				
				alUsuario.add(us);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alUsuario;
			
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
		return alUsuario;	
	}
	
	protected boolean inserir(Usuario us, Endereco end) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(false);
			
			if(end.inserir(end, conn)) {
				
				stmt = conn. prepareStatement(
						"INSERT INTO PI_Usuarios "
						+ "(Num_CPF"
						+ ",Nme_Pessoa"
						+ ",Idf_Sexo"
						+ ",Idf_Cidade"
						+ ",Dta_Nascimento"
						+ ",Idf_Endereco"
						+ ",Num_DDD_Celular"
						+ ",Num_Celular"
						+ ",Email_Pessoa"
						+ ",Dta_Cadastro"
						+ ",Idf_Estado_Civil"
						+ ",Flg_Inativo"
						+ ",Idf_Tipo_Usuario)"
						+ " VALUES"
						+ " (?,?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,0,1)");
						
				stmt.setString(1,us.getNum_CPF());
				stmt.setString(2,us.getNme_Pessoa());
				stmt.setInt(3,us.getIdf_Sexo());
				stmt.setInt(4,end.getIdf_Cidade());
				java.sql.Date sqlDate = new java.sql.Date(us.getDta_NascimentoDate().getTime());
				stmt.setDate(5, sqlDate);	
				stmt.setInt(6,end.getIdf_Endereco());
				stmt.setString(7,us.getNum_DDD_Celular());
				stmt.setString(8,us.getNum_Celular());
				stmt.setString(9,us.getEml_Pessoa());
				stmt.setInt(10,us.getIdf_Estado_Civil());

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
	
	protected boolean atualizar(Usuario us) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(false);
			
			Endereco end = new Endereco();
			if(end.atualizarEndereco(conn)) {
				
				stmt = conn. prepareStatement(
						"UPDATE PIU.Nme_Pessoa, PIU.Idf_Sexo, PIU.Idf_Cidade, PIU.Dta_Nascimento, "
					  + "FROM PI_Usuarios PIU WHERE PIU.Idf_Usuario = ?");
										
				stmt.setString(2,us.getNme_Pessoa());
				stmt.setInt(3,us.getIdf_Sexo());
				stmt.setInt(4,us.getIdf_Cidade());
				stmt.setDate(5, (Date) us.getDta_NascimentoDate());
				stmt.setInt(6,us.getIdf_Endereco());
				stmt.setString(7,us.getNum_DDD_Celular());
				stmt.setString(8,us.getNum_Celular());
				stmt.setString(9,us.getEml_Pessoa());
				stmt.setInt(10,us.getIdf_Estado_Civil());

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
			}
			else {
				conn.rollback();
				conn.close();
				return false;
			}		
		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}

		}
		return false;	
	}
	
	protected boolean deletar(Usuario us) {
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(false);			
			stmt = conn.prepareStatement(
					"update pi_usuarios set flg_inativo = 1 where Idf_Usuario = ?");
					
			stmt.setInt(1,us.getIdf_Usuario());
			
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
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}

		}
		return false;
		
	}
	
}
