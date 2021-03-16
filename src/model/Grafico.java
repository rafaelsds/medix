package model;

import java.util.ArrayList;


public class Grafico{
    
	private ArrayList<String> lstRotulos = new ArrayList<String>();
	private ArrayList<Float> lstvalores = new ArrayList<Float>();
	
	private float metaMin;
	private float metaMax;
	
	public void addRotulo(String element) {
		lstRotulos.add(element);
	}
	
	public void addValor(Float element) {
		lstvalores.add(element);
	}

	public ArrayList<String> getLstRotulos() {
		return lstRotulos;
	}


	public ArrayList<Float> getLstvalores() {
		return lstvalores;
	}

	public float getMetaMin() {
		return metaMin;
	}

	public void setMetaMin(float metaMin) {
		this.metaMin = metaMin;
	}

	public float getMetaMax() {
		return metaMax;
	}

	public void setMetaMax(float metaMax) {
		this.metaMax = metaMax;		
	}



	
	
	
}