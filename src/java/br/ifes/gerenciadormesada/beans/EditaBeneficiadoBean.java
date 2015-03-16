/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "editaBeneficiadoBean")
@SessionScoped
public class EditaBeneficiadoBean {

    private Beneficiado beneficiado;
    private final BeneficiadoDAO dao;
    private boolean desabilitaCampos;
    
    public EditaBeneficiadoBean() 
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        this.beneficiado = sessao.getBeneficiadoSessao();
        
        this.dao = new BeneficiadoDAO();
        this.desabilitaCampos = true;
    }   
    
    public Beneficiado getBeneficiado() {
        return beneficiado;
    }

    public void setBeneficiado(Beneficiado objeto) {
        this.beneficiado = objeto;
    }   

    public boolean isDesabilitaCampos() {
        return desabilitaCampos;
    }

    public void habilitaCampos() 
    {
        this.desabilitaCampos = false;
    }
    
    public void remover()
    {
        try
        {
            this.dao.remover(this.beneficiado);
            
            EscreveMensagem.escreveInformacao("Os usuário foi removido");
            
            ControlaLogin.redirecionaParaLogin();
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
            this.dao.alterar(this.beneficiado);
            this.atualizaBeneficiadoSessao();         
            this.desabilitaCampos = true;
            
            EscreveMensagem.escreveInformacao("Os dados foram atualizados");
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    //Insere o beneficiado atualizado na sessão
    private void atualizaBeneficiadoSessao() 
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        sessao.setBeneficiadoSessao(this.beneficiado);
    }
}
