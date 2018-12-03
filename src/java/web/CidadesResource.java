/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import com.google.gson.Gson;
import controle.Conexao;
import controle.CtrlCidade;
import java.sql.Connection;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import modelo.Cidade;

/**
 * REST Web Service
 *
 * @author fabiano.lemos
 */
@Path("cidades")
public class CidadesResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of CidadesResource
     */
    public CidadesResource() {
    }

    /**
     * Retorna todas as cidades que são capitais
     */
    @GET
    @Path("capitais")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonCapitais() {
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        String json = gson.toJson(CtrlCidade.getCapitais(conn));
        
        return json;
    }
    
    /*
    Retorna a o estado com maior e menor quantidade de cidades e o total de cidades
    */    
    @GET
    @Path("estatistica")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonMenorMaior() {
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        String json = gson.toJson(CtrlCidade.getMaiorMenor(conn));
        
        return json;
    }
    
    /*
    Retorno o total de cidades por estados
    */
    @GET
    @Path("cidadesporestados")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonQuantidadeCidadesEstados() {
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        String json = gson.toJson(CtrlCidade.getQuantidadeCidadePorEstado(conn));
        
        return json;
    }
    
    /*
    Retorna os dados da cidade passando como parametro o id ibge da cidade
    */
    @GET
    @Path("cidade/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getJsonQuantidadeCidadesEstados(@PathParam("id") int id) {
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        String json = gson.toJson(CtrlCidade.getCidade(conn, id, null));
        
        return json;
    }
    
    /*
    Retorna os dados de uma cidade passando como parametro a UF do estado
    */
    @GET
    @Path("estado/{uf}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getGsonEstado(@PathParam("uf") String uf) {
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        String json = gson.toJson(CtrlCidade.getCidade(conn, 0, uf));
        
        return json;
    }

    /**
     * PUT method for updating or creating an instance of CidadesResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)    
    public String postJsonCidade(String content){
        Gson gson = new Gson();
        Connection conn = Conexao.getConexao();
        CtrlCidade aux = new CtrlCidade();
        Cidade cidade = gson.fromJson(content, Cidade.class);
        String retorno = "[]";
        
        if(aux.setIncluiCidade(conn, cidade)){
            retorno = content;
        }else{
            retorno = "{\"mensagem\":\"Nao foi possivel incluir\"}";
        }
        Conexao.setFechaConexao(conn);
        return retorno;        
        
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String deleteJsonCidade(@PathParam("id") int id){
        String retorno = "[]";
        Connection conn  = Conexao.getConexao();
        CtrlCidade aux = new CtrlCidade();
        if(aux.setDeletaCidade(conn, id)){
            retorno = "{\"mensagem\":\"O registro foi removido com sucesso!\"}";
        }else{
            retorno = "{\"mensagem\":\"Não foi possivel remover!\"}";
        }        
        return retorno;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/quantidade")
    public String getJsonQuantidade(){
        String retorno = "[]";
        Connection conn = Conexao.getConexao();
        int quantidade = CtrlCidade.getQuantidadeTotal(conn);
        System.out.println(quantidade);
        
        retorno = "{\"quantidade\":"+quantidade+"}";
        return retorno;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/cidadesmaislonges")    
    public String getJsonCidadesMaisDistantes(@QueryParam("lat") String lat, 
                                              @QueryParam("lon") String lon ){
        String retorno = "[]";
        Connection conn = Conexao.getConexao();
        Gson gson = new Gson();
        String json = gson.toJson(CtrlCidade.getDistancia(conn, -11.9355403048, -61.9998238963));
        
        retorno = json;
        return retorno;
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/filtrar")    
    public String getJsonCidadeFiltro(@QueryParam("campo") String campo, 
                                      @QueryParam("valor") String valor ){
        
        String retorno = "[]";
        Connection conn = Conexao.getConexao();
        Gson gson = new Gson();
        String json = gson.toJson(CtrlCidade.getCidadeFiltro(conn, campo, valor));
        
        retorno = json;
        return retorno;
    }
    
    
}
