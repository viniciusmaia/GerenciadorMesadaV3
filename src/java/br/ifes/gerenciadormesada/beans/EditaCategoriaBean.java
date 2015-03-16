/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.CategoriaDAO;
import br.ifes.gerenciadormesada.modelo.Categoria;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
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
@ManagedBean(name = "editaCategoriaBean")
@SessionScoped
public class EditaCategoriaBean
{
    
    private Categoria categoria;
    private final CategoriaDAO dao;
    
    public EditaCategoriaBean() 
    {
        this.dao = new CategoriaDAO();
    }
    
    public String abrir(Categoria cat)
    {
        this.categoria = cat;
        
        return "editacategoria";
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    
    
    
    public void alterar()
    {
        try
        {
            this.dao.alterar(this.categoria);
            
            EscreveMensagem.escreveInformacao("A alteração foi efetuada");
            
            FacesContext context = FacesContext.getCurrentInstance();      
        
            HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
            HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
            //Redireciona para a página de lista de categorias
            response.sendRedirect((request.getContextPath()+"/listacategorias.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
}
