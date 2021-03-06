/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "menuLogadoBean")
@SessionScoped
public class MenuLogadoBean
{
    private boolean renderizaCampos;
    
    public MenuLogadoBean()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        String tipoUsuario = sessao.getTipoUsuario();
        
        if (tipoUsuario == "patrocinador")
        {
            this.renderizaCampos = false;
        }
        else
        {
            this.renderizaCampos = true;
        }
    }

    public boolean isRenderizaCampos() {
        return renderizaCampos;
    }

    public void setRenderizaCampos(boolean renderizaCampos) {
        this.renderizaCampos = renderizaCampos;
    }
    
    
    
    public String redirecionaUsuario()
    { 
        
        ManipulaSessao sessao = new ManipulaSessao();
        
        String tipoUsuario = sessao.getTipoUsuario();
        
        if (tipoUsuario != null)
        {
            return "edita" + tipoUsuario;
        }
        
        return "home";
    }
        
    public String redirecionaMesada()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        Mesada mesada = sessao.getMesadaAtualSessao();
        
        if (mesada != null)
        {
            return "editamesada";
        }
        
        return "mesada";
    }    
}
