/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.testes;

import br.ifes.gerenciadormesada.dao.CategoriaDAO;
import br.ifes.gerenciadormesada.modelo.Categoria;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Vinicius
 */
public class TesteCategoriaDAO {
    
    @Test
    public void testaInserir()
    {
        CategoriaDAO dao = new CategoriaDAO();
        Categoria c = new Categoria();
        
        c.setTitulo("Alimentação");
        
        dao.inserir(c);
    }
}
