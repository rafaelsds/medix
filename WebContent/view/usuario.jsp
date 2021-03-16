<%@page import="java.util.HashMap"%>
<%@ page import="controller.UsuarioController" %>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>

<%@page import="model.Usuario"%>
<%@page import="dao.UsuarioDao"%>
<%@page import="util.function"%>

<!DOCTYPE html>
<html>
	<%@include file="../menu/head_padrao.jsp"%>

    <script type="text/javascript">
		function validarSenha(){
			var senha1 = formCadUsuario.senha.value; 
			var senha2 = formCadUsuario.senhaConfirmar.value; 
			
			if (senha1 == senha2)
				document.formCadUsuario.submit();
			else
				alert("Senhas não conferem!");
				formCadUsuario.senhaConfirmar.focus();
				return false;
		} 
	</script> 

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
	            	<form id=formCadUsuario method="POST" action="/medix/view/usuario">
						<main>
							<div class="container-fluid">
							
							<% 
								HttpSession ses = request.getSession(true);
								Usuario usarioSessao = (Usuario) ses.getAttribute("usuarioParam");
								
								String papel="", dtNascimento="", situacao="A";
							    
								if(usarioSessao != null){
									if(usarioSessao.getPapelUsuario() != null)
										papel = usarioSessao.getPapelUsuario();
									
									if(usarioSessao.getDataNascimento() != null)
										dtNascimento = function.toDate(usarioSessao.getDataNascimento(),"yyyy-MM-dd");
									
									if(usarioSessao.getSituacao() != null && !usarioSessao.getSituacao().isEmpty())
										situacao = usarioSessao.getSituacao();
								}

							%>
							
								<ol class="breadcrumb mb-4 mt-4">
		                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
		                           <li class="breadcrumb-item active">Cadastro de usuário</li>
			                    </ol>
		
								<div class="form-row">
									<div class="col-md-2 mb-3">
										<label>Código</label> 
										<input type="text" class="form-control" name="id" readonly value="${usuarioParam.getId()}">
									</div>
									<div class="col-md-10 mb-3">
										<label>Nome Completo</label> 
										<input type="text" class="form-control"  name="nomeUsuario" required="required" value="${usuarioParam.getNomeUsuario()}">
									</div>
								</div>
								
								<div class="form-row">
									<div class="col-md-3 mb-3">
										<label>Dt. Nascimento</label> 
										<% 
									    	out.write("<input type='date' class='form-control' name='dataNascimento' value='"+dtNascimento+"'>"); 
										%>
									</div>
									<div class="col-md-3 mb-3">
										<label>Celular</label> 
										<input type="number" class="form-control" min="55"  maxlength="13" name="telefone" placeholder="DDI+DDD+NUMERO" value="${usuarioParam.getTelefone()}">
									</div>
									<div class="col-md-6 mb-3">
										<label>E-mail</label>
										<div class="input-group mb-3">
											<input type="email" class="form-control" name="email" value="${usuarioParam.getEmail()}">
											<div class="input-group-append">
												<span class="input-group-text" id="basic-addon2">@example.com</span>
											</div>
										</div>
									</div>
								</div>
								
								<div class="form-row">
									<div class="col-md-6 mb-3">
										<label>Usuário</label> 
										<input type="text" class="form-control" name="login" required="required" value="${usuarioParam.getLogin()}">
									</div>
									<div class="col-md-3 mb-3">
										<label for="validationDefault04">Papel</label> 
										<select class="custom-select" name="papelUsuario">
											
											<% 
										     	  out.write("<option value='O' "+(papel == null ? "" : papel.equals("O") ? "selected=''" : "" ) +">Operador</option>"); 
										     	  out.write("<option value='S' "+(papel == null ? "" : papel.equals("S") ? "selected=''" : "" ) +">Supervisor</option>");
											 %>
										</select>
									</div>
									<div class="col-md-3 mb-3">
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
									<div class="col-md-6 mb-3">
										<label>Senha</label> 
										<input type="password" class="form-control" id="senha" name="senha" required="required" value="${usuarioParam.getSenha()}">
									</div>
									<div class="col-md-6 mb-3">
										<label>Confirme a Senha</label> 
										<input type="password" class="form-control" id="senhaConfirmar"  required="required" value="${usuarioParam.getSenha()}">
									</div>
								</div>
								
								<button class="btsave btn btn-primary" name="action" value="save" onclick="return validarSenha()">Salvar</button>
								<button class="btn btn-primary" onclick="this.href='/medix/view/usuario'">Cancelar</button>
							</div>
						</main>
					</form>
					
					<!-- <script>
					     $(document).ready(function(){
				                $("#formCadUsuario").submit(function(){
				                      if($("#senha").val() == $("#senhaConfirmar").val()){
				                               return true;
				                      }
				                      return false;
				                });
					     });
					</script> -->
					
	           		<br>
	          		<div class="card mb-4" style="margin-left: 20px; margin-right: 20px;">
					<div class="card-header">
						<i class="fas fa-table mr-1"></i>Usuários
					</div>
	
					<div class="card-body">
						<div>
							<table class="table table-bordered" width="100%" cellspacing="0">
								<thead>
									<th>Código</th>
									<th>Nome</th>
									<th>Dt. Nasc.</th>
									<th>Login</th>
									<th>Papel</th>
									<th>Editar</th>
									<th>Excluir</th>
								</thead>
								<tbody>
	
								    <%
										UsuarioDao dao = new UsuarioDao();
										List<Usuario> lista = dao.getAll();
	
									for (Usuario f : lista) {
									%>
	
									<tr>
										<th id="id" scope="row"><%=f.getId()%></th>
										<td><%=f.getNomeUsuario()%></td>
								 		<td><%=function.toDate(f.getDataNascimento(), "dd/MM/yyyy")%></td> 
										<td><%=f.getLogin()%></td>
										<td><%=f.getPapelUsuario()%></td>
										<td align="center"><a 
											onclick="this.href='/medix/view/usuario?action=alt&id='+<%=f.getId()%>">
												<i class="fas fa-user fa-edit"></i>
										</a></td>
	 
										<td align="center"><a
											onclick="this.href='/medix/view/usuario?action=del&id='+<%=f.getId()%>">
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