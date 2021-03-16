<%@page import="java.util.HashMap"%>
<%@ page import="controller.GatewayController" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page import="model.Localizacao"%>
<%@page import="dao.LocalizacaoDao"%>


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
            	<form method="POST" action="/medix/view/localizacao">
				<main>
					<div class="container-fluid">
					
						<% 
							HttpSession ses = request.getSession(true);
							Localizacao localizacaoSessao = (Localizacao) ses.getAttribute("localizacaoParam");
							
							String situacao="A";
						    
							if(localizacaoSessao != null){
								if(localizacaoSessao.getSituacao() != null && !localizacaoSessao.getSituacao().isEmpty())
									situacao = localizacaoSessao.getSituacao();

							}
						%>
					
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Cadastro de Localização</li>
	                    </ol>

						<div class="form-row">
							<div class="col-md-2 mb-3">
								<label>Código</label> 
								<input type="text" class="form-control" name="id" readonly value="${localizacaoParam.getId()}">
							</div>
							<div class="col-md-6 mb-3">
								<label>Descrição</label> 
								<input type="text" class="form-control"  name="localizacao" value="${localizacaoParam.getLocalizacao()}">
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
						
						<button class="btn btn-primary" name="action" value="save" type="submit">Salvar</button>
						<button class="btn btn-primary" onclick="this.href='/medix/view/localizacao'">Cancelar</button>
					</div>
				</main>
			</form>	
			<br>
			<div class="card mb-4" style="margin-left: 20px; margin-right: 20px;">
				<div class="card-header">
					<i class="fas fa-table mr-1"></i>Localizações
				</div>

				<div class="card-body">
					<div>
						<table class="table table-bordered" width="100%" cellspacing="0">
							<thead>
								<th>Localização</th>
								<th>Situação</th>
								<th>Editar</th>
								<th>Excluir</th>
							</thead>
							<tbody>

							   <%
								LocalizacaoDao dao = new LocalizacaoDao();
								List<Localizacao> lista = dao.getAll();

								for (Localizacao f : lista) {
								%>

								<tr>
									<td><%=f.getLocalizacao()%></td>
									<td><%=(f.getSituacao().equals("A") ? "Ativo" : "Inativo")%></td>
									<td align="center"><a
										onclick="this.href='/medix/view/localizacao?action=alt&id='+<%=f.getId()%>">
											<i class="fas fa-user fa-edit"></i>
									</a></td>

									<td align="center"><a name="excluirForn"
										onclick="this.href='/medix/view/localizacao?action=del&id='+<%=f.getId()%>">
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