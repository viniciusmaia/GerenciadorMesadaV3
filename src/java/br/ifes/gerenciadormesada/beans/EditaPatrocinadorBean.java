/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.dao.PatrocinadorDAO;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "editaPatrocinadorBean")
@SessionScoped
public class EditaPatrocinadorBean {

    private Patrocinador patrocinador;
    private final PatrocinadorDAO dao;
    private boolean desabilitaCampos;
    
    public EditaPatrocinadorBean()
    {        
        this.dao = new PatrocinadorDAO();
        this.desabilitaCampos = true;
        
        ManipulaSessao sessao = new ManipulaSessao();
        
        this.patrocinador = sessao.getPatrocinadorSessao();
        
    }

    public Patrocinador getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(Patrocinador patrocinador) {
        this.patrocinador = patrocinador;
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
            this.removeBeneficiado();
            
            this.dao.remover(this.patrocinador);
            
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
            this.dao.alterar(this.patrocinador);
            this.atualizaPatrocinadorSessao();
            this.desabilitaCampos = true;
            
            EscreveMensagem.escreveInformacao("Os dados foram atualizados");
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    //insere o patrocinador atualizado na sessão
    private void atualizaPatrocinadorSessao() 
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        sessao.setPatrocinadorSessao(this.patrocinador);
    }
    
    private void removeBeneficiado()
    {
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        dao.remover(this.patrocinador.getBeneficiado());
    }
    
    
}
