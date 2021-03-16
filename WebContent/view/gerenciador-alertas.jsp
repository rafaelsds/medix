<%@page import="dao.AlertaDao"%>
<%@page import="model.Gateway"%>
<%@page import="model.Alerta"%>
<%@page import="model.Localizacao"%>
<%@page import="dao.LocalizacaoDao"%>
<%@page import="model.Usuario"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="model.Equipamento"%>
<%@page import="dao.EquipamentoDao"%>
<%@page import="java.util.HashMap"%>
<%@ page import="controller.GatewayController" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page import="dao.GatewayDao"%>


<!DOCTYPE html>
<html>
	<%@include file="../menu/head_padrao.jsp"%>

<body class="sb-nav-fixed" > 
		
		<% 
			HashMap<String, String> collapse = new HashMap<String, String>();
			collapse.put("ger-alertas-clp","collapsed show");
			collapse.put("cadastros-clp","collapse");
			collapse.put("cadastros-clped","collapsed show");
		%>
		
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
            <%@include file="../menu/layout_slide_nav.jsp"%>
          		
          		<div id="layoutSidenav_content">
            	<form method="POST" action="/medix/view/gerenciador-alertas">
				<main>
					<div class="container-fluid">
					
						<% 
							HttpSession ses = request.getSession(true);
							Alerta alertaSessao = (Alerta) ses.getAttribute("alertaParam");
							
							String situacao="A", monitorUmidade="S", monitorTemp="S", alertaTelegram="S", alertaEmail="S";
							int idUsuario=0, idEquipamento=0, idLocalizacao=0;
							
							if(alertaSessao != null){
								
								idUsuario=alertaSessao.getIdUsuario();
								idEquipamento=alertaSessao.getIdEquipamento();
								idLocalizacao=alertaSessao.getIdLocalizacao();
								
								if(alertaSessao.getSituacao() != null && !alertaSessao.getSituacao().isEmpty())
									situacao = alertaSessao.getSituacao();
								
								if(alertaSessao.getMonitorarTemp() != null && !alertaSessao.getMonitorarTemp().isEmpty())
									monitorTemp = alertaSessao.getMonitorarTemp();
								
								if(alertaSessao.getMonitorarUmidade() != null && !alertaSessao.getMonitorarUmidade().isEmpty())
									monitorUmidade = alertaSessao.getMonitorarUmidade();
								
								if(alertaSessao.getAlertaEmail() != null && !alertaSessao.getAlertaEmail().isEmpty())
									alertaEmail = alertaSessao.getAlertaEmail();
								
								if(alertaSessao.getAlertaTelegram() != null && !alertaSessao.getAlertaTelegram().isEmpty())
									alertaTelegram = alertaSessao.getAlertaTelegram();

							}
						%>
					
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Cadastro de Alerta</li>
	                    </ol>
	
						<div class="form-row">
							<div class="col-md-2 mb-3">
								<label>Código</label> 
								<input type="text" class="form-control" name="id" readonly value="${alertaParam.getId()}">
							</div>
						</div>
						
						<div class="form-row">
							<div class="col-md-4 mb-3">
								<label>Localização</label> 
								<select
									class="custom-select" name="idLocalizacao">
									<option value='0'>Todas</option>
									<%
										LocalizacaoDao localDao = new LocalizacaoDao();
										List<Localizacao> lstLocal = localDao.getAll("ie_situacao = 'A'");
																						
										for (Localizacao localList : lstLocal) {
										    out.write("<option value='"+localList.getId()+"' "+(idLocalizacao == 0 ? "" : idLocalizacao == localList.getId() ? "selected=''" : "" ) +" >"+localList.getLocalizacao()+"</option>"); 
										}
									%>
								</select>
							</div>
						
							<div class="col-md-4 mb-3">
								<label>Equipamento</label> 
								<select
									class="custom-select" name="idEquipamento">
									<option value='0'>Todos</option>
									<%
										EquipamentoDao equipDao = new EquipamentoDao();
										List<Equipamento> lstEquip = equipDao.getAll();
		
										for (Equipamento equipList : lstEquip) {
											out.write("<option value='"+equipList.getId()+"' "+(idEquipamento == 0 ? "" : idEquipamento == equipList.getId() ? "selected=''" : "" ) +" >"+equipList.getDescricao()+"</option>"); 
										}
									%>
								</select>
							</div>
							
							<div class="col-md-4 mb-3">
								<label>Usuário</label> 
								<select
									class="custom-select" name="idUsuario">
									<option value='0'>Todos</option>
									<%
										UsuarioDao usuDao = new UsuarioDao();
										List<Usuario> lstUsuario = usuDao.getAll();
		
										for (Usuario lstUsu : lstUsuario) {
											out.write("<option value='"+lstUsu.getId()+"' "+(idUsuario == 0 ? "" : idUsuario == lstUsu.getId() ? "selected=''" : "" ) +" >"+lstUsu.getLogin()+"</option>"); 
										}
									%>
								</select>
							</div>
							
						</div> 
						
						<div class="form-row">
							<div class="col-md-4 mb-3">
								<label for="validationDefault04">Monitorar Temperatura</label>  
								<select class="custom-select" name="monitorarTemp">							    
								    <% 
								    		out.write("<option value='S' "+( monitorTemp.equals("S") ? "selected=''" : "" ) +">Sim</option>"); 
							     	  		out.write("<option value='N' "+( monitorTemp.equals("N") ? "selected=''" : "" ) +">Não</option>");
									 %>
								</select>
							</div>
							
							<div class="col-md-4 mb-3">
								<label for="validationDefault04">Monitorar Umidade</label>  
								<select class="custom-select" name="monitorarUmidade">							    
								    <% 
								     	  out.write("<option value='S' "+( monitorUmidade.equals("S") ? "selected=''" : "" ) +">Sim</option>"); 
								     	  out.write("<option value='N' "+( monitorUmidade.equals("N") ? "selected=''" : "" ) +">Não</option>");
									 %>
								</select>
							</div>
							
							<div class="col-md-4 mb-3">
								<label for="validationDefault04">Situação</label>  
								<select class="custom-select" name="situacao">							    
								    <% 
								     	  out.write("<option value='A' "+( situacao.equals("A") ? "selected=''" : "" ) +">Ativo</option>"); 
								     	  out.write("<option value='I' "+( situacao.equals("I") ? "selected=''" : "" ) +">Inativo</option>");
									 %>
								</select>
							</div>
							
						</div>
						
						<div class="form-row">
							<div class="col-md-4 mb-3">
								<div class="custom-control custom-checkbox">
								    <%out.write("<input type='checkbox' "+(alertaTelegram.equals("S") ? "checked":"")+" class='custom-control-input' value='S' name='alertaTelegram' id='alertaTelegram'>");%>
								    <label class="custom-control-label" for="alertaTelegram">Enviar Alerta Telegram</label>
								</div>
						 	</div>
						 	<div class="col-md-4 mb-3">
								<div class="custom-control custom-checkbox">
								    
								     <%out.write("<input type='checkbox' "+(alertaEmail.equals("S") ? "checked":"")+" class='custom-control-input' value='S' name='alertaEmail' id='alertaEmail'>");%>
								    <label class="custom-control-label" for="alertaEmail">Enviar Alerta E-mail</label>
								</div>
						 	</div>
						</div>
						
						<button class="btn btn-primary" name="action" value="save" type="submit">Salvar</button>
						<button class="btn btn-primary" onclick="this.href='/medix/view/gerenciador-alertas'">Cancelar</button>
					</div>
				</main>
			</form>	
			<br>
			<div class="card mb-4" style="margin-left: 20px; margin-right: 20px;">
				<div class="card-header">
					<i class="fas fa-table mr-1"></i>Alertas
				</div>

				<div class="card-body">
					<div>
						<table class="table table-bordered" width="100%" cellspacing="0">
							<thead>
								<th>Localização</th>
								<th>Equipamento</th>
								<th>Usuário</th>
								<th>Situação</th>
								<th>Temp</th>
								<th>Umidade</th>
								<th>Editar</th>
								<th>Excluir</th>
							</thead>
							<tbody>

							   <%
								AlertaDao dao = new AlertaDao();
								List<Alerta> lista = dao.getAll();

								for (Alerta f : lista) {
								%>

								<tr>
									<td><%=f.getLocalizacao()%></td>
									<td><%=f.getEquipamento()%></td>
									<td><%=f.getUsuario()%></td>
									<td><%=(f.getSituacao().equals("A") ? "Ativo" : "Inativo")%></td>
									<td><%=f.getMonitorarTemp()%></td>
									<td><%=f.getMonitorarUmidade()%></td>
									
									<td align="center"><a
										onclick="this.href='/medix/view/gerenciador-alertas?action=alt&id='+<%=f.getId()%>">
											<i class="fas fa-user fa-edit"></i>
									</a></td>

									<td align="center"><a
										onclick="this.href='/medix/view/gerenciador-alertas?action=del&id='+<%=f.getId()%>">
											<i class="fas fa-user fa-trash-alt"></i>
									</a></td>

								</tr>
								<%
									}
								%>

							</tbody>
						</table>
					</div>
				</div>
			</div>
				
          </div> 
          		
        </div>    
       
        <%@include file="../menu/scripts.jsp"%>
    </body>
</html>