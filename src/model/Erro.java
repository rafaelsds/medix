package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
  
public final class Erro
        implements Serializable {
  
    private final List<String> erros;
    private boolean loginIncorreto;
    
    public Erro() {
        erros = new ArrayList<>();
        loginIncorreto=false;
    }
  
    public Erro(String mensagem) {
        erros = new ArrayList<>();
        erros.add(mensagem);
        loginIncorreto=false;
    }
  
    public void setLoginIncorreto(boolean loginInc) {
    	loginIncorreto = loginInc;
    }
    
    public void add(String mensagem) {
        erros.add(mensagem);
    }
     
    public boolean getLoginIncorreto() {
    	return loginIncorreto;
    }
    
    public boolean isExisteErros() {
        return !erros.isEmpty();
    }
     
    public List<String> getErros() {
        return erros;
    }
    
}