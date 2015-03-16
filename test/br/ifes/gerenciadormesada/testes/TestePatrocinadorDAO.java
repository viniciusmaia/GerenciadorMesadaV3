/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.testes;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.dao.PatrocinadorDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import org.hibernate.HibernateException;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author Vinicius
 */
public class TestePatrocinadorDAO {
    
    public TestePatrocinadorDAO() {
    }
    /*
    @Test
    public void testeInserir()
    {
        Patrocinador patrocinador = new Patrocinador();
        PatrocinadorDAO dao = new PatrocinadorDAO();
        Beneficiado beneficiado;
        BeneficiadoDAO beneficiadoDao = new BeneficiadoDAO();
        
        patrocinador.setNome("Danilo");
        patrocinador.setEmail("danilo.maia@gmail.com");
        patrocinador.setLogin("danilo");
        patrocinador.setSenha("123");
        
        
        try
        {
            beneficiado = beneficiadoDao.buscaPorId(2);
            
            patrocinador.setBeneficiado(beneficiado);
            
            dao.inserir(patrocinador);
        }
        catch(HibernateException e)
        {
            System.out.println("deu ruim");
        }
    }*/
    
    /*@Test
    public void testeBuscaPorId()
    {
        PatrocinadorDAO dao = new PatrocinadorDAO();
        
        Integer id = 3;
        
        Patrocinador patrocinador = dao.buscaPorId(id);
        
        assertEquals(patrocinador.getId(), 3);
        
        Beneficiado beneficiado = patrocinador.getBeneficiado();
        
        assertEquals(beneficiado.getNome(), "Vinicius");
    }*/
    
    @Test 
    public void testeRemover()
    {
        PatrocinadorDAO dao = new PatrocinadorDAO();
        
        Patrocinador p = new Patrocinador();
        
        p.setNome("Teste");
        p.setEmail("Teste");
        p.setLogin("Teste");
        p.setSenha("Teste");
        
        BeneficiadoDAO beneficiadoDAO = new BeneficiadoDAO();
        
        Beneficiado b = beneficiadoDAO.buscaPorId(3);
        
        p.setBeneficiado(b);
        
        
        try
        {
            p.setId(dao.inserir(p));
            dao.remover(p);
        }
        catch(Exception e)
        {
            System.out.println("Deu ruim");
        }
    }
    
}
