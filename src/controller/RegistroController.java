package controller;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.postgresql.util.PSQLException;

import dao.Dao;
import dao.RegistroDao;
import dao.EquipamentoDao;
import dao.UsuarioDao;
import model.Equipamento;
import model.Registro;
import model.Usuario;
import util.function;

 
@WebServlet( urlPatterns = {"/view/registro"})
public class RegistroController extends HttpServlet{

    private static final long serialVresionId = 1L;
    private RegistroDao dao = new RegistroDao();
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	HttpSession session = req.getSession();
    	session.removeAttribute("registroParam");
    	
    	String acao = req.getParameter("action");
		String id = req.getParameter("id");
				
		id = id == null ? "" : (id.equals("0") ? "" : id);
		
		if(acao != null) {
			switch(acao) {
				case "save":
					
					Registro f = obterRegistro(req);
					
					if(f == null)
						break;
					
					if(id != null && !id.isEmpty()) { //Update
						f.setId(Integer.parseInt(id));
						dao.update(f);
						
					}else { //Insert
						dao.insert(f);
					}
					
					break;
					
				case "del":
					if(id != null && !id.isEmpty())
						dao.delete(Integer.valueOf(id));
					break;
					
				case "alt":
					if(id != null && !id.isEmpty()) {
						Registro registro = dao.getRegistro(Integer.valueOf(id));
						session.setAttribute("registroParam", registro);

					}
					break;
					
				case "pesquisa":
					session.setAttribute("filtroDtInicial", req.getParameter("filtroDtInicial"));
					session.setAttribute("filtroDtFinal", req.getParameter("filtroDtFinal"));
					session.setAttribute("filtroIdEquipamento", req.getParameter("filtroIdEquipamento"));
					session.setAttribute("filtroDesvioTemp", function.obterSeVazio(req.getParameter("filtroDesvioTemp"),"N"));
					session.setAttribute("filtroDesvioUmidade", function.obterSeVazio(req.getParameter("filtroDesvioUmidade"),"N"));
					
					
			}
		}
		
		req.getRequestDispatcher("registro.jsp").forward(req,resp);
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    private Registro obterRegistro(HttpServletRequest req) {
    	Registro f = new Registro();
    	
		if(req.getParameter("temperatura").equals("nan")||req.getParameter("temperatura").equals("nan")){
			return null;
		}else {	
			EquipamentoDao Equipdao = new EquipamentoDao();
			
	    	if(!function.obterSeVazio(req.getParameter("idEquipamento")).isEmpty()) {
				f.setIdEquipamento(Integer.parseInt(function.obterSeVazio(req.getParameter("idEquipamento"))));
				Equipamento equip = Equipdao.getEquipamento(f.getIdEquipamento());
				f.setNomeEquipamento(equip.getDescricao());
				f.setManutencao(equip.getManutencao());
	    	}else if(!function.obterSeVazio(req.getParameter("macCircuito")).isEmpty()){
	    		
	        	Equipamento equip = Equipdao.getEquipamento("DS_MAC_CIRCUITO", req.getParameter("macCircuito"));
	        	
	        	if(equip != null && equip.getId() != 0) {
	        		f.setIdEquipamento(equip.getId());
	        		f.setNomeEquipamento(equip.getDescricao());
	        		f.setManutencao(equip.getManutencao());
	        	}else {
	        		System.out.println("EQUIPAMENTO NAO ENCONTRADO!!!");
	        		return null;
	        	}
	    	}
			
	    	if(!function.obterSeVazio(req.getParameter("temperatura")).isEmpty()) {
				f.setTemperatura(Float.parseFloat(function.obterSeVazio(req.getParameter("temperatura"))));
			}else {
				f.setTemperatura(0f);
			}
	    	
	    	if(!function.obterSeVazio(req.getParameter("umidade")).isEmpty()) {
				f.setUmidade(Float.parseFloat(function.obterSeVazio(req.getParameter("umidade"))));
			}else {
				f.setUmidade(0f);
			}
	    	
			f.setUsuarioModificacao(String.valueOf(req.getSession().getAttribute("usuarioLogado")));
			f.setUsuarioRegistro(String.valueOf(req.getSession().getAttribute("usuarioLogado")));
			f.setObservacao(req.getParameter("observacao"));
			f.setDesvioTemp(obterSeDesvio(f.getTemperatura(), f.getIdEquipamento(), "T"));
			f.setDesvioUmidade(obterSeDesvio(f.getUmidade(), f.getIdEquipamento(), "U"));
			
	    	return f;
	    }
    }
    

    private String obterSeDesvio(Float valor, int idEquipamento, String tipo) {
    	EquipamentoDao Equipdao = new EquipamentoDao();
    	Equipamento equip = Equipdao.getEquipamento(idEquipamento);
    	
    	if(tipo.equals("T"))
    		return (valor >= equip.getTempMinima() && valor <=  equip.getTempMaxima() ? "N" : "S");
    	
    	if(tipo.equals("U")) {
    		if(equip.getUmidadeMinima() != 0f || equip.getUmidadeMaxima() != 0f) 
    			return (valor >= equip.getUmidadeMinima() && valor <=  equip.getUmidadeMaxima() ? "N" : "S");
    	}
    	
    	return "N";
    
    }
}
