/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.testes;

import br.ifes.gerenciadormesada.dao.CategoriaDAO;
import br.ifes.gerenciadormesada.dao.GastoDAO;
import br.ifes.gerenciadormesada.dao.MesadaDAO;
import br.ifes.gerenciadormesada.modelo.Categoria;
import br.ifes.gerenciadormesada.modelo.Gasto;
import br.ifes.gerenciadormesada.modelo.Mesada;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Vinicius
 */
public class TesteGastoDAO {
    
    public TesteGastoDAO() {
    }
    
    @Test
    public void testaInserir()
    {
        Mesada mesada;
        MesadaDAO mesadaDAO = new MesadaDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        Categoria cat;
        
        GastoDAO dao = new GastoDAO();
        Gasto g = new Gasto();
        
        g.setData("2015-02-10");
        g.setDescricao("Lanche");
        g.setValor(20.0);
        
        mesada = mesadaDAO.buscaPorId(1);
        cat = categoriaDAO.buscaPorId(1);
        
        g.setMesada(mesada);
        g.setCategoria(cat);
        
        try
        {
            dao.inserir(g);
        }
        catch(Exception e)
        {
            
        }
        
    }
}
