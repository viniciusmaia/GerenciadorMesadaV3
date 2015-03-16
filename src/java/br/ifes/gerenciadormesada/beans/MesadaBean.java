/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.ifes.gerenciadormesada.beans;


import br.ifes.gerenciadormesada.dao.MesadaDAO;
import br.ifes.gerenciadormesada.dao.PatrocinadorDAO;
import br.ifes.gerenciadormesada.modelo.Beneficiado;
import br.ifes.gerenciadormesada.modelo.Mesada;
import br.ifes.gerenciadormesada.modelo.Patrocinador;
import br.ifes.gerenciadormesada.util.ControlaLogin;
import br.ifes.gerenciadormesada.util.EscreveMensagem;
import br.ifes.gerenciadormesada.util.ManipulaDataAtual;
import br.ifes.gerenciadormesada.util.ManipulaSessao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Vinicius
 */
@ManagedBean(name = "mesadaBean")
@SessionScoped
public class MesadaBean
{

    private MesadaDAO dao;  
    private boolean desabilitaRecompensa;
    private Mesada mesada;
    
    public MesadaBean() 
    {
        if (ControlaLogin.isLogado())
        {
            this.dao = new MesadaDAO();
            this.mesada = new Mesada();
            ManipulaSessao sessao = new ManipulaSessao();
            
            String tipoUsuario = sessao.getTipoUsuario();
            
            //Verifica se o usuário é um beneficiado
            //Caso seja um beneficiado, o campo recompensa será desabilitado.
            //Se for um patrocinador, ficará habilitado
            if (tipoUsuario.equals("beneficiado"))
            {
                this.desabilitaRecompensa = true;
            }
            else
            {
                this.desabilitaRecompensa = false;
            }
            
        }
        else
        {
            ControlaLogin.redirecionaParaLogin();
        }
    }  

    public MesadaDAO getDao() {
        return dao;
    }

    public void setDao(MesadaDAO dao) {
        this.dao = dao;
    }

    public boolean isDesabilitaRecompensa() {
        return desabilitaRecompensa;
    }

    public void setDesabilitaRecompensa(boolean desabilitaRecompensa) {
        this.desabilitaRecompensa = desabilitaRecompensa;
    }  

    public Mesada getMesada() {
        return mesada;
    }

    public void setMesada(Mesada mesada) {
        this.mesada = mesada;
    }
    
    
    
    public void inserir()
    {
        try
        {
            
            //Associa a mesada ao beneficiado e ao patrocinador
            this.associaMesadaBeneficiadoPatrocinador();
            
            this.mesada.setAno(ManipulaDataAtual.getAnoAtual());
            this.mesada.setMes(ManipulaDataAtual.getMesAtual());
            
            Integer id = this.dao.inserir(mesada);
            
            if (id != null)
            {
                this.mesada.setId(id);
                
                this.setMesadaSessao();
            
                EscreveMensagem.escreveInformacao("A mesada foi cadastrada");
                
                this.redirecionaParaEdicao();
                
                
            }
            else
            {
                EscreveMensagem.escreveErro("Falha não identificada");
            }
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
    
    
    private void associaMesadaBeneficiadoPatrocinador()
    {
        ManipulaSessao sessao = new ManipulaSessao();
            
        String tipoUsuario = sessao.getTipoUsuario();

        if (tipoUsuario.equals("beneficiado"))
        {
            this.setBeneficiado(sessao);
        }
        else
        {
            this.setPatrocinador(sessao);
        }
    }
    
    private void setPatrocinador(ManipulaSessao sessao)
    {
            
        Patrocinador patrocinador = sessao.getPatrocinadorSessao(); 
        
        this.mesada.setPatrocinador(patrocinador);
        this.mesada.setBeneficiado(patrocinador.getBeneficiado());
    }
    
    //Insere o beneficiado na mesada
    private void setBeneficiado(ManipulaSessao sessao)
    {  
        PatrocinadorDAO dao = new PatrocinadorDAO();
        Patrocinador patrocinador;
        
        //Busca o beneficiado na sessão
        Beneficiado beneficiado = sessao.getBeneficiadoSessao();
        
        this.mesada.setBeneficiado(beneficiado);
        
        //Busca o patrocinador do beneficiado
        patrocinador = dao.buscaPorBeneficiado(beneficiado);
        
        //Se o beneficiado tiver um patrocinador, ele será inserido na mesada
        if (patrocinador != null)
        {
            this.mesada.setPatrocinador(patrocinador);
        }
    }
    
    private void setMesadaSessao()
    {
        ManipulaSessao sessao = new ManipulaSessao();
        
        sessao.setMesadaAtualSessao(this.mesada);
    }
    
    private void redirecionaParaEdicao()
    {
        FacesContext context = FacesContext.getCurrentInstance();      
        
        HttpServletRequest request = ((HttpServletRequest) context.getExternalContext().getRequest());
        HttpServletResponse response = ((HttpServletResponse) context.getExternalContext().getResponse());
        
        try
        {
            //Direciona o usuário para a área de usuários logados do sistema
            response.sendRedirect((request.getContextPath()+"/editamesada.jsf"));
        }
        catch(Exception e)
        {
            EscreveMensagem.escreveErro(e.getMessage());
        }
    }
}
