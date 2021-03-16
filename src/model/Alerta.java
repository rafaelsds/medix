package model;

import java.sql.Timestamp;

import util.function;

public class Alerta{
    
    private int id;
    private int idUsuario;
    private int idLocalizacao;
    private int idEquipamento;
    private String situacao;
    private String monitorarUmidade;
    private String monitorarTemp;
    private String usuario;
    private String equipamento;
    private String localizacao;
    private String alertaTelegram;
    private String alertaEmail;
    
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public int getIdLocalizacao() {
		return idLocalizacao;
	}
	public void setIdLocalizacao(int idLocalizacao) {
		this.idLocalizacao = idLocalizacao;
	}
	public int getIdEquipamento() {
		return idEquipamento;
	}
	public void setIdEquipamento(int idEquipamento) {
		this.idEquipamento = idEquipamento;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getMonitorarUmidade() {
		return monitorarUmidade;
	}
	public void setMonitorarUmidade(String monitorarUmidade) {
		this.monitorarUmidade = monitorarUmidade;
	}
	public String getMonitorarTemp() {
		return monitorarTemp;
	}
	public void setMonitorarTemp(String monitorarTemp) {
		this.monitorarTemp = monitorarTemp;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getEquipamento() {
		return equipamento;
	}
	public void setEquipamento(String equipamento) {
		this.equipamento = equipamento;
	}
	public String getLocalizacao() {
		return localizacao;
	}
	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}
	public String getAlertaTelegram() {
		return alertaTelegram;
	}
	public void setAlertaTelegram(String alertaTelegram) {
		this.alertaTelegram = alertaTelegram;
	}
	public String getAlertaEmail() {
		return alertaEmail;
	}
	public void setAlertaEmail(String alertaEmail) {
		this.alertaEmail = alertaEmail;
	}

    
	
 
}