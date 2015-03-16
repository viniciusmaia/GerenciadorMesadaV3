/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.dao;

import br.ifes.gerenciadormesada.conversores.BeneficiadoConverte;
import br.ifes.gerenciadormesada.conversores.MesadaConverte;
import br.ifes.gerenciadormesada.entidades.BeneficiadoEntidade;
import br.ifes.gerenciadormesada.entidades.MesadaEntidade;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;

/**
 *
 * @author Vinicius
 */
public class MesadaDAO extends AbstractDAO<MesadaEntidade>
{
    private final MesadaConverte conversor;

    public MesadaDAO() {
        this.conversor = new MesadaConverte();
    }
    
    public Integer inserir(Mesada gasto)
    {
        MesadaEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        return super.inserir(entidade);
    }
    
    public void remover(Mesada gasto)
    {
        MesadaEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        super.remover(entidade);
    }
    
    public void alterar(Mesada gasto)
    {
        MesadaEntidade entidade = this.conversor.ModeloParaEntidade(gasto);
        
        super.alterar(entidade);
    }
    
    public List<Mesada> buscarTodos()
    {
        List<MesadaEntidade> mesadasEntidade = super.buscarTodos(MesadaEntidade.class);
        
        List<Mesada> mesadas = new ArrayList<Mesada>();
        
        Mesada objMesada;
        
        for (int i = 0; i < mesadasEntidade.size(); i++)
        {
            objMesada = this.conversor.EntidadeParaModelo(mesadasEntidade.get(i));
            
            mesadas.add(objMesada);
        }
        
        return mesadas;
    }
    
    public Mesada buscaPorId(Integer id)
    {
        MesadaEntidade entidade = super.buscarPorId(MesadaEntidade.class, id);
        
        return this.conversor.EntidadeParaModelo(entidade);
    }
    
    public Mesada buscaPorMesAnoBeneficiado(Beneficiado beneficiado, int mes, int ano)
    {
        BeneficiadoEntidade beneficiadoEntidade;
        BeneficiadoConverte beneficiadoConverte = new BeneficiadoConverte();
        MesadaEntidade entidade;
        Query consulta;
        // TODO Auto-generated method stub
        String hql="select u from MesadaEntidade u where u.beneficiado = :beneficiado and u.mes = :mes and u.ano =:ano ";
        
        this.iniciaOperacao();
        
        consulta = this.sessao.createQuery(hql);
        
        beneficiadoEntidade = beneficiadoConverte.ModeloParaEntidade(beneficiado);
        
        consulta.setEntity("beneficiado", beneficiadoEntidade);
        consulta.setInteger("mes", mes);
        consulta.setInteger("ano", ano);
        
        entidade = (MesadaEntidade) consulta.uniqueResult();
        
        if (entidade != null)
        {
            return this.conversor.EntidadeParaModelo(entidade);
        }
        
        return null;
    }
    
    public Mesada buscaPorMesAnoPatrocinador(Patrocinador patrocinador, int mes, int ano)
    {
        MesadaEntidade entidade;
        Query consulta;
        // TODO Auto-generated method stub
        String hql="select u from MesadaEntidade u where u.patrocinador = :idpatrocinador and u.mes = :mes and u.ano =:ano ";
        
        this.iniciaOperacao();
        
        consulta = this.sessao.createQuery(hql);
        
        consulta.setInteger("idpatrocinador", patrocinador.getId());
        consulta.setInteger("mes", mes);
        consulta.setInteger("ano", ano);
        
        entidade = (MesadaEntidade) consulta.uniqueResult();
        
        if (entidade != null)
        {
            return this.conversor.EntidadeParaModelo(entidade);
        }
        
        return null;
    }
}
