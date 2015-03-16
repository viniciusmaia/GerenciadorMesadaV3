/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.testes;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.dao.MesadaDAO;
import br.ifes.gerenciadormesada.dao.PatrocinadorDAO;
import br.ifes.gerenciadormesada.entidades.BeneficiadoEntidade;
import br.ifes.gerenciadormesada.entidades.MesadaEntidade;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
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
public class TesteMesadaDAO {
    
    /*
    @Test
    public void testaInserir()
    {
        Mesada mesada = new Mesada();
        MesadaDAO dao = new MesadaDAO();
        
        mesada.setAno(2015);
        mesada.setMes(2);
        mesada.setRecompensa(40.0);
        mesada.setValor(200.0);
        
        BeneficiadoDAO beneficiadoDAO = new BeneficiadoDAO();
        Beneficiado beneficiado = beneficiadoDAO.buscaPorId(2);    
        
        PatrocinadorDAO patrocinadorDAO = new PatrocinadorDAO();
        Patrocinador p = patrocinadorDAO.buscaPorId(3);
        
        mesada.setBeneficiado(beneficiado);
        mesada.setPatrocinador(p);
        
        try
        {
            dao.inserir(mesada);
        }
        catch(Exception e)
        {
            
        }
        
    }*/
    
    @Test
    public void testeBuscaPorId()
    {
        MesadaDAO dao = new MesadaDAO();
        
        Integer id = 1;
        
        Mesada mesada = dao.buscaPorId(id);
        
        assertEquals(mesada.getId(), id);
        
        Beneficiado beneficiado = mesada.getBeneficiado();
        
        assertEquals(beneficiado.getNome(), "Vinicius");
        
        Patrocinador p = mesada.getPatrocinador();
        
        assertEquals(p.getNome(), "Danilo");
    }
}
