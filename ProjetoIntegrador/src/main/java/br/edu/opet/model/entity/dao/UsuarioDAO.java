package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.edu.opet.entity.model.Endereco;
import br.edu.opet.entity.model.Usuario;
import br.edu.opet.util.conexao;

public class UsuarioDAO{
	
	public UsuarioDAO() {
		
	}
		
	protected ArrayList<Usuario> listarUsuarios() {
		
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
					+ ",PIC.Nme_Cidade "
					+ ",PIE.Idf_Endereco"
					+ ",PIE.Des_Logradouro"
					+ ",PIE.Num_Endereco"
					+ ",PIE.Num_CEP"
					+ ",PIE.Des_Bairro"
					+ ",PIE.Des_Complemento"
					+ ",PIU.Dta_Nascimento"
					+ ",Num_DDD_Celular"
					+ ",Num_Celular"
					+ ",Email_Pessoa"
					+ ",PIU.Dta_Cadastro"
					+ ",Idf_Estado_Civil"
					+ ",PIU.Flg_Inativo"
					+ ",Idf_Tipo_Usuario"
					+ " FROM PI_Usuarios PIU "
					+ " JOIN PI_Sexo PIS ON PIS.Idf_Sexo = PIU.Idf_Sexo "
					+ " JOIN PI_Endereco PIE ON PIE.Idf_Endereco = PIU.Idf_Endereco "
					+ " JOIN PI_Cidade PIC ON PIC.Idf_Cidade = PIE.Idf_Cidade "
					+ " WHERE PIU.Flg_Inativo = 0");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Usuario us = new Usuario();	
				Endereco end = new Endereco();
				
				us.setIdf_Usuario(rs.getInt("Idf_Usuario"));
				us.setNum_CPF(rs.getString("Num_CPF"));
				us.setNme_Pessoa(rs.getString("Nme_Pessoa"));
				us.setDta_NascimentoDate(rs.getDate("Dta_Nascimento"));
				us.setNum_DDD_Celular(rs.getString("Num_DDD_Celular"));
				us.setNum_Celular(rs.getString("Num_Celular"));
				us.setEml_Pessoa(rs.getString("Email_Pessoa"));	
				us.setDesc_Sexo(rs.getString("Desc_Sexo"));
				us.setDta_CadastroDate(rs.getDate("Dta_Cadastro"));
				end.setIdf_Endereco(rs.getInt("Idf_Endereco"));
				end.setDesc_Logradouro(rs.getString("Des_Logradouro"));
				end.setNum_Endereco(rs.getString("Num_Endereco"));
				end.setNum_CEP(rs.getString("Num_CEP"));
				end.setDes_Bairro(rs.getString("Des_Bairro"));
				end.setDesc_Complemento(rs.getString("Des_Complemento"));			
				us.setEndereco(end);
				
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
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return null;
			}

		}
		return alUsuario;	
	}
	
	protected boolean inserirUsuarios(Usuario us) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		Endereco end = us.getEndereco();
		
		try {
			conn = conexao.getConnection(false);
			
			if(end.inserir(end, conn)) {
				
				stmt = conn. prepareStatement(
						"INSERT INTO PI_Usuarios "
						+ "(Num_CPF"
						+ ",Nme_Pessoa"
						+ ",Idf_Sexo"
						+ ",Dta_Nascimento"
						+ ",Idf_Endereco"
						+ ",Num_DDD_Celular"
						+ ",Num_Celular"
						+ ",Email_Pessoa"
						+ ",Dta_Cadastro"
						+ ",Idf_Estado_Civil"
						+ ",Flg_Inativo"
						+ ",Idf_Tipo_Usuario"
						+ ",Senha)"
						+ " VALUES"
						+ " (?,?,?,?,?,?,?,?,CURRENT_TIMESTAMP,?,0,1,?)");
						
				stmt.setString(1,us.getNum_CPF());
				stmt.setString(2,us.getNme_Pessoa());
				stmt.setInt(3,us.getIdf_Sexo());
				java.sql.Date sqlDate = new java.sql.Date(us.getDta_NascimentoDate().getTime());
				stmt.setDate(4, sqlDate);	
				stmt.setInt(5,end.getIdf_Endereco());
				stmt.setString(6,us.getNum_DDD_Celular());
				stmt.setString(7,us.getNum_Celular());
				stmt.setString(8,us.getEml_Pessoa());
				stmt.setInt(9,us.getIdf_Estado_Civil());
				stmt.setString(10, us.getSenha());

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
	
	protected boolean atualizarUsuario(Usuario us) {
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(false);
			
			Endereco end = us.getEndereco();
			if(end.atualizarEndereco(conn, end)) {
				
				stmt = conn. prepareStatement(
						"UPDATE PI_Usuarios SET "
					  + " Nme_Pessoa = ?"
					  + ",Idf_Sexo = ?"
					  + ",Dta_Nascimento = ?"
				      + ",Num_DDD_Celular = ?"
				      + ",Num_Celular = ?"
				      + ",Email_Pessoa = ?"
				      + ",Idf_Estado_Civil = ?"
				      + ",Senha = ?"
					  + " WHERE Idf_Usuario = ?");
										
				stmt.setString(1,us.getNme_Pessoa());
				stmt.setInt(2,us.getIdf_Sexo());
				java.sql.Date sqlDate = new java.sql.Date(us.getDta_NascimentoDate().getTime());
				stmt.setDate(3, sqlDate);	
				stmt.setString(4,us.getNum_DDD_Celular());
				stmt.setString(5,us.getNum_Celular());
				stmt.setString(6,us.getEml_Pessoa());
				stmt.setInt(7,us.getIdf_Estado_Civil());
				stmt.setInt(8,us.getIdf_Usuario());
				stmt.setString(9, us.getSenha());

				//int rowAffected -> Pode ter sido alterado ou não
				stmt.executeUpdate();

				conn.commit();
				stmt.close();
				conn.close();
				return true;			
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
	
	protected boolean deletarUsuario(Usuario us) {
		
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
	
	protected Usuario buscarUsuario(Usuario user) {
		Connection conn = null;
		PreparedStatement stmt = null;
        boolean status = false;
				
		try {
			conn = conexao.getConnection(false);
						
			stmt = conn. prepareStatement("SELECT Idf_Usuario\r\n"
					+ ",Num_CPF "
					+ ",Nme_Pessoa "
					+ ",PIS.Desc_Sexo "
					+ ",PIU.Idf_Sexo "
					+ ",PIC.Idf_Cidade"
					+ ",PIC.Nme_Cidade "
					+ ",PIE.Idf_Endereco "
					+ ",PIE.Des_Logradouro "
					+ ",PIE.Num_Endereco "
					+ ",PIE.Num_CEP "
					+ ",PIE.Des_Bairro "
					+ ",PIE.Des_Complemento "
					+ ",PIU.Dta_Nascimento "
					+ ",Num_DDD_Celular "
					+ ",Num_Celular "
					+ ",Email_Pessoa "
					+ ",PIU.Dta_Cadastro "
					+ ",Idf_Estado_Civil "
					+ ",PIU.Flg_Inativo "
					+ ",Idf_Tipo_Usuario "
					+ ",Senha "
					+ " FROM PI_Usuarios PIU "
					+ " JOIN PI_Sexo PIS ON PIS.Idf_Sexo = PIU.Idf_Sexo "
					+ " JOIN PI_Endereco PIE ON PIE.Idf_Endereco = PIU.Idf_Endereco "
					+ " JOIN PI_Cidade PIC ON PIC.Idf_Cidade = PIE.Idf_Cidade "
					+ " WHERE PIU.Flg_Inativo = 0 AND PIU.Email_Pessoa = ? and PIU.Senha = ?");
					
			stmt.setString(1,user.getEml_Pessoa());
			stmt.setString(2,user.getSenha());

			ResultSet rs = stmt.executeQuery();
			status = rs.next();	
			
			if(status) {
				Usuario us = new Usuario();
				Endereco end = new Endereco();
				
				us.setNme_Pessoa(rs.getString("Nme_Pessoa"));		
				us.setNum_CPF(rs.getString("Num_CPF"));
				us.setIdf_Sexo(rs.getInt("Idf_Sexo"));
				us.setIdf_Cidade(rs.getInt("Idf_Cidade"));

				end.setNum_CEP(rs.getString("Num_CEP"));
				end.setDesc_Logradouro(rs.getString("Des_Logradouro"));
				end.setDes_Bairro(rs.getString("Des_Bairro"));
				end.setDesc_Complemento(rs.getString("Des_Complemento"));
				end.setNum_Endereco(rs.getString("Num_Endereco"));
				end.setIdf_Cidade(rs.getInt("Idf_Cidade"));
				us.setEndereco(end);
				
				us.setIdf_Estado_Civil(rs.getInt("Idf_Estado_Civil"));
				us.setDta_NascimentoDate(rs.getDate("Dta_Nascimento"));
				us.setNum_DDD_Celular(rs.getString("Num_DDD_Celular"));
				us.setNum_Celular(rs.getString("Num_Celular"));
				us.setEml_Pessoa(rs.getString("Email_Pessoa"));
				us.setIdf_Usuario(rs.getInt("Idf_Usuario"));	
				us.setIdf_Tipo_Usuario(rs.getInt("Idf_Tipo_Usuario"));
				
				return us;
			}
			
			rs.close();
			stmt.close();
			conn.close();
		
			return null;

		} catch (SQLException e) {
			System.err.println(e);
			try {
				conn.rollback();
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return null;
			}

		}
		
		return null;	
	}

}
