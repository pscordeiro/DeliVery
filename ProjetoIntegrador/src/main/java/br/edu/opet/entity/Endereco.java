package br.edu.opet.entity;

import java.sql.Connection;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.edu.opet.model.entity.dao.EnderecoDAO;

@ManagedBean
@RequestScoped
public class Endereco extends EnderecoDAO {
	
	
	private int Idf_Endereco;
	private String Desc_Logradouro;
	private String Num_CEP;
	private String Num_Endereco;
	private String Desc_Complemento;
	private String Des_Bairro;
	private int Idf_Cidade;
	
	
	public int getIdf_Endereco() {
		return Idf_Endereco;
	}
	public void setIdf_Endereco(int idf_Endereco) {
		Idf_Endereco = idf_Endereco;
	}
	public String getDesc_Logradouro() {
		return Desc_Logradouro;
	}
	public void setDesc_Logradouro(String desc_Logradouro) {
		Desc_Logradouro = desc_Logradouro;
	}
	public String getNum_CEP() {
		return Num_CEP;
	}
	public void setNum_CEP(String num_CEP) {
		Num_CEP = num_CEP;
	}
	public String getNum_Endereco() {
		return Num_Endereco;
	}
	public void setNum_Endereco(String num_Endereco) {
		Num_Endereco = num_Endereco;
	}
	public String getDesc_Complemento() {
		return Desc_Complemento;
	}
	public void setDesc_Complemento(String desc_Complemento) {
		Desc_Complemento = desc_Complemento;
	}
	public String getDes_Bairro() {
		return Des_Bairro;
	}
	public void setDes_Bairro(String des_Bairro) {
		Des_Bairro = des_Bairro;
	}
	public int getIdf_Cidade() {
		return Idf_Cidade;
	}
	public void setIdf_Cidade(int idf_Cidade) {
		Idf_Cidade = idf_Cidade;
	}
	
	public boolean inserirEndereco(Endereco end, Connection conn) {
		return super.inserir(this, conn);
	}
	
	public boolean atualizarEndereco(Connection conn) {
		return super.atualizar(conn, this);
	}
	
}
