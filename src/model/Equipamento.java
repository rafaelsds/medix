package model;


public class Equipamento extends Localizacao{
    
    private int id;
    private String descricao;
    private Float tempMinima;
    private Float tempMaxima;
    private Float umidadeMinima;
    private Float umidadeMaxima;
    private int idLocalizacao;
    private String macCircuito;
    private String situacao;
    private String manutencao;
    private String localizacao;
    
    public Equipamento(){
    	
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getTempMinima() {
		return tempMinima;
	}

	public void setTempMinima(Float tempMinima) {
		this.tempMinima = tempMinima;
	}

	public Float getTempMaxima() {
		return tempMaxima;
	}

	public void setTempMaxima(Float tempMaxima) {
		this.tempMaxima = tempMaxima;
	}

	public Float getUmidadeMinima() {
		return umidadeMinima;
	}

	public void setUmidadeMinima(Float umidadeMinima) {
		this.umidadeMinima = umidadeMinima;
	}

	public Float getUmidadeMaxima() {
		return umidadeMaxima;
	}

	public void setUmidadeMaxima(Float umidadeMaxima) {
		this.umidadeMaxima = umidadeMaxima;
	}

	public int getIdLocalizacao() {
		return idLocalizacao;
	}

	public void setIdLocalizacao(int localizacao) {
		this.idLocalizacao = localizacao;
	}

	
	
	public String getMacCircuito() {
		return macCircuito;
	}

	public void setMacCircuito(String macCircuito) {
		this.macCircuito = macCircuito;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public String getManutencao() {
		return manutencao;
	}

	public void setManutencao(String manutencao) {
		this.manutencao = manutencao;
	}

	@Override
	public String toString() {
		return "Equipamento [id=" + id + ", descricao=" + descricao + ", tempMinima=" + tempMinima + ", tempMaxima="
				+ tempMaxima + ", umidadeMinima=" + umidadeMinima + ", umidadeMaxima=" + umidadeMaxima
				+ ", idLocalizacao=" + idLocalizacao + ", macCircuito=" + macCircuito + ", situacao=" + situacao
				+ ", manutencao=" + manutencao + ", localizacao=" + localizacao + "]";
	}
		
	
	

 }