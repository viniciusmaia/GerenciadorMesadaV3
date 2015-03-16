/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.gerenciadormesada.util;

import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vinicius
 */
public class ManipulaSessao 
{
    private final HttpSession session;
    
    public ManipulaSessao()
    {
        this.session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }
    
    public String getTipoUsuario()
    {        
        return (String) this.session.getAttribute("TipoUsuario");
    }
    
    public void setTipoUsuario(String tipo)
    {
        this.session.setAttribute("TipoUsuario", tipo);
    }
    
    public void setPatrocinadorSessao(Patrocinador patrocinador)
    {        
        session.setAttribute("usuario", patrocinador);
        
        session.setAttribute("TipoUsuario", "patrocinador");
    }
    
    
    public void setBeneficiadoSessao(Beneficiado beneficiado) 
    {
        
        //Insere o beneficiado na sessão
        this.session.setAttribute("usuario", beneficiado);
        
        //Insere um identificador de usuário na sessão
        this.session.setAttribute("TipoUsuario", "beneficiado");
    }
    
    
    public Patrocinador getPatrocinadorSessao()
    {       
        Patrocinador p = (Patrocinador) session.getAttribute("usuario");
        
        if (p == null)
        {
            ControlaLogin.redirecionaParaLogin();
        }
        
        return  p;
    }
    
    public Beneficiado getBeneficiadoSessao()
    {               
        Beneficiado b = (Beneficiado) session.getAttribute("usuario");
        
        if (b == null)
        {
            ControlaLogin.redirecionaParaLogin();
        }
        
        return  b;
    }
    
    //insere a mesada do mes atual na sessão
    public void setMesadaAtualSessao(Mesada mesada)
    {
        this.session.setAttribute("mesada", mesada);
    }
    
    public Mesada getMesadaAtualSessao()
    {
       return (Mesada) this.session.getAttribute("mesada");
    }
    
    public void fechar()
    {
        this.session.invalidate();
    }
}
