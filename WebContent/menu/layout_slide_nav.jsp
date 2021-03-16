<%@page import="util.function"%>
<div id="layoutSidenav_nav">
                <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                    <div class="sb-sidenav-menu">
                        <div class="nav">
                             
                            <div class="sb-sidenav-menu-heading">Principal</div>
        					<a class="nav-link" href="/medix/view/dashboard.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                                Dashboard
                            </a>
                                                    
                            <div class="sb-sidenav-menu-heading">Operador</div>
        					<a class="nav-link" href="/medix/view/registro.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-temperature-low"></i></div>
                                Registros
                            </a>
                            
                            <a class="nav-link" href="/medix/view/relatorio.jsp">
                                <div class="sb-nav-link-icon"><i class="fas fa-chart-pie"></i></div>
                                Relatórios
                            </a>
						
						<%if(String.valueOf(session.getAttribute("papelUsuarioLogado")).equals("S")){ %>
							<div class="sb-sidenav-menu-heading">Supervisor</div>	
							
							<%
	                           out.write("<a class='nav-link "+collapse.get("cadastros-clped")+"' href='#' data-toggle='collapse' data-target='#collapseLayouts' aria-expanded='false' aria-controls='collapseLayouts'>"+
	                        		   "<div class='sb-nav-link-icon'><i class='fas fa-columns'></i></div>"+
	                                	"Cadastros"+
	                                "<div class='sb-sidenav-collapse-arrow'><i class='fas fa-angle-down'></i></div>"+
	                            "</a>"); 
	                         %>  
													
                           <%
                             out.write("<div class='"+collapse.get("cadastros-clp")+"' id='collapseLayouts' aria-labelledby='headingOne' data-parent='#sidenavAccordion'>"); 
                           %>  
                             
                               <nav class="sb-sidenav-menu-nested nav">
                                   <a class="nav-link" href="/medix/view/usuario.jsp">Usuário</a>
                                   <a class="nav-link" href="/medix/view/equipamento.jsp">Equipamento</a>
                                   <a class="nav-link" href="/medix/view/gateway.jsp">Gateway</a>
                                   <a class="nav-link" href="/medix/view/localizacao.jsp">Localização</a>
                               </nav>
                          </div>
                            
                          <%
	                           out.write("<a class='nav-link "+collapse.get("ger-alertas-clped")+"' href='#' data-toggle='collapse' data-target='#gerenciadorAlertas' aria-expanded='false' aria-controls='collapseLayouts'>"+
	                        		   "<div class='sb-nav-link-icon'><i class='fas fa-bell'></i></div>"+
	                                	"Ger. de Alertas"+
	                                "<div class='sb-sidenav-collapse-arrow'><i class='fas fa-angle-down'></i></div>"+
	                            "</a>"); 
	                          
	                      out.write("<div class='"+collapse.get("ger-alertas-clp")+"' id='gerenciadorAlertas' aria-labelledby='headingOne' data-parent='#sidenavAccordion'>"); 
                          %>  
                         
                       		<nav class="sb-sidenav-menu-nested nav">
                                <a class="nav-link" href="/medix/view/gerenciador-config.jsp">Configuração</a>
                                <a class="nav-link" href="/medix/view/gerenciador-alertas.jsp">Alertas</a>
                            </nav>
                         </div>
                         <%}%>
                         
                        </div>
                    </div>
                    
                    <div class="sb-sidenav-footer">
                        <div class="small">Usuário Conectado:</div>
                        <%out.write(function.initCap(String.valueOf(session.getAttribute("usuarioLogado"))));%>
                    </div>
                    
                </nav>
 </div>