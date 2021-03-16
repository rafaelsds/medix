<%@page import="java.util.HashMap"%>
<%@page import="dao.LocalizacaoDao"%>
<%@page import="model.Localizacao"%>
<%@ page import="controller.EquipamentoController" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page import="model.Equipamento"%>
<%@page import="dao.EquipamentoDao"%>
<%@page import="util.function"%>

<!DOCTYPE html>
<html>
	<%@include file="../menu/head_padrao.jsp"%>

<body class="sb-nav-fixed" > 
		
		<% 
			HashMap<String, String> collapse = new HashMap<String, String>();
			collapse.put("cadastros-clp","collapsed show");
			collapse.put("ger-alertas-clp","collapse");
			collapse.put("ger-alertas-clped","collapsed show");
		%>
		
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
            <%@include file="../menu/layout_slide_nav.jsp"%>
          		
          		<div id="layoutSidenav_content">
            	<form method="POST" action="/medix/view/equipamento">
				<main>
					<div class="container-fluid">
					
						<% 
							HttpSession ses = request.getSession(true);
							Equipamento equipSessao = (Equipamento) ses.getAttribute("equipamentoParam");
							
							String situacao="A", manutencao="N";
							int idLocal=0;
							
							if(equipSessao != null){
								if(equipSessao.getSituacao() != null && !equipSessao.getSituacao().isEmpty())
									situacao = equipSessao.getSituacao();
								
								if(equipSessao.getManutencao() != null && !equipSessao.getManutencao().isEmpty())
									manutencao = equipSessao.getManutencao();
								
								idLocal = equipSessao.getIdLocalizacao();
								
							}
						%>
					
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Cadastro de Equipamento</li>
	                    </ol>

						<div class="form-row">
							<div class="col-md-2 mb-3">
								<label>Código</label> 
								<input type="text" class="form-control" name="id" readonly value="${equipamentoParam.getId()}">
							</div>
							<div class="col-md-10 mb-3">
								<label>Descrição do Equipamento</label> 
								<input type="text" class="form-control"  name="descricao" value="${equipamentoParam.getDescricao()}">
							</div>
						</div>
						
						<div class="form-row">
							<div class="col-md-6 mb-3">
								<label>Temperatura Mínima Cº</label> 
								<input type="number" class="form-control" name="tempMinima" min="-40" max="80" required="required" value="${equipamentoParam.getTempMinima()}">
							</div>
							<div class="col-md-6 mb-3">
								<label>Temperatura Máxima Cº</label> 
								<input type="number" class="form-control" name="tempMaxima" step="any"  min="-40" max="80" required="required" value="${equipamentoParam.getTempMaxima()}">
							</div>
						</div>
						
						<div class="form-row">
							<div class="col-md-6 mb-3">
								<label>Umidade Mínima %</label> 
								<input type="number" class="form-control" name="umidadeMinima" step="any" min="0" max="100" value="${equipamentoParam.getUmidadeMinima()}">
							</div>
							<div class="col-md-6 mb-3">
								<label>Umidade Máxima %</label> 
								<input type="number" class="form-control" name="umidadeMaxima" step="any"  min="0" max="100"  value="${equipamentoParam.getUmidadeMaxima()}">
							</div>
						</div>
						
						<div class="form-row">
							<%-- <div class="col-md-6 mb-3">
								<label>Localização do Equipamento</label> 
								<input type="text" class="form-control" name="localizacao" value="${equipamentoParam.getLocalizacao()}">
							</div>
							 --%>
							<div class="col-md-6 mb-3">
								<label>Localização</label> 
								<select
									class="custom-select" name="idlocalizacao">
									
									<%
										LocalizacaoDao localDao = new LocalizacaoDao();
										List<Localizacao> lstLocal = localDao.getAll("ie_situacao = 'A'");

										for (Localizacao localList : lstLocal) {
											out.write("<option value='"+localList.getId()+"' "+(idLocal == 0 ? "" : idLocal == localList.getId() ? "selected=''" : "" ) +" >"+localList.getLocalizacao()+"</option>"); 
										}
									%>
									
								</select>
							</div>
							
							<div class="col-md-2 mb-3">
								<label>Endereço MAC</label> 
								<input type="text" class="form-control" name="macCircuito" value="${equipamentoParam.getMacCircuito()}">
							</div>
							
							<div class="col-md-2 mb-3">
								<label for="validationDefault04">Manutenção</label>  
								<select class="custom-select" name="manutencao">							    
								    <% 
								     	  out.write("<option value='S' "+( manutencao.equals("S") ? "selected=''" : "" ) +">Sim</option>"); 
								     	  out.write("<option value='N' "+( manutencao.equals("N") ? "selected=''" : "" ) +">Não</option>");
									 %>
								</select>
							</div>
							
							<div class="col-md-2 mb-3">
								<label for="validationDefault04">Situação</label>  
								<select class="custom-select" name="situacao">							    
								    <% 
								     	  out.write("<option value='A' "+( situacao.equals("A") ? "selected=''" : "" ) +">Ativo</option>"); 
								     	  out.write("<option value='I' "+( situacao.equals("I") ? "selected=''" : "" ) +">Inativo</option>");
									 %>
								</select>
							</div>
						</div>
						
						<button class="btn btn-primary" name="action" value="save" type="submit">Salvar</button>
						<button class="btn btn-primary" onclick="this.href='/medix/view/equipamento'">Cancelar</button>
					</div>
				</main>
			</form>	
			<br>
			<div class="card mb-4" style="margin-left: 20px; margin-right: 20px;">
				<div class="card-header">
					<i class="fas fa-table mr-1"></i>Equipamentos
				</div>

				<div class="card-body">
					<div>
						<table class="table table-bordered" width="100%" cellspacing="0">
							<thead>
								<th>Descrição</th>
								<th>Situação</th>
								<th>Localização</th>
								<th>MAC</th>
								<th>Editar</th>
								<th>Excluir</th>
							</thead>
							<tbody>

							   <%
								EquipamentoDao dao = new EquipamentoDao();
								List<Equipamento> lista = dao.getAll();

								for (Equipamento f : lista) {
								%>

								<tr>
									<td><%=f.getDescricao()%></td>
									<td><%=(f.getSituacao().equals("A") ? "Ativo" : "Inativo")%></td>
									<td><%=f.getLocalizacao()%></td>
									<td><%=f.getMacCircuito()%></td>

									<td align="center"><a
										onclick="this.href='/medix/view/equipamento?action=alt&id='+<%=f.getId()%>">
											<i class="fas fa-user fa-edit"></i>
									</a></td>

									<td align="center"><a
										onclick="this.href='/medix/view/equipamento?action=del&id='+<%=f.getId()%>">
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