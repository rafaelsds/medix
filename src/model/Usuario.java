package model;

import java.sql.Timestamp;

import util.function;

public class Usuario{
    
    private int id;
    private String nomeUsuario;
    private String login;
    private String senha;
    private Timestamp dataNascimento;
    private String email;
    private String papelUsuario;
    private String telefone;
    private String situacao;
    private int idUserTelegram;
    
    public Usuario() {
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		if(senha.length()<32) {
			this.senha = function.convertStringToMd5(senha); //Verifica se é necessário encriptografar
		}else {
			this.senha = senha;
		}
	}

	public void setSenhaCript(String senha) {
		this.senha = senha;
	}
	
	public Timestamp getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Timestamp dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	

	public String getPapelUsuario() {
		return papelUsuario;
	}

	public void setPapelUsuario(String papelUsuario) {
		this.papelUsuario = papelUsuario;
	}

	
	
	
	public int getIdUserTelegram() {
		return idUserTelegram;
	}

	public void setIdUserTelegram(int idUserTelegram) {
		this.idUserTelegram = idUserTelegram;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	
	
	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nomeUsuario=" + nomeUsuario + ", login=" + login + ", senha=" + senha
				+ ", dataNascimento=" + dataNascimento + ", email=" + email + ", papelUsuario=" + papelUsuario
				+ ", telefone=" + telefone + ", situacao=" + situacao + ", idUserTelegram=" + idUserTelegram + "]";
	}




	
 
}