/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;

import br.ifes.gerenciadormesada.dao.CategoriaDAO;
import br.ifes.gerenciadormesada.dao.GastoDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Categoria;
import br.ifes.gerenciadormesada.modelo.Gasto;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.DefaultEditorKit;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "gastoBean")
@RequestScoped
public class GastoBean{

    private Gasto gasto;
    private GastoDAO dao;  
    private List<Categoria> categorias;
    private List<Gasto> gastosMesAtual;
    private int idCat;
    private Date data;
    
    public GastoBean() 
    {
        if (ControlaLogin.isLogado())
        {
            this.gasto = new Gasto();
            this.dao = new GastoDAO();

            CategoriaDAO catDao = new CategoriaDAO();

            this.categorias = catDao.buscarTodos();
            
            this.criaListaGastosMesAtual();
        }
        else
        {
            ControlaLogin.redirecionaParaLogin();
        }
    }    
    
    public Gasto getGasto()
    {
        return this.gasto;
    }

    public void setGasto(Gasto gasto) {
        this.gasto = gasto;
    }

    public List<Gasto> getGastosMesAtual() {
        return gastosMesAtual;
    }

    public void setGastosMesAtual(List<Gasto> gastosMesAtual) {
        this.gastosMesAtual = gastosMesAtual;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }    

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }    
    
    private void criaListaGastosMesAtual()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        Mesada mesadaMesAtual = sessao.getMesadaAtualSessao();
        
        this.gastosMesAtual = this.dao.buscaPorMesada(mesadaMesAtual);
        
    }
    
    public void inserir()
    {
        try
        {
            this.setCategoria();
            this.setMesada();
            this.converteData();
            
            Integer id = this.dao.inserir(this.gasto);
            
            if (id != null)
            {
                this.gasto.setId(id);
                
                EscreveMensagem.escreveInformacao("O gasto foi inserido");
                
                this.direcionaParaLista();
            }
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    public void remover(Gasto gasto)
    {
        try
        {
            this.dao.remover(gasto);
            this.removeGastoLista(gasto);
            
            EscreveMensagem.escreveInformacao("O registro foi removido");
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    private void removeGastoLista(Gasto gasto)
    {
        for (Gasto objGasto : this.gastosMesAtual)
        {
            if (objGasto.getId().equals(gasto.getId()))
            {
                this.gastosMesAtual.remove(objGasto);
                break;
            }
        }
    }
    
    private void setCategoria()
    {
        CategoriaDAO dao = new CategoriaDAO();
        
        Categoria categoria = dao.buscaPorId(this.idCat);
        
        this.gasto.setCategoria(categoria);
    }
    
    private void setMesada()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        Mesada m = sessao.getMesadaAtualSessao();
        
        this.gasto.setMesada(m);
    }
    
    private void converteData()
    {
        DateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        
        String dataString = formata.format(this.data);
        
        this.gasto.setData(dataString);
    }
    
    private void direcionaParaLista() throws IOException 
    {
        FacesContext context = FacesContext.getCurrentInstance();      
        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());

        //Redireciona para a página de lista de categorias
        response.sendRedirect((request.getContextPath()+"/listagastos.jsf"));
    }
}
