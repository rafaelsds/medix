<%@page import="java.util.HashMap"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="model.Usuario"%>
<%@page import="model.Localizacao"%>
<%@page import="dao.LocalizacaoDao"%>
<%@page import="dao.ParametrosGerenciadorDao"%>
<%@page import="model.GerenciadorAlertaConfig"%>
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
			collapse.put("ger-alertas-clp","collapsed show");
			collapse.put("cadastros-clp","collapse");
			collapse.put("cadastros-clped","collapsed show");
 		%>
		
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
       			
       			<%@include file= "../menu/layout_slide_nav.jsp" %>
          		
          		<div id="layoutSidenav_content">
            	<form method="POST" action="/medix/view/gerenciador-config">
				<main>
					<div class="container-fluid">
										
						<% 
							HttpSession ses = request.getSession(true);
							ParametrosGerenciadorDao dao = new ParametrosGerenciadorDao();
							
							GerenciadorAlertaConfig gerenciadorSessao = dao.getAll("");
							
							String situacao="A",situacaoBot="A", situacaoGerenciador="A";
						    
							if(gerenciadorSessao != null){
								if(gerenciadorSessao.getSituacaoEmail() != null && !gerenciadorSessao.getSituacaoEmail().isEmpty())
									situacao = gerenciadorSessao.getSituacaoEmail();
								
								if(gerenciadorSessao.getSituacaoBot() != null && !gerenciadorSessao.getSituacaoBot().isEmpty())
									situacaoBot = gerenciadorSessao.getSituacaoBot();
								
								if(gerenciadorSessao.getSituacaoGerenciador() != null && !gerenciadorSessao.getSituacaoGerenciador().isEmpty())
									situacaoGerenciador = gerenciadorSessao.getSituacaoGerenciador();

							}
						%>
										
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Gerenciador de Alertas</li>
	                    </ol>

											
						<div class="card">
							<div class="card-header">
								<ul class="nav nav-pills">
									<li class='nav-item'><a class='nav-link active' data-toggle='pill' href='#pagEmail'>E-mail</a></li>
									<li class='nav-item'><a class='nav-link' data-toggle='pill' href='#pagTelegram'>Telegram</a></li>
									<li class='nav-item'><a class='nav-link' data-toggle='pill' href='#pagPeriodo'>Gerenciador</a></li>
								</ul>
							</div>	
							
							<input type="hidden"  name="id" value="<%=gerenciadorSessao.getId()%>">
							
							<div class="tab-content mt-3" style="padding:15px;">
   		 						<div id='pagEmail' class='tab-pane fade show active'>
  		 							<div class="form-row">
										<div class="col-md-4 mb-3">
											<label>Servidor SMTP</label> 
											<input type="text" class="form-control" required="required" name="servidorSmtp" placeholder="smtp.gmail.com" value="<%=gerenciadorSessao.getServidorSmtp()%>">
										</div>
										<div class="col-md-2 mb-3">
											<label>Porta</label> 
											<input type="text" class="form-control" required="required" name="portaEmail" placeholder="465" value="<%=String.valueOf(gerenciadorSessao.getPortaEmail())%>">
										</div>
										<div class="col-md-2 mb-3">
											<label for="validationDefault04">Situação</label> 
											<select class="custom-select" name="situacaoEmail">							    
											    <% 
											     	  out.write("<option value='A' "+( situacao.equals("A") ? "selected=''" : "" ) +">Ativo</option>"); 
											     	  out.write("<option value='I' "+( situacao.equals("I") ? "selected=''" : "" ) +">Inativo</option>");
												 %>
											</select>
										</div>
									</div>
									
									<div class="form-row">
										<div class="col-md-4 mb-3">
											<label>E-mail</label> 
											<input type="text" class="form-control" required="required" name="enderecoEmail" placeholder="email@dominio.com" value="<%=gerenciadorSessao.getEnderecoEmail()%>">
										</div>
										<div class="col-md-4 mb-3">
											<label>Senha</label> 
											<input type="password" class="form-control" required="required" name ="senhaEmail" value="<%=gerenciadorSessao.getSenhaEmail()%>">
										</div>
										<div class="col-md-4 mb-3">
											<label>Nome de Exibição</label> 
											<input type="text" class="form-control" name="nomeExibicaoEmail" value="<%=gerenciadorSessao.getNomeExibicaoEmail()%>">
										</div>
									</div>
								</div>
								
								<div id='pagTelegram' class='tab-pane fade'>
   		 							<div class="form-row">
										<div class="col-md-5 mb-3">
											<label>Descrição</label> 
											<input type="text" class="form-control" name="descricaoBot" value="<%=gerenciadorSessao.getDescricaoBot()%>">
										</div>
										<div class="col-md-5 mb-3">
											<label>Token</label> 
											<input type="text" class="form-control"  name="tokenBot" value="<%=String.valueOf(gerenciadorSessao.getTokenBot())%>">
										</div>
										<div class="col-md-2 mb-3">
											<label for="validationDefault04">Situação</label> 
											<select class="custom-select" name="situacaoBot">							    
											    <% 
											     	  out.write("<option value='A' "+( situacaoBot.equals("A") ? "selected=''" : "" ) +">Ativo</option>"); 
											     	  out.write("<option value='I' "+( situacaoBot.equals("I") ? "selected=''" : "" ) +">Inativo</option>");
												 %>
											</select>
										</div>
									</div>
								</div>
								
								<div id='pagPeriodo' class='tab-pane fade'>
   		 							 <div class="form-row">
										<div class="col-md-4 mb-3">
											<label>Frequência de Atualização (Minutos)</label> 
											<input type="number" class="form-control"  name="freqAtualizacao" step="any" min="5" max="1440"  value="<%=String.valueOf(gerenciadorSessao.getFrequenciaAtualizacao())%>">
										</div>
										<div class="col-md-3 mb-3">
											<label for="validationDefault04">Situação</label> 
											<select class="custom-select" name="situacaoGerenciador">							    
											    <% 
											     	  out.write("<option value='A' "+( situacaoGerenciador.equals("A") ? "selected=''" : "" ) +">Ativo</option>"); 
											     	  out.write("<option value='I' "+( situacaoGerenciador.equals("I") ? "selected=''" : "" ) +">Inativo</option>");
												 %>
											</select>
										</div>
									</div> 
								</div>
							</div>
						</div>
						
						<br>
												
						<button class="btn btn-primary" name="action" value="save" type="submit">Salvar</button>

					</div>
				</main>
				</form>
							   
          </div>  
        </div>
        <%@include file="../menu/scripts.jsp"%>
    </body>
</html>