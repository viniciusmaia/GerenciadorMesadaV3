/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.dao.GastoDAO;
import br.ifes.gerenciadormesada.dao.MesadaDAO;
import br.ifes.gerenciadormesada.dao.PatrocinadorDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Gasto;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ManipulaDataAtual;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author 20101bsi0267
 */
@ManagedBean(name = "loginBean")
@SessionScoped
public class LoginBean {

    private String login;
    private String senha;
    private final PatrocinadorDAO daoPatrocinador;
    private final BeneficiadoDAO daoBeneficiado;

    /**
     * Creates a new instance of LoginBean
     */
    public LoginBean() {
        daoBeneficiado = new BeneficiadoDAO();
        daoPatrocinador = new PatrocinadorDAO();
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the senha
     */
    public String getSenha() {
        return senha;
    }

    /**
     * @param senha the senha to set
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void logar()
    {
        //Tente buscar um beneficiado
        Beneficiado beneficiado = daoBeneficiado.buscaPorLogin(this.login);
        
        
        //Verifica se encontrou um beneficiado
        if (beneficiado != null)
        {
            //Caso o beneficiado exista no sistema, verifica se a senha foi inserida corretamente
            if(beneficiado.getSenha().equals(this.senha))
            {
                //Caso os dados estejam corretos, efetua o login do beneficiado
                this.logaBeneficiado(beneficiado);
            }
            else
            {
                EscreveMensagem.escreveErro("Senha incorreta");
            }
        }
        
        //Se não foi encontrado um beneficiado, tenta encontrar um patrocinador
        Patrocinador patrocinador = daoPatrocinador.buscaPorLogin(this.login);
        
        if (patrocinador != null)
        {
            //Verifica se a senha do patrocinador está correta
            if(patrocinador.getSenha().equals(this.senha))
            {
                //loga o patrocinador
                this.logaPatrocinador(patrocinador);
            }
            else
            {
               EscreveMensagem.escreveErro("Senha incorreta"); 
            }
            
        }
        
        if (patrocinador == null && beneficiado == null)
        {
            EscreveMensagem.escreveErro("O usuário não está cadastrado");
        }
        
    }
    
    private void logaBeneficiado(Beneficiado beneficiado)
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        FacesContext context = FacesContext.getCurrentInstance();      
        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
        //Insere o beneficiado na sessão
        sessao.setBeneficiadoSessao(beneficiado);
        
        this.setMesadaAtualSessao(beneficiado);
        
        try
        {
            //Direciona o usuário para a área de usuários logados do sistema
            response.sendRedirect((request.getContextPath()+"/templatelogado.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }       
    }
    
    private void logaPatrocinador(Patrocinador patrocinador)
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        FacesContext context = FacesContext.getCurrentInstance();        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
        //Insere o beneficiado na sessão        
        sessao.setPatrocinadorSessao(patrocinador);
        
        this.setMesadaAtualSessao(patrocinador);
        try
        {
            //Direciona o usuário para a área de usuários logados do sistema
            response.sendRedirect((request.getContextPath()+"/templatelogado.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    //Método que insere a mesada do mês atual na sessão
    //Recebe como parâmetro o beneficiado que recebe a mesada
    private void setMesadaAtualSessao(Beneficiado beneficiado)
    {
        ManipulaSessao sessao = new ManipulaSessao();
        MesadaDAO dao = new MesadaDAO();
        Mesada mesada;
        int mes, ano;
        
        mes = ManipulaDataAtual.getMesAtual();
        ano = ManipulaDataAtual.getAnoAtual();
        
        mesada = dao.buscaPorMesAnoBeneficiado(beneficiado, mes, ano);
                       
        sessao.setMesadaAtualSessao(mesada);        
    }
    
    private void setMesadaAtualSessao(Patrocinador patrocinador)
    {
        ManipulaSessao sessao = new ManipulaSessao();
        MesadaDAO dao = new MesadaDAO();
        Mesada mesada;
        int mes, ano;
        
        mes = ManipulaDataAtual.getMesAtual();
        ano = ManipulaDataAtual.getAnoAtual();
        
        mesada = dao.buscaPorMesAnoPatrocinador(patrocinador, mes, ano);
                       
        sessao.setMesadaAtualSessao(mesada);        
    }
    
    public void sair()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        sessao.fechar();
        
        ControlaLogin.redirecionaParaLogin();
    }
}
