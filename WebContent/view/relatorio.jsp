<%@page import="java.util.HashMap"%>
<%@page import="model.Equipamento"%>
<%@page import="dao.EquipamentoDao"%>
<%@ page import="controller.RegistroController" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page import="model.Registro"%>
<%@page import="dao.RegistroDao"%>
<%@page import="util.function"%>

<!DOCTYPE html>
<html>
	<%@include file="../menu/head_padrao.jsp"%>

<body class="sb-nav-fixed" > 
		
		<% 
			HashMap<String, String> collapse = new HashMap<String, String>();
			collapse.put("cadastros-clp","collapse");
			collapse.put("cadastros-clped","collapsed show");
			collapse.put("ger-alertas-clp","collapse");
			collapse.put("ger-alertas-clped","collapsed show");
		%>
		
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
       			
       			<%@include file= "../menu/layout_slide_nav.jsp" %>
          		
          		<div id="layoutSidenav_content">
            	
				<main>
					<div class="container-fluid">
										
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Relatórios</li>
	                    </ol>

											
						<div class="form-row">
						
							<div class="col-md-4 mb-3">
								<label>Relatório</label> 
								<select class="custom-select" id="relatorio">
									<option value="temperaturaAnvisaMensal">Temperatura Mensal (ANVISA)</option>
									<option value="umidadeAnvisaMensal">Umidade Mensal (ANVISA)</option>	
								</select>
							</div>
						
							<div class="col-md-4 mb-3">
								<label>Equipamento</label> 
								<select
									class="custom-select" id="idEquipamento">
									
									<%
										EquipamentoDao equipDao = new EquipamentoDao();
										List<Equipamento> lstEquip = equipDao.getAll();

										for (Equipamento equipList : lstEquip) {
											out.write("<option value='"+equipList.getId()+"'>"+equipList.getDescricao()+"</option>"); 
										}
									%>
									
								</select>
							</div>
							
							<div class="col-md-4 mb-2">
									<label>Mês Referência</label> 
									<% 
										String dataFiltro = function.getDate("yyyy-MM")+"-01"; //Setar dia primeiro
									    out.write("<input type='date' id='mesReferencia' name='mesReferencia' class='form-control' value='"+dataFiltro+"'>"); 
									%>
							</div>

						</div>
						
						<button class="btn btn-primary" onclick= "window.open('/medix/view/gerarRelatorio?mesReferencia='+document.getElementById('mesReferencia').value+'&idEquipamento='+document.getElementById('idEquipamento').value+'&relatorio='+document.getElementById('relatorio').value,'_blank');">Gerar</button>

					</div>
				</main>
							   
          </div>  
        </div>    
        <%@include file="../menu/scripts.jsp"%>
    </body>
</html>