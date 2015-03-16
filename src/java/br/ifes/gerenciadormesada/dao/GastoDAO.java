/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.dao;

import br.ifes.gerenciadormesada.conversores.GastoConverte;
import br.ifes.gerenciadormesada.conversores.MesadaConverte;
import br.ifes.gerenciadormesada.entidades.GastoEntidade;
import br.ifes.gerenciadormesada.entidades.MesadaEntidade;
import br.ifes.gerenciadormesada.modelo.Gasto;
import br.ifes.gerenciadormesada.modelo.Mesada;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Vinicius
 */
public class GastoDAO extends AbstractDAO<GastoEntidade>
{
    private final GastoConverte conversor;

    public GastoDAO() {
        this.conversor = new GastoConverte();
    }
    
    public Integer inserir(Gasto gasto)
    {
        GastoEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        return super.inserir(entidade);
    }
    
    public void remover(Gasto gasto)
    {
        GastoEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        super.remover(entidade);
    }
    
    public void alterar(Gasto gasto)
    {
        GastoEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        super.alterar(entidade);
    }
    
    public List<Gasto> buscarTodos()
    {
        List<GastoEntidade> gastossEntidade = super.buscarTodos(GastoEntidade.class);
        
        List<Gasto> gastos = new ArrayList<Gasto>();
        
        Gasto objGasto;
        
        for (int i = 0; i < gastossEntidade.size(); i++)
        {
            objGasto = this.conversor.EntidadeParaModelo(gastossEntidade.get(i));
            
            gastos.add(objGasto);
        }
        
        return gastos;
    }
    
    public Gasto buscaPorId(Integer id)
    {
        GastoEntidade entidade = super.buscarPorId(GastoEntidade.class, id);
        
        return this.conversor.EntidadeParaModelo(entidade);
    }
    
    public List<Gasto> buscaPorMesada(Mesada mesada)
    {
        MesadaEntidade mesadaEntidade;
        MesadaConverte mesadaConverte = new MesadaConverte();
        Query consulta;
        List<GastoEntidade> entidades;
        List<Gasto> gastos = new ArrayList<Gasto>();
        
        String hql="select u from GastoEntidade u where u.mesada = :mesada ";
        
        this.iniciaOperacao();
        
        consulta = this.sessao.createQuery(hql);
        
        mesadaEntidade = mesadaConverte.ModeloParaEntidade(mesada);
        
        consulta.setEntity("mesada", mesadaEntidade);
        
        entidades = (List<GastoEntidade>) consulta.list();       
        
        if (entidades != null)
        {
            Gasto gasto;
            
            for (GastoEntidade entidade : entidades)
            {
                gasto = this.conversor.EntidadeParaModelo(entidade);
                
                gastos.add(gasto);
            }
            
            return gastos;
        }
        
        return null;
    }
    
}
