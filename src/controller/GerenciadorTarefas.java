package controller;

import java.util.Timer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import dao.AlertaDao;
import dao.ParametrosGerenciadorDao;
import dao.RegistroDao;
import dao.UsuarioDao;
import model.Alerta;
import model.Email;
import model.Equipamento;
import model.Registro;
import model.Telegram;
import model.Usuario;

import java.util.List;
 

public class GerenciadorTarefas extends HttpServlet{
	
	private AlertaDao alertaDao = new AlertaDao();
	private RegistroDao regDao = new RegistroDao();
	private UsuarioDao usuDao = new UsuarioDao();
	private ParametrosGerenciadorDao paramDao = new ParametrosGerenciadorDao();
	
    public void init(ServletConfig config) throws ServletException{
    	try {
	    	new Telegram().iniciaBot();
	    	GerenciadorAlertas tarefa = new GerenciadorAlertas();
	        Timer timer = new Timer();        
	        timer.schedule(tarefa, 0, (60000)*paramDao.getAll("").getFrequenciaAtualizacao());
    	}catch(Exception e) {
    		System.out.println("erro ao iniciar gerenciador: "+e.getMessage());
    	}
    }
    
    public void init() throws ServletException{
    	try {
	    	new Telegram().iniciaBot();
	    	GerenciadorAlertas tarefa = new GerenciadorAlertas();
	        Timer timer = new Timer();        
	        timer.schedule(tarefa, 0, (60000)*paramDao.getAll("").getFrequenciaAtualizacao());
    	}catch(Exception e) {
    		System.out.println("erro ao iniciar gerenciador: "+e.getMessage());
    	}
    }
    
    public class GerenciadorAlertas extends java.util.TimerTask {
    	int nrMinutos=10;
        @Override
        public void run() {
        	System.out.println("open gerenciadore");
        	List<Alerta> lstAlertas = alertaDao.getAll("ie_situacao = 'A' and (ie_monitor_temp = 'S' or ie_monitor_umidade='S')");
        	for(Alerta alerta : lstAlertas){
        		String where;
        		
        		where = " (equipamento.nr_sequencia = "+alerta.getIdEquipamento()+" or 0 = "+alerta.getIdEquipamento()+")";
        		where+= " and(equipamento.nr_seq_localizacao = "+alerta.getIdLocalizacao()+" or 0 = "+alerta.getIdLocalizacao()+")";
        		
        		List<Registro> listaDesvio = regDao.getRegistroDesvioMinuto(where, nrMinutos);
        		
        		for(Registro reg : listaDesvio){
        			System.out.println("\n");
        			System.out.println("Equipamento '"+reg.getNomeEquipamento()+"' com desvio!");
        			List<Usuario> lstUsuario = usuDao.getAll("ie_situacao = 'A' and (nr_sequencia = "+alerta.getIdUsuario()+" or 0="+alerta.getIdUsuario()+")");
        			for(Usuario us : lstUsuario) {
        				System.out.println(us.toString());
        				if(alerta.getAlertaEmail().equals("S") && !us.getEmail().isEmpty()) {
        					Email.enviar(us.getEmail(), "Alerta - Equipamento "+reg.getNomeEquipamento(), obterMensagem("EMAIL", "DESVIO", reg, null));
        				}
        				if(alerta.getAlertaTelegram().equals("S") && us.getIdUserTelegram()>0){
        					new Telegram().enviarMensagem(us.getIdUserTelegram(), obterMensagem("TELEGRAM", "DESVIO", reg, null));
        				}
        			}
        		}
        		
        		List<Equipamento> listEquipParado = regDao.getEquipParadoMinuto(where, nrMinutos);
        		for(Equipamento equip : listEquipParado) {
        			System.out.println("\n");
        			System.out.println("Equipamento '"+equip.getDescricao()+"' parado!");
        			List<Usuario> lstUsuario = usuDao.getAll("ie_situacao = 'A' and (nr_sequencia = "+alerta.getIdUsuario()+" or 0="+alerta.getIdUsuario()+")");
        			for(Usuario us : lstUsuario) {
        				System.out.println(us.toString());
        				if(alerta.getAlertaEmail().equals("S") && !us.getEmail().isEmpty()) {
        					Email.enviar(us.getEmail(), "Alerta - Equipamento "+equip.getDescricao(), obterMensagem("EMAIL", "PARADO", null, equip));
        				}
        				if(alerta.getAlertaTelegram().equals("S") && us.getIdUserTelegram()>0){
        					new Telegram().enviarMensagem(us.getIdUserTelegram(), obterMensagem("TELEGRAM", "PARADO", null, equip));
        				}
        			}
        		}
        		
        	}
        }
    
        
        
        public String obterMensagem(String tipoAlerta, String desvioParado, Registro d, Equipamento e) {
        	String mensagem="";
        	
        	if(desvioParado.equals("DESVIO")) {		
        		if(tipoAlerta.equals("EMAIL")) {
					mensagem="<h3>Desvio de ";
							
					if(d.getDesvioTemp().equals("S")) {
						mensagem+="Temperatura";
						
						if(d.getDesvioUmidade().equals("S"))
							mensagem+=" e Umidade";
					}else if(d.getDesvioUmidade().equals("S")) {
						mensagem+="Umidade";
					}
					
					mensagem+="</h3>";
					mensagem+="<br>";
					mensagem+="O equipamento '"+d.getNomeEquipamento()+"' do local '"+d.getLocalizacao()+"' não se encontra em manutenção e recebeu valores fora do ideal, favor verificar!";
					mensagem+="<br>";
					mensagem+="Temperatura: "+d.getTemperatura();
					mensagem+="<br>";
					mensagem+="Umidade: "+d.getUmidade();
					mensagem+="<br>";
					mensagem+="<br>";
					mensagem+="<i><small>Suporte Medix,";
					mensagem+="<br>";
					mensagem+="E-mail automático</small></i>";
					
        		}else if(tipoAlerta.equals("TELEGRAM")) {
        			mensagem="Desvio de ";
					
					if(d.getDesvioTemp().equals("S")) {
						mensagem+="Temperatura";
						
						if(d.getDesvioUmidade().equals("S"))
							mensagem+=" e Umidade";
					}else if(d.getDesvioUmidade().equals("S")) {
						mensagem+="Umidade";
					}
					mensagem+="\nO equipamento '"+d.getNomeEquipamento()+"' do local '"+d.getLocalizacao()+"' não se encontra em manutenção e recebeu valores fora do ideal, favor verificar!";
        		}
        	}else if(desvioParado.equals("PARADO")) {
        		if(tipoAlerta.equals("EMAIL")) {
        			mensagem="<h3>Equipamento Parado!</h3>";
        			mensagem+="<br>";
        			mensagem+= "Nenhum registro do equipamento '"+e.getDescricao()+"' localizado em '"+e.getLocalizacao()+"' foi encontrado nos últimos 10 minutos, favor verificar!";
        			mensagem+="<br>";
					mensagem+="<br>";
					mensagem+="<i><small>Suporte Medix,";
					mensagem+="<br>";
					mensagem+="E-mail automático</small></i>";
        		}else if(tipoAlerta.equals("TELEGRAM")){
        			mensagem= "Nenhum registro do equipamento '"+e.getDescricao()+"' localizado em '"+e.getLocalizacao()+"' foi encontrado nos últimos 10 minutos, favor verificar!";
        		}
        	}
			return mensagem;
        }
        
        
        public String obterMensagem2(String tipoAlerta, Registro d, Equipamento e) {
        	String mensagem="";
        			
        	if(tipoAlerta.equals("EMAIL")){
        		if(d.getManutencao().equals("N")){
        			if(d.getDesvioTemp().equals("S") || d.getDesvioUmidade().equals("S")) {

						mensagem="<h3>Desvio de ";
								
						if(d.getDesvioTemp().equals("S")) {
							mensagem+="Temperatura";
							
							if(d.getDesvioUmidade().equals("S"))
								mensagem+=" e Umidade";
						}else if(d.getDesvioUmidade().equals("S")) {
							mensagem+="Umidade";
						}
						
						mensagem+="</h3>";
						mensagem+="<br>";
						mensagem+="O equipamento '"+d.getNomeEquipamento()+"' do local '"+d.getLocalizacao()+"' não se encontra em manutenção e recebeu valores fora do ideal, favor verificar!";
						mensagem+="<br>";
						mensagem+="Temperatura: "+d.getTemperatura();
						mensagem+="<br>";
						mensagem+="Umidade: "+d.getUmidade();
						mensagem+="<br>";
						mensagem+="<br>";
						mensagem+="<i><small>Suporte Medix,";
						mensagem+="<br>";
						mensagem+="E-mail automático</small></i>";
					}
				}
			}else if(tipoAlerta.equals("TELEGRAM")){
				mensagem= "Nenhum registro do equipamento '"+e.getDescricao()+"' localizado em '"+e.getLocalizacao()+"' foi encontrado nos últimos 10 minitos, favor verificar!";
			}
			
			return mensagem;
        }
        
    }
    
}