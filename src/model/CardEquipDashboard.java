package model;

public class CardEquipDashboard {
	
	public static String montar(String equipamento, String temperatura, String umidade, String desvioUmidade, String desvioTemp) {
		
		String tipoCard="primary";
		
		if(desvioUmidade.equals("S") || desvioTemp.equals("S")) {
			tipoCard="warning";
			equipamento+=" (Desvio)";
		}
			
		
		return "<div class='col-xl-3 col-md-6'>"+
		"	<div class='card border-"+tipoCard+" text-white mb-4' align='center'>"+
		"		<div class='card-header  bg-"+tipoCard+"'>"+equipamento+"</div>"+
		"	        <div class='card-body text-"+tipoCard+"'>"+
		"	            <div class='row'>	"+
		"	                <div class='col-xl-6 col-md-6'>"+
		"	                	<i class='fas fa-snowflake'></i>"+
		"	                	<label>"+temperatura+"Cº</label>"+
		"	                </div>"+
		"	                <div class='col-xl-6 col-md-6'>"+
		"	                	<i class='fas fa-tint-slash'></i>"+
		"	                	<label>"+umidade+"%</label>"+
		"	                </div>"+
		"	            </div>	"+
		"	        </div>"+
		"	</div>"+
		"</div>";
	}
	
	
	public static String parado(String equipamento) {
		
		return "<div class='col-xl-3 col-md-6'>"+
		"	<div class='card border-danger text-white mb-4' align='center'>"+
		"		<div class='card-header  bg-danger '>Sensor Parado</div>"+
		"	        <div class='card-body text-danger '>"+
		"	        	<a class='small text-danger stretched-link'>"+equipamento+"</a>"+
		"	        </div>"+
		"	</div>"+
		"</div>";
		
	}
	
	
}
