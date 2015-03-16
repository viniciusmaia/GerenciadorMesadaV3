/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifes.gerenciadormesada.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class ManipulaDataAtual 
{
    public static Date getDataAtual()
    {
        return new Date();
    }
    
    public static int getMesAtual()
    {
        Date dataAtual = new Date();
        
        DateFormat formato = new SimpleDateFormat("MM");
        
        String anoString = formato.format(dataAtual);
        
        return Integer.parseInt(anoString);  
    }
    
    public static int getAnoAtual()
    {
        Date dataAtual = new Date();
        
        DateFormat formato = new SimpleDateFormat("yyyy");
        
        String anoString = formato.format(dataAtual);
        
        return Integer.parseInt(anoString);        
    }
}
