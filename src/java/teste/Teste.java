/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teste;

import com.google.gson.Gson;
import controle.Conexao;
import controle.CtrlCidade;
import controle.Haversine;
import java.sql.Connection;

/**
 *
 * @author fabiano.lemos
 */
public class Teste {
    public static void main(String[] args) {
        Connection conn = Conexao.getConexao();
        Gson gson = new Gson();
        String json = gson.toJson(CtrlCidade.getCidadeFiltro(conn, "name", "Manaus"));
        System.out.println(json);     
        
    }
    
    
}
