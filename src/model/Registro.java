package model;

import java.sql.Timestamp;
import java.util.Date;

public class Registro extends Equipamento{
    
    private int id;
    private int idEquipamento;
    private String nomeEquipamento;
    private Float temperatura;
    private Float umidade;
    private Timestamp dtRegistro;
    private Timestamp dtModificacao;
    private String observacao;
    private String usuarioRegistro;
    private String usuarioModificacao;
    private String desvioTemp;
    private String desvioUmidade;
    
    public Registro() {
    }
    
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdEquipamento() {
		return idEquipamento;
	}

	public void setIdEquipamento(int idEquipamento) {
		this.idEquipamento = idEquipamento;
	}

	public Float getTemperatura() {
		return temperatura;
	}

	public void setTemperatura(Float temperatura) {
		this.temperatura = temperatura;
	}

	public Float getUmidade() {
		return umidade;
	}

	public void setUmidade(Float umidade) {
		this.umidade = umidade;
	}

	public Timestamp getDtRegistro() {
		return dtRegistro;
	}

	public void setDtRegistro(Timestamp dtRegistro) {
		this.dtRegistro = dtRegistro;
	}

	public Timestamp getDtModificacao() {
		return dtModificacao;
	}

	public void setDtModificacao(Timestamp dtModificacao) {
		this.dtModificacao = dtModificacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getUsuarioRegistro() {
		return usuarioRegistro;
	}

	public void setUsuarioRegistro(String usuarioRegistro) {
		this.usuarioRegistro = usuarioRegistro;
	}

	public String getUsuarioModificacao() {
		return usuarioModificacao;
	}

	public void setUsuarioModificacao(String usuarioModificacao) {
		this.usuarioModificacao = usuarioModificacao;
	}

	public String getDesvioTemp() {
		return desvioTemp;
	}

	public void setDesvioTemp(String desvioTemp) {
		this.desvioTemp = desvioTemp;
	}

	public String getDesvioUmidade() {
		return desvioUmidade;
	}

	public void setDesvioUmidade(String desvioUmidade) {
		this.desvioUmidade = desvioUmidade;
	}

	public String getNomeEquipamento() {
		return nomeEquipamento;
	}

	public void setNomeEquipamento(String nomeEquipamento) {
		this.nomeEquipamento = nomeEquipamento;
	}
	
	@Override
	public String toString() {
		return "Registro [id=" + id + ", idEquipamento=" + idEquipamento + ", nomeEquipamento=" + nomeEquipamento
				+ ", temperatura=" + temperatura + ", umidade=" + umidade + ", dtRegistro=" + dtRegistro
				+ ", dtModificacao=" + dtModificacao + ", observacao=" + observacao + ", usuarioRegistro="
				+ usuarioRegistro + ", usuarioModificacao=" + usuarioModificacao + ", desvioTemp=" + desvioTemp
				+ ", desvioUmidade=" + desvioUmidade + "]";
	}
	 
    
	
	

 }