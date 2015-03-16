/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.MesadaDAO;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "editaMesadaBean")
@SessionScoped
public class EditaMesadaBean {

    private Mesada mesada;
    private MesadaDAO dao;  
    private boolean desabilitaRecompensa;
    private boolean desabilitaCampos;
    private String tipoUsuario;
    
    public EditaMesadaBean() 
    {
       if (ControlaLogin.isLogado())
        {
            this.dao = new MesadaDAO();
            this.desabilitaCampos = true;
            this.desabilitaRecompensa = true;
            
            ManipulaSessao sessao = new ManipulaSessao();
            
            this.mesada = sessao.getMesadaAtualSessao();
            
            this.tipoUsuario = sessao.getTipoUsuario();
            
        }
        else
        {
            ControlaLogin.redirecionaParaLogin();
        } 
    }

    public Mesada getMesada() {
        return mesada;
    }

    public void setMesada(Mesada mesada) {
        this.mesada = mesada;
    }

    public boolean isDesabilitaRecompensa() {
        return desabilitaRecompensa;
    }

    public boolean isDesabilitaCampos() {
        return desabilitaCampos;
    }

    public void habilitaCampos()
    {
        this.desabilitaCampos = false;
        
        if (this.tipoUsuario.equals("patrocinador"))
        {
            this.desabilitaRecompensa = false;
        }
    }  
    
    public void remover()
    {
        try
        {
            ManipulaSessao sessao = new ManipulaSessao();
            
            this.dao.remover(this.mesada);
            
            EscreveMensagem.escreveErro("A mesada foi removida");
            
            sessao.setMesadaAtualSessao(null);
            
            this.redirecionaParaMesada();
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    public void alterar()
    {
        try
        {
            this.dao.alterar(this.mesada);
            
            this.desabilitaCampos = true;
            this.desabilitaRecompensa = true;
            
            EscreveMensagem.escreveInformacao("A mesada foi alterada");
            
            
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    } 
    
    private void redirecionaParaMesada()
    {
        FacesContext context = FacesContext.getCurrentInstance();      
        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
        try
        {
            //Direciona o usuário para a área de usuários logados do sistema
            response.sendRedirect((request.getContextPath()+"/mesada.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
}
