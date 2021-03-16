package model;


public class GerenciadorAlertaConfig{
    
    private int id;
    private String servidorSmtp;
    private int portaEmail;
    private String situacaoEmail;
    private String enderecoEmail;
    private String senhaEmail;
    private String nomeExibicaoEmail;
    private String descricaoBot;
    private String tokenBot;
    private String situacaoBot;
    private String situacaoGerenciador;
    private int frequenciaAtualizacao;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getServidorSmtp() {
		return servidorSmtp;
	}
	public void setServidorSmtp(String servidorSmtp) {
		this.servidorSmtp = servidorSmtp;
	}

	
	
	public int getPortaEmail() {
		return portaEmail;
	}
	public void setPortaEmail(int portaEmail) {
		this.portaEmail = portaEmail;
	}
	public String getSituacaoEmail() {
		return situacaoEmail;
	}
	public void setSituacaoEmail(String situacaoEmail) {
		this.situacaoEmail = situacaoEmail;
	}
	public String getEnderecoEmail() {
		return enderecoEmail;
	}
	public void setEnderecoEmail(String enderecoEmail) {
		this.enderecoEmail = enderecoEmail;
	}
	public String getSenhaEmail() {
		return senhaEmail;
	}
	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}
	public String getNomeExibicaoEmail() {
		return nomeExibicaoEmail;
	}
	public void setNomeExibicaoEmail(String nomeExibicaoEmail) {
		this.nomeExibicaoEmail = nomeExibicaoEmail;
	}
	public String getDescricaoBot() {
		return descricaoBot;
	}
	public void setDescricaoBot(String descricaoBot) {
		this.descricaoBot = descricaoBot;
	}
	public String getTokenBot() {
		return tokenBot;
	}
	public void setTokenBot(String tokenBot) {
		this.tokenBot = tokenBot;
	}
	public String getSituacaoBot() {
		return situacaoBot;
	}
	public void setSituacaoBot(String situacaoBot) {
		this.situacaoBot = situacaoBot;
	}
	public int getFrequenciaAtualizacao() {
		return frequenciaAtualizacao;
	}
	public void setFrequenciaAtualizacao(int frequenciaAtualizacao) {
		this.frequenciaAtualizacao = frequenciaAtualizacao;
	}
	public String getSituacaoGerenciador() {
		return situacaoGerenciador;
	}
	public void setSituacaoGerenciador(String situacaoGerenciador) {
		this.situacaoGerenciador = situacaoGerenciador;
	}


    
    
 }