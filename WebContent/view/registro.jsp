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
		
		<% 	String tipoUsuario= String.valueOf(session.getAttribute("papelUsuarioLogado"));
			HttpSession ses = request.getSession(true);
		
			HashMap<String, String> collapse = new HashMap<String, String>();
			collapse.put("cadastros-clp","collapse");
			collapse.put("cadastros-clped","collapsed show");
			collapse.put("ger-alertas-clp","collapse");
			collapse.put("ger-alertas-clped","collapsed show");
			
			EquipamentoDao equipDao = new EquipamentoDao();
			List<Equipamento> lstEquip = equipDao.getAll();
			
		%>
		
		<%@include file="../menu/nav_menu.jsp"%>
        
        <div id="layoutSidenav">
       			
       			<%@include file= "../menu/layout_slide_nav.jsp" %>
          		
          		<div id="layoutSidenav_content">
          		
          		<%if(tipoUsuario.equals("S")){ %>
            	<form method="POST" action="/medix/view/registro">
				<main>
					<div class="container-fluid">
					
						<% 
							
							Registro registroSessao = (Registro) ses.getAttribute("registroParam");
							
							String dtReg="",dtModif="";
							int idEquip=0;
						    
							if(registroSessao != null){
								dtReg = function.toDate(registroSessao.getDtRegistro(),"dd/MM/yyyy HH:mm:ss");
								dtModif = function.toDate(registroSessao.getDtModificacao(),"dd/MM/yyyy HH:mm:ss");
								idEquip = registroSessao.getIdEquipamento();
							}

						%>
					
						<ol class="breadcrumb mb-4 mt-4">
                           <li class="breadcrumb-item"><a href="/medix/view/dashboard.jsp">Dashboard</a></li>
                           <li class="breadcrumb-item active">Registro</li>
	                    </ol>

						<div class="form-row">
							<div class="col-md-4 mb-3">
								<label>Código</label> 
								<input type="text" class="form-control" name="id" readonly value="${registroParam.getId()}">
							</div>
							<div class="col-md-5 mb-3">
								<label>Usuário Registro</label> 
								<input type="text" class="form-control" name="usuarioRegistro" readonly value="${registroParam.getUsuarioRegistro()}">
							</div>
							<div class="col-md-3 mb-3">
								<label>Data Registro</label> 
								<% 
									out.write("<input type='text' class='form-control' name='dtRegistro' readonly value='"+dtReg+"'>"); 
								%>
							</div>
						</div>
						
						<div class="form-row">
							<div class="col-md-2 mb-3">
								<label>Desvio Cº</label> 
								<input type="text" class="form-control" name="desvioTemp" readonly value="${registroParam.getDesvioTemp()}">
							</div>
								
							<div class="col-md-2 mb-3">
								<label>Desvio U%</label> 
								<input type="text" class="form-control" name="desvioUmidade" readonly value="${registroParam.getDesvioUmidade()}">
							</div>
								
							<div class="col-md-5 mb-3">
							
								<label>Usuário Modificação</label> 
								<input type="text" class="form-control" name="usuarioModificacao" readonly value="${registroParam.getUsuarioModificacao()}">
							</div>
							<div class="col-md-3 mb-3">
								<label>Data Modificação</label> 
								<% 
									out.write("<input type='text' class='form-control' name='dtModificacao' readonly value='"+dtModif+"'>"); 
								%>
							</div>
						</div>
						
						<div class="form-row">
						
							<div class="col-md-12 mb-3">
								<label>Equipamento</label> 
								<select
									class="custom-select" name="idEquipamento">
									
									<%
										for (Equipamento equipList : lstEquip) {
											out.write("<option value='"+equipList.getId()+"' "+(idEquip == 0 ? "" : idEquip == equipList.getId() ? "selected=''" : "" ) +" >"+equipList.getDescricao()+"</option>"); 
										}
									%>
									
								</select>
							</div>
						</div> 
						
						<div class="form-row">
							<div class="col-md-6 mb-3">
								<label>Temperatura Cº</label> 
								<input type="number" class="form-control" name="temperatura" step="any" min="-40" max="80" required="required" value="${registroParam.getTemperatura()}">
							</div>
							<div class="col-md-6 mb-3">
								<label>Umidade %</label> 
								<input type="number" class="form-control" name="umidade" step="any"  min="0" max="100" value="${registroParam.getUmidade()}">
							</div>
						</div>
						
						<div class="form-row">
							<div class="col-md-12 mb-3">
								<label>Observação</label> 
								<input type="text" class="form-control" name="observacao" value="${registroParam.getObservacao()}">
							</div>
						</div>
						
						<button class="btn btn-primary" name="action" value="save" type="submit">Salvar</button>
						<button class="btn btn-primary" onclick="this.href='/medix/view/registro'">Cancelar</button>
						
					</div>
				</main>
			</form>	
			
			<%}%>	
			<form method="POST" action="/medix/view/registro">
				<main>
				<br>
				<div class="card mb-4" style="margin-left: 20px; margin-right: 20px;">
						<div class="card-header">
							<i class="fas fa-table mr-1"></i>Registros
							<br><br>
							<div class="form-row">
								<div class="col-md-2 mb-2">
									<label>Dt. Inicial</label> 
									<% 
										if(ses.getAttribute("filtroDtInicial")==null){
											ses.setAttribute("filtroDtInicial", function.getDate("yyyy-MM-dd"));
										}
									    out.write("<input type='date' class='form-control' name='filtroDtInicial' value='"+ses.getAttribute("filtroDtInicial")+"'>"); 
									%>
								</div>
								<div class="col-md-2 mb-2">
									<label>Dt. Final</label> 
									<% 
										if(ses.getAttribute("filtroDtFinal")==null){
											ses.setAttribute("filtroDtFinal", function.getDate("yyyy-MM-dd"));
										}
									    out.write("<input type='date' class='form-control' name='filtroDtFinal' value='"+ses.getAttribute("filtroDtFinal")+"'>"); 
									%>
								</div>
								<div class="col-md-4 mb-2">
									<label>Equipamento</label> 
									<select class="custom-select" name="filtroIdEquipamento">
									<option value=""></option>
									<%
										if(ses.getAttribute("filtroIdEquipamento")==null){
											ses.setAttribute("filtroIdEquipamento", "");
										}
									
										for (Equipamento equipList : lstEquip) {
											out.write("<option value='"+equipList.getId()+"' "+(ses.getAttribute("filtroIdEquipamento").toString().equals("") ? "" : ses.getAttribute("filtroIdEquipamento").toString().trim().equals(String.valueOf(equipList.getId())) ? "selected=''" : "" ) +" >"+equipList.getDescricao()+"</option>");			
										}
									%>
									</select>
								</div>
								
								<div class="col-md-3 mb-2">
									<div class="custom-control custom-checkbox">
									    <%
										    if(ses.getAttribute("filtroDesvioTemp")==null){
												ses.setAttribute("filtroDesvioTemp", "N");
											}
									    	out.write("<input type='checkbox' "+(ses.getAttribute("filtroDesvioTemp").toString().equals("S") ? "checked":"")+" class='custom-control-input' value='S' name='filtroDesvioTemp' id='filtroDesvioTemp'>");
									    %>
										<br>
									    <label class="custom-control-label" for="filtroDesvioTemp">Apenas Desvio Temperatura</label>
									</div>
									<div class="custom-control custom-checkbox">
									    <%
										    if(ses.getAttribute("filtroDesvioUmidade")==null){
												ses.setAttribute("filtroDesvioUmidade", "N");
											}
									    	out.write("<input type='checkbox' "+(ses.getAttribute("filtroDesvioUmidade").toString().equals("S") ? "checked":"")+" class='custom-control-input' value='S' name='filtroDesvioUmidade' id='filtroDesvioUmidade'>");
									    %>
										
									    <label class="custom-control-label" for="filtroDesvioUmidade">Apenas Desvio Umidade</label>
									</div>
							 	</div>
						
								<div class="col-md-1 mb-2">
									<label class="col-md-1 mb-4"> </label>
									<button class="btn btn-primary form-control" name="action" value="pesquisa" type="submit">Pesquisar</button>
								</div>
							</div>

						</div>
						<div class="card-body">
							<div>
								<table class="table table-bordered" width="50%" cellspacing="0">
									<thead>
										<th>Dt. Registro</th>
										<th>Temp Cº</th>
										<th>Desvio</th>
										<th>Umidade %</th>
										<th>Desvio</th>
										<th>Equipamento</th>
										<%if(tipoUsuario.equals("S")){ %>
											<th>Editar</th>
											<th>Excluir</th>
										<%}%>
									</thead>
									<tbody>
		
									<%
										RegistroDao dao = new RegistroDao();
										String dtInicial = ses.getAttribute("filtroDtInicial").toString();
										String dtFinal = ses.getAttribute("filtroDtFinal").toString();
										String idEquipamento = ses.getAttribute("filtroIdEquipamento").toString();
										String apenasDesvioTemp = ses.getAttribute("filtroDesvioTemp").toString();
										String apenasDesvioUmidade = ses.getAttribute("filtroDesvioUmidade").toString();
										
										String param="";
										
										if(!dtInicial.isEmpty() && !dtFinal.isEmpty())
											param="TO_CHAR(dt_registro,'yyyy-mm-dd') between '"+dtInicial+"' and '"+dtFinal+"'";
											param+="and ((ie_desvio_temp = '"+apenasDesvioTemp+"') or 'N' = '"+apenasDesvioTemp+"')";
											param+="and ((ie_desvio_umidade = '"+apenasDesvioUmidade+"') or 'N' = '"+apenasDesvioUmidade+"')";
											
										 if(!idEquipamento.isEmpty())
											if(param.isEmpty()){
												param="nr_seq_equipamento = "+idEquipamento;
											}else{
												param+=" and nr_seq_equipamento = "+idEquipamento;
											} 

										List<Registro> lista = dao.getAll(param,"nr_sequencia desc");
										
										for (Registro f : lista) {
									%>
		
										<tr>
											<td><%=function.toDate(f.getDtRegistro(), "dd/MM/yyyy HH:mm:ss")%></td> 
											<td><%=f.getTemperatura()%></td>
											<td><%=f.getDesvioTemp()%></td>
											<td><%=f.getUmidade()%></td>
											<td><%=f.getDesvioUmidade()%></td>
										 	<td><%=f.getNomeEquipamento()%></td>
										 	<%if(tipoUsuario.equals("S")){ %>
												<td align="center"><a
													onclick="this.href='/medix/view/registro?action=alt&id='+<%=f.getId()%>">
														<i class="fas fa-user fa-edit"></i>
												</a></td>
			
												<td align="center"><a name="excluirForn"
													onclick="this.href='/medix/view/registro?action=del&id='+<%=f.getId()%>">
														<i class="fas fa-user fa-trash-alt"></i>
												</a></td>
											<%}%>
										</tr>
										<%
											}
										%>
		
									</tbody>
								</table>
							</div>
						</div>
			  </div>
		  </main>	  
		  </form>   
          </div>  
        </div>    
        <%@include file="../menu/scripts.jsp"%>
    </body>
</html>