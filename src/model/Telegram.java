package model;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;

import dao.ParametrosGerenciadorDao;
import dao.UsuarioDao;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public final class Telegram {
	
	private final String token = new ParametrosGerenciadorDao().getBotToken();
	private TelegramBot bot = new TelegramBot(token);
	private UsuarioDao usuarioDao = new UsuarioDao();
	
	
	public void iniciaBot() {
				
		if(token != null && !token.isEmpty()) {
	    	bot.setUpdatesListener(new UpdatesListener() {
	    	    @Override
	    	    public int process(List<Update> updates) {
	    	    	
	    	    	for(Update up : updates) {
			        	if(up.message().contact() != null) {
			        		
			        		System.out.println("\n");
			        		System.out.println(up.message().contact());
			        		System.out.println("id="+up.message().contact().userId()+"\t phone: "+up.message().contact().phoneNumber());
			        		
			        		String mensagem="";
			        		
			        		if(up.message().contact().userId() != null){
			        			
			        			if(!up.message().contact().userId().equals(up.message().from().id())) {
			        				mensagem="Contato Inválido!\nApenas o titular do número possui permissão para enviar o próprio contato!";
			        			}else {
			        				
			        				String nrTelefone= up.message().contact().phoneNumber().replace("+", "").replace(" ", "");
			        				List<Usuario> lstUsuarios = usuarioDao.getAll("ie_situacao = 'A' and nr_telefone = '"+nrTelefone+"'");
					        				
			        				if(lstUsuarios.size()>0) {
			        					
			        					for(Usuario usu : lstUsuarios) {
				        					if(usu.getIdUserTelegram() >0) {
				        						mensagem+="O seu contato já está vinculado ao usuário "+usu.getLogin()+" no sistema Medix!";
				        					}else {
				        						try {
				        							usuarioDao.updateUserIdTelegram(usu.getId(), up.message().contact().userId());
				        							mensagem+="Seu contato foi vinculado com sucesso ao usuário "+usu.getLogin()+" no sistema medix!";
				        						}catch (Exception e) {
				        							mensagem+="Erro ao vincular contato!\nEntre em contato com o administrador do sistema!";
												}
				        						
				        					}
				        				}
			        					
			        				}else {
			        					mensagem="Não encontramos seu número em nossa base de dados.\nEntre em contato com o administrador do sistema!";
			        				}
			        			}
			        			
			        		}else {
			        			mensagem="Número de Telefone Inválido!\nPor favor verifique se está no formato correto (DDI DDD NUMERO)";
			        		}
			        		
			        		bot.execute(new SendMessage(up.message().from().id(), mensagem));
			        		
			        	}else {
			        		System.out.println("\nSem contato");
			        		System.out.println("id="+up.message().from().id()+" - "+up.message().from().firstName()+": "+up.message().text());
			        	}
			
			    	}
	    	        
	    	        return UpdatesListener.CONFIRMED_UPDATES_ALL;
	    	    }
	    	});
		}
	}
	
	public void desligaBot() {
		bot.removeGetUpdatesListener();
	}
	
	public void enviarMensagem(int idUser, String mensagem) {
		bot.execute(new SendMessage(idUser, mensagem));
	}
}