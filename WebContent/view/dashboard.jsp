<%@page import="java.util.HashMap"%>
<%@page import="model.CardEquipDashboard"%>
<%@page import="model.Localizacao"%>
<%@page import="dao.LocalizacaoDao"%>
<%@page import="dao.EquipamentoDao"%>
<%@page import="model.Equipamento"%>
<%@page import="util.function"%>
<%@page import="model.Grafico"%>
<%@page import="java.util.ArrayList"%>
<%@page import="dao.RegistroDao"%>
<%@page import="model.Registro"%>
<%@page import="java.util.List"%>

<!DOCTYPE html>
<html>
<head>
    <%@include file="../menu/head_padrao.jsp"%>
</head>

 <body class="sb-nav-fixed"> 
 
 
 		<% 
			HashMap<String, String> collapse = new HashMap<String, String>();
			collapse.put("cadastros-clp","collapse");
			collapse.put("cadastros-clped","collapsed show");
			collapse.put("ger-alertas-clp","collapse");
			collapse.put("ger-alertas-clped","collapsed show");
			response.setIntHeader("Refresh", 30);
		%>

 
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
	       
            <%@include file= "../menu/layout_slide_nav.jsp" %>
         	
            <div id="layoutSidenav_content">
            	
				<main>
					<div class="container-fluid">
						
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Dashboard</li>
	                    </ol>
						
			
						<div class="card text-center">
							<div class="card-header">
								<ul class="nav nav-pills">
								<%
									LocalizacaoDao localDao = new LocalizacaoDao();
									EquipamentoDao equipDao = new EquipamentoDao();
									RegistroDao regDao = new RegistroDao();
									
									String where;
									where = "ie_situacao = 'A' and exists "+
									"	(   select 1"+
									"		from equipamento w"+
									"		where w.nr_seq_localizacao = a.nr_sequencia "+
									"		and w.ie_situacao = 'A'"+
									"	)";
									List<Localizacao> lstLocalAtivo = localDao.getAll(where, "ds_localizacao");
									
									boolean primeiroReg=true;

									for (Localizacao localizacao : lstLocalAtivo) {
										out.write("<li class='nav-item'><a class='nav-link "+(primeiroReg ? "active'":"'")+" data-toggle='pill' href='#pag"+localizacao.getId()+"'>"+localizacao.getLocalizacao()+"</a></li>");
										if(primeiroReg)
											primeiroReg=false;
									}
								%>
								</ul>
							 </div>	
						 
							<div class="tab-content mt-3">
								 <%
								 
								    primeiroReg=true;
									for (Localizacao localizacao : lstLocalAtivo) {
									
										out.write("<div id='pag"+localizacao.getId()+"' class='tab-pane fade "+(primeiroReg ? "show active'":"'")+"'>");
										out.write("<div class='row' style='margin:auto;'>");
										
										if(primeiroReg)
											primeiroReg=false;
										
										List<Equipamento> lstEquipLocalAtivo = equipDao.getAll("ie_situacao = 'A' and nr_seq_localizacao = "+String.valueOf(localizacao.getId()));
										
										for (Equipamento equip : lstEquipLocalAtivo) {
											Registro regLocal = regDao.getUltimoRegistroEquip(equip.getId());
											
											if(regLocal == null || regLocal.getId()==0){
												out.write(CardEquipDashboard.parado(equip.getDescricao()));
											}else{
												out.write(CardEquipDashboard.montar(regLocal.getNomeEquipamento(), String.valueOf(regLocal.getTemperatura()), String.valueOf(regLocal.getUmidade()), regLocal.getDesvioUmidade(), regLocal.getDesvioTemp()));
											}
										}
										
										out.write("</div></div>");
									}
								 
								%> 

							</div>			
						</div>	
						
						<br>						
											
						<%
							List<Equipamento> lstEquipAtivo = equipDao.getAll("ie_situacao = 'A'");

							for (Equipamento equip : lstEquipAtivo) {
								
								List<Registro> lstDados = regDao.getRegistroEquipHora(equip.getId(),24);
								
								Grafico graficoTemp = new Grafico();
								Grafico graficoUmidade = new Grafico();
								
								Float menorValorTemp=0f, maiorValorTemp=0f;
								Float menorValorUmidade=0f, maiorValorUmidade=0f;
								
							 	if(lstDados.size() >0){
									 
							 		graficoTemp.setMetaMax(lstDados.get(0).getTempMaxima());
							 		graficoTemp.setMetaMin(lstDados.get(0).getTempMinima());
									
							 		graficoUmidade.setMetaMax(lstDados.get(0).getUmidadeMaxima());
							 		graficoUmidade.setMetaMin(lstDados.get(0).getUmidadeMinima());
							 		
									for (Registro regList : lstDados) {
										graficoTemp.addValor(regList.getTemperatura());
										graficoTemp.addRotulo("\""+function.toDate(regList.getDtRegistro(),"HH:mm")+"\"");
										
										graficoUmidade.addValor(regList.getUmidade());
										graficoUmidade.addRotulo("\""+function.toDate(regList.getDtRegistro(),"HH:mm")+"\"");
									}
									
									menorValorTemp = function.minValue(graficoTemp.getLstvalores());
									maiorValorTemp = function.maxValue(graficoTemp.getLstvalores());
									
									menorValorUmidade = function.minValue(graficoUmidade.getLstvalores());
									maiorValorUmidade = function.maxValue(graficoUmidade.getLstvalores());
									
									if(graficoTemp.getMetaMax() > maiorValorTemp)
										maiorValorTemp = graficoTemp.getMetaMax();
									
									
									if(graficoTemp.getMetaMin() < menorValorTemp)
										menorValorTemp = graficoTemp.getMetaMin();
									
									if(graficoUmidade.getMetaMax() > maiorValorUmidade)
										maiorValorUmidade = graficoUmidade.getMetaMax();
									
									
									if(graficoUmidade.getMetaMin() < menorValorUmidade)
										menorValorUmidade = graficoUmidade.getMetaMin();
						%>
						
								<div class="row">
									<div class="col-xl-6 ">
										<div class="card mb-4">
				                            <div class="card-header">
				                                 <i class="fas fa-snowflake mr-1" 
				                                	onload='montarChart("<%="TEMP"+equip.getId()%>",<%=graficoTemp.getLstRotulos().toString()%>,<%=graficoTemp.getLstvalores().toString()%>,<%=graficoTemp.getMetaMin()%>,<%=graficoTemp.getMetaMax()%>,<%=menorValorTemp%>,<%=maiorValorTemp%>,"Cº")'></i>
				                                Temperatura <%=equip.getDescricao()%>
				                            </div>
		
				                            <div class="card-body" >
				                            	<canvas id="<%="TEMP"+equip.getId()%>" width="100%" height="30" ></canvas>
				                            </div>
				                        </div>
				                        
									</div>

									
									<div class="col-xl-6 " >
										<div class="card mb-4">
				                            <div class="card-header">
				                                <i class="fas fa-tint-slash mr-1"
													onload='montarChart("<%="UMIDADE"+equip.getId()%>",<%=graficoUmidade.getLstRotulos().toString()%>,<%=graficoUmidade.getLstvalores().toString()%>,<%=graficoUmidade.getMetaMin()%>,<%=graficoUmidade.getMetaMax()%>,<%=menorValorUmidade%>,<%=maiorValorUmidade%>,"%")'></i>
				                                Umidade <%=equip.getDescricao()%>
				                            </div>
				                            <div class="card-body"><canvas id="<%="UMIDADE"+equip.getId()%>" width="100%" height="30"></canvas></div>
				                        </div>
									</div>
								</div>
										
						<%		
								}
							}
								
						%>

					</div>
				</main>
				
			</div>			
        </div>    
       
               
        <%@include file="../menu/scripts.jsp"%>  
        

        <script>
	      function montarChart(idGrafico,rotulo, valor, metaMin, metaMax, minEixo, maxEixo, label) {
	    	  Chart.defaults.global.defaultFontFamily = '-apple-system,system-ui,BlinkMacSystemFont,"Segoe UI",Roboto,"Helvetica Neue",Arial,sans-serif';
	    	  Chart.defaults.global.defaultFontColor = '#292b2c';
	    	  if(rotulo.length >0){
		    	  var ctx = document.getElementById(idGrafico);
		    	  var myLineChart = new Chart(ctx, {
		    	    type: 'line',
		    	    data: {
		    	      labels: rotulo,
		    	      datasets: [{
		    	        label: label,
		    	        lineTension: 0.3,
		    	        backgroundColor: "rgba(2,117,216,0.2)",
		    	        borderColor: "rgba(2,117,216,1)",
		    	        pointRadius: 5,
		    	        pointBackgroundColor: "rgba(2,117,216,1)",
		    	        pointBorderColor: "rgba(255,255,255,0.8)",
		    	        pointHoverRadius: 5,
		    	        pointHoverBackgroundColor: "rgba(2,117,216,1)",
		    	        pointHitRadius: 50,
		    	        pointBorderWidth: 2,
		    	        data: valor,
		    	      }, {
		    	    	  label: label+" min",
		    	    	  data: [metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin, metaMin],
		    	    	  type: 'line',
		    	    	  backgroundColor: "rgba(0,0,0,0)",
		    	    	  borderColor: "rgba(255,0,0,1)",
		    	    	  pointRadius: 0.2,
		    	    	  borderWidth: 1
		    	    	 }, {
			    	    	  label: label+" max",
			    	    	  data: [metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax, metaMax],
			    	    	  type: 'line',
			    	    	  backgroundColor: "rgba(0,0,0,0)",
			    	    	  borderColor: "rgba(255,0,0,1)",
			    	    	  pointRadius: 0.2,
			    	    	  borderWidth: 1
			    	    	 }
		    	      ],
		    	    },
		    	    options: {
		    	      scales: {
		    	        xAxes: [{
		    	          time: {
		    	            unit: 'date'
		    	          },
		    	          gridLines: {
		    	            display: false
		    	          },
		    	          ticks: {
		    	            maxTicksLimit: 13
		    	          }
		    	        }],
		    	        yAxes: [{
		    	          ticks: {
		    	            min: minEixo,
		    	            max: maxEixo,
		    	            maxTicksLimit: 10
		    	          },
		    	          gridLines: {
		    	            color: "rgba(0, 0, 0, .125)",
		    	            borderDash: [-5],
		    	          }
		    	        }],
		    	      },
		    	      legend: {
		    	        display: false
		    	      }
		    	    }
		    	  });
	    	  }
	      };
		</script>
		
    </body>
</html>