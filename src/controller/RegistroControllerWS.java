package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
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

 
@WebServlet( urlPatterns = {"/registro"})
public class RegistroControllerWS extends HttpServlet{

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
					
					if(id == null || id.isEmpty()) {
						dao.insert(f);
					}
					
					break;
					
			}
		}
		
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
        
    }
	
    
    private Registro obterRegistro(HttpServletRequest req) {
    	Registro f = new Registro();
    	
    	System.out.println("MAC="+req.getParameter("macCircuito"));
    	System.out.println("temp="+req.getParameter("temperatura"));
    	System.out.println("umidade="+req.getParameter("umidade"));
    	
		if(req.getParameter("temperatura").equals("nan")||req.getParameter("temperatura").equals("nan")){
			return null;
		}else {	
	    	if(!function.obterSeVazio(req.getParameter("idEquipamento")).isEmpty()) {
				f.setIdEquipamento(Integer.parseInt(function.obterSeVazio(req.getParameter("idEquipamento"))));
	    	}else if(!function.obterSeVazio(req.getParameter("macCircuito")).isEmpty()){
	    		EquipamentoDao Equipdao = new EquipamentoDao();
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
	    	
			f.setUsuarioModificacao("medix");
			f.setUsuarioRegistro("medix");
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
