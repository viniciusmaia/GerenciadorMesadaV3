/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.testes;

import br.ifes.gerenciadormesada.dao.BeneficiadoDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Vinicius
 */

public class TesteBeneficiadoDAO {
    
    @Test
    public void testaInserir()
    {
        Beneficiado beneficiado = new Beneficiado();
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        beneficiado.setNome("Vinicius");
        beneficiado.setEmail("vinicius.c.maia@gmail.com");
        beneficiado.setLogin("vinicius");
        beneficiado.setSenha("123");
        
        
        try
        {
            dao.inserir(beneficiado);
        }
        catch(HibernateException e)
        {
            System.out.println("deu ruim");
        }
        
        beneficiado = new Beneficiado();
        
        beneficiado.setNome("Teste");
        beneficiado.setEmail("teste@gmail.com");
        beneficiado.setLogin("teste");
        beneficiado.setSenha("123");
        
        try
        {
            dao.inserir(beneficiado);
        }
        catch(HibernateException e)
        {
            System.out.println("deu ruim");
        }
        
        beneficiado = new Beneficiado();
        
        
        beneficiado.setNome("Teste2");
        beneficiado.setEmail("teste2@gmail.com");
        beneficiado.setLogin("teste2");
        beneficiado.setSenha("123");
        
        try
        {
            dao.inserir(beneficiado);
        }
        catch(HibernateException e)
        {
            System.out.println("deu ruim");
        }
    }
    /*
    @Test
    public void testFindById()
    {
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        Integer id = 1;
        
        Beneficiado beneficiado = dao.buscaPorId(id);
        
        assertEquals(beneficiado.getId(), 1);
    }
    
    @Test
    public void testeBuscaTodos()
    {
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        List<Beneficiado> beneficiados = new ArrayList<Beneficiado>();
        
        beneficiados = dao.buscarTodos();
        
        assertTrue(beneficiados.size() > 0);
        
        for (int i = 0; i < beneficiados.size(); i++)
        {
            String nome = beneficiados.get(i).getNome();
            
            assertTrue(nome.equals("Vinicius") || nome.equals("Teste") || nome.equals("Teste2"));
        }
    }
    
    @Test 
    public void testeRemover()
    {
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        Beneficiado b = new Beneficiado();
        
        b.setId(1);
        b.setNome("Vinicius");
        
        try
        {
            dao.remover(b);
        }
        catch(Exception e)
        {
            System.out.println("Deu ruim");
        }
    }
    */
    @Test
    public void testeAlterar()
    {
        BeneficiadoDAO dao = new BeneficiadoDAO();
        
        Beneficiado b = new Beneficiado();
        
        b.setId(2);
        b.setNome("Vinicius");
        b.setEmail("vinicius.c.maia@gmail.com");
        b.setLogin("vinicius");
        b.setSenha("123");
        
        try
        {
            dao.alterar(b);
            b = dao.buscaPorId(2);
            
            assertEquals(b.getNome(), "Vinicius");
        }
        catch(Exception e)
        {
            System.out.println("Deu ruim");
        }
    }
}