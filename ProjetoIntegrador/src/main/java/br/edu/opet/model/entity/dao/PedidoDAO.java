package br.edu.opet.model.entity.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.opet.entity.model.Carrinho;
import br.edu.opet.entity.model.Endereco;
import br.edu.opet.entity.model.Pedido;
import br.edu.opet.entity.model.PedidoItem;
import br.edu.opet.entity.model.Produto;
import br.edu.opet.entity.model.Usuario;
import br.edu.opet.util.conexao;

public class PedidoDAO {
	
	protected ArrayList<Pedido> listarPedidos() {
		
		ArrayList<Pedido> alPedido = new ArrayList<Pedido>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
			 ("SELECT PIP.Idf_Pedido, "
			+ "PIE.Des_Logradouro, "
			+ "PIE.Num_Endereco,"
			+ "PIE.Des_Bairro,"
			+ "PIC.Nme_Cidade,"
			+ "PIC.Sig_Estado,"
			+ "PIP.Idf_Usuario,"
			+ "PIP.DataHoraPedido "
			+ " FROM PI_Pedido PIP "
			+ " JOIN PI_Usuarios PIU on PIU.Idf_Usuario = PIP.Idf_Usuario "
			+ " JOIN PI_Endereco PIE on PIE.Idf_Endereco = PIU.Idf_Endereco "
			+ " JOIN PI_Cidade PIC on PIC.Idf_Cidade = PIE.Idf_Cidade "
			+ " WHERE PIP.Idf_Situacao = 1");
		
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				Pedido ped = new Pedido();
				Usuario us = new Usuario();
				Endereco end = new Endereco();
				
				end.setDesc_Logradouro(rs.getString("Des_Logradouro"));
				end.setNum_Endereco(rs.getString("Num_Endereco"));
				end.setDes_Bairro(rs.getString("Des_Bairro"));
				end.setNme_Cidade(rs.getString("Nme_Cidade"));
				end.setSig_Estado(rs.getString("Sig_Estado"));
				us.setIdf_Usuario(rs.getInt("Idf_Usuario"));
				ped.setEndereco(end);
				ped.setUsuario(us);				
				ped.setIdf_Pedido(rs.getInt("Idf_Pedido"));
				ped.setDataHoraPedido(rs.getDate("DataHoraPedido"));
				ped.setIdf_Usuario(rs.getInt("Idf_Usuario"));
				
				alPedido.add(ped);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alPedido;
			
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
		return alPedido;	
	}
	protected ArrayList<PedidoItem> listarItensPedido(int Idf_Pedido) {
		
		ArrayList<PedidoItem> alPedidoItem = new ArrayList<PedidoItem>();
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = conexao.getConnection(true);		
			stmt = conn. prepareStatement
			 ("SELECT PIPR.Desc_Produto, "
	 		+ "PIPE.Quantidade, "
	 		+ "PIPE.Valor_Produto, "
	 		+ "PIPE.Valor_Total, "
	 		+ "PIPR.Desc_Produto "
	 		+ "FROM PI_Pedido PIP "
	 		+ "JOIN PI_PedidoItem PIPE on PIPE.Idf_Pedido = PIP.Idf_Pedido "
	 		+ "JOIN PI_Produtos PIPR on PIPR.Idf_Produto = PIPE.Idf_Produto "
	 		+ "WHERE PIP.Idf_Pedido = ? ");
				
			stmt.setInt(1,Idf_Pedido);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				
				PedidoItem pedItem = new PedidoItem();
				Produto prod = new Produto();
				pedItem.setValor_Produto(rs.getDouble("Valor_Produto"));
				pedItem.setQuantidade(rs.getInt("Quantidade"));
				pedItem.setValor_Total(rs.getDouble("Valor_Total"));
				prod.setDesc_Produto(rs.getString("Desc_Produto"));
				pedItem.setProduto(prod);
				
				alPedidoItem.add(pedItem);
			}
			
			rs.close();
			stmt.close();
			conn.close();
			
			return alPedidoItem;
			
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
		return alPedidoItem;	
	}
	protected boolean salvarPedido(Pedido ped, Carrinho car) {
		Connection conn = null;
		PreparedStatement stmt = null;	
		
		try {
			conn = conexao.getConnection(false);
			ArrayList<Carrinho> alListaItens = car.listarCarrinho(car);
	
			if(inserirPedido(ped, conn)) {
				
				for(Carrinho item : alListaItens) {
					
					stmt = conn. prepareStatement(
						"INSERT into PI_PedidoItem (Idf_Pedido, Idf_Produto, Quantidade, Valor_Produto,Percentual_Desconto, Valor_Desconto, Valor_Total) "
						+ "values (?,?,?,?,0,0,?)"
					);
					
					stmt.setInt(1,ped.getIdf_Pedido());
					stmt.setInt(2, item.getProd_Carrinho().getIdf_Produto());
					stmt.setInt(3, item.getProd_Carrinho().getQuantidade());
					stmt.setDouble(4, item.getProd_Carrinho().getValor_Produto());
					stmt.setDouble(5, item.getSub_Total());
					
					int rowAffected = stmt.executeUpdate();
					if(rowAffected == 1){	
						conn.commit();
						stmt.close();
					}
					else {
						conn.rollback();
						stmt.close();
					}	
		
				}
			
				conn.commit();
				conn.close();
				car.apagarCarrinho(car);
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
				stmt.close();
				conn.close();
			} catch (SQLException e1) {
				System.err.println(e1);
				return false;
			}

		}
		return false;	
	}
	protected boolean inserirPedido(Pedido ped, Connection conn) {
		PreparedStatement stmt = null;	
		
		try {							
			stmt = conn. prepareStatement(
					"INSERT into PI_Pedido values (getdate(),1,1,?)",
					Statement.RETURN_GENERATED_KEYS);
					
			stmt.setInt(1,ped.getIdf_Usuario());

			int rowAffected = stmt.executeUpdate();
			
			if(rowAffected == 1){
				ResultSet rs = stmt.getGeneratedKeys();
				rs.next();
				int Idf_Pedido = rs.getInt(1);
				ped.setIdf_Pedido(Idf_Pedido);
				conn.commit();
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

}
