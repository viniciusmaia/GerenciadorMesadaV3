/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.util;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vinicius
 */
public class ControlaLogin
{
    public static void redirecionaParaLogin()
    {
        FacesContext context = FacesContext.getCurrentInstance();      
        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
        try
        {
            //Direciona o usuário para a área de usuários logados do sistema
            response.sendRedirect((request.getContextPath()+"/home.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }     

    public static boolean isLogado()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        return sessao.getTipoUsuario() != null;
    }
}
