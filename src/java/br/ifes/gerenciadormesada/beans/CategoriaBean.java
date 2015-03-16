/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.AbstractDAO;
import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.dao.CategoriaDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Categoria;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "categoriaBean")
@RequestScoped
public class CategoriaBean
{

    private Categoria categoria;
    private CategoriaDAO dao;  
    private List<Categoria> categorias;
    
    public CategoriaBean() 
    {
        this.categoria = new Categoria();
        this.dao = new CategoriaDAO();
        this.categorias = this.dao.buscarTodos();
    }    
    
    public Categoria getCategoria()
    {
        return this.categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public CategoriaDAO getDao() {
        return dao;
    }

    public void setDao(CategoriaDAO dao) {
        this.dao = dao;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }   
    
    public void inserir()
    {
        try
        {
            Integer id = this.dao.inserir(this.categoria);
            if (id != null)
            {
                this.categoria.setId(id);
                EscreveMensagem.escreveInformacao("Cadastro efetuado");
            }
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    public void remover(Categoria categoria)
    {
        try
        {
            this.dao.remover(categoria);
            
            this.retiraCategoriaDaLista(categoria);
            
            EscreveMensagem.escreveInformacao("A categoria foi removida");
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());;
        }
    }
    
    private void retiraCategoriaDaLista(Categoria categoria)
    {
        for (Categoria objCategoria : this.categorias)
        {
            if (objCategoria.getId().equals(categoria.getId()))
            {
                this.categorias.remove(objCategoria);
                
                break;
            }
        }
    }
}
