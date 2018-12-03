/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import modelo.Cidade;
import modelo.CidadeEstatistica;

/**
 *
 * @author fabiano.lemos
 */
public class CtrlCidade {

    public static ArrayList<Cidade> getCapitais(Connection conn) {
        ArrayList<Cidade> list_cidade = new ArrayList<Cidade>();
        Cidade cidade = null;

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from cidade where capital = true order by name");
            while (rs.next()) {
                int ibge_id = rs.getInt("ibge_id");
                String uf = rs.getString("uf");
                String name = rs.getString("name");
                boolean capital = rs.getBoolean("capital");
                float lon = rs.getFloat("lon");
                float lat = rs.getFloat("lat");
                String no_accents = rs.getString("no_accents");
                String alternative_names = rs.getString("alterative_names");
                String microregion = rs.getString("microregion");
                String mesoregion = rs.getString("mesoregion");

                cidade = new Cidade(ibge_id, uf, name, capital, lon, lat, no_accents, alternative_names, microregion, mesoregion);
                list_cidade.add(cidade);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list_cidade;
    }

    public static List<Cidade> getCidade(Connection conn, int id, String filtro_uf) {
        List<Cidade> list_cidade = new ArrayList<Cidade>();
        Cidade cidade = null;
        String filtro = "";

        try {
            if (filtro_uf != null) {
                filtro = " where uf = '" + filtro_uf + "'";
            } else {
                filtro = " where ibge_id = " + id;
            }

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from cidade " + filtro + " order by name");
            while (rs.next()) {
                int ibge_id = rs.getInt("ibge_id");
                String uf = rs.getString("uf");
                String name = rs.getString("name");
                boolean capital = rs.getBoolean("capital");
                float lon = rs.getFloat("lon");
                float lat = rs.getFloat("lat");
                String no_accents = rs.getString("no_accents");
                String alternative_names = rs.getString("alterative_names");
                String microregion = rs.getString("microregion");
                String mesoregion = rs.getString("mesoregion");

                cidade = new Cidade(ibge_id, uf, name, capital, lon, lat, no_accents, alternative_names, microregion, mesoregion);
                list_cidade.add(cidade);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list_cidade;
    }

    public static ArrayList<CidadeEstatistica> getMaiorMenor(Connection conn) {
        ArrayList<CidadeEstatistica> list_cidades = new ArrayList<CidadeEstatistica>();
        CidadeEstatistica cidade = null;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select uf, quantidade\n"
                    + "from(selecT uf, quantidade\n"
                    + "from(selecT uf, count(*)  quantidade\n"
                    + "from cidade  \n"
                    + "group by uf \n"
                    + "order by 2 DESC\n"
                    + "limit 1)cidade_maior \n"
                    + "union all\n"
                    + "select uf, quantidade\n"
                    + "from(selecT uf, count(*) quantidade\n"
                    + "from cidade  \n"
                    + "group by uf \n"
                    + "order by 2 \n"
                    + "limit 1)tabela_menor) geral");
            while (rs.next()) {
                String uf = rs.getString(1);
                int quantidade = rs.getInt(2);
                cidade = new CidadeEstatistica(uf, quantidade);
                list_cidades.add(cidade);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list_cidades;
    }

    public static ArrayList<CidadeEstatistica> getQuantidadeCidadePorEstado(Connection conn) {
        ArrayList<CidadeEstatistica> list_cidades = new ArrayList<CidadeEstatistica>();
        CidadeEstatistica cidade = null;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("selecT uf, count(*) quantidade\n"
                    + "from cidade \n"
                    + "group by uf\n"
                    + "order by 2 DESC");
            while (rs.next()) {
                String uf = rs.getString(1);
                int quantidade = rs.getInt(2);
                cidade = new CidadeEstatistica(uf, quantidade);
                list_cidades.add(cidade);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list_cidades;
    }

    public static boolean setIncluiCidade(Connection conn, Cidade cidade) {
        boolean incluiu = false;
        try {
            PreparedStatement ps = conn.prepareStatement("insert into cidade(ibge_id, uf, name, capital, lon, lat, no_accents, alterative_names, microregion, mesoregion) values(?,?,?,?,?,?,?,?,?,?);");
            ps.setInt(1, cidade.getIdbg_id());
            ps.setString(2, cidade.getUf());
            ps.setString(3, cidade.getName());
            ps.setBoolean(4, cidade.isCapital());
            ps.setFloat(5, cidade.getLon());
            ps.setFloat(6, cidade.getLat());
            ps.setString(7, cidade.getNo_accents());
            ps.setString(8, cidade.getAlterative_names());
            ps.setString(9, cidade.getMicroregion());
            ps.setString(10, cidade.getMesoregion());
            if (!ps.execute()) {
                incluiu = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return incluiu;
    }

    public static boolean setDeletaCidade(Connection conn, int ibdg_id) {
        boolean excluiu = false;
        try {
            PreparedStatement ps = conn.prepareStatement("delete from cidade where ibge_id = " + ibdg_id);
            if (!ps.execute()) {
                excluiu = true;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return excluiu;
    }

    public static ArrayList<Cidade> getCidadeFiltro(Connection conn, String coluna, String valor) {
        ArrayList<Cidade> list_cidades = new ArrayList<Cidade>();
        Cidade cidade = null;
        String sql = "";
        if(coluna=="name"){
            sql  = " where name = '"+valor+"'";
        }else if(coluna=="ibge_id"){
            sql = " where ibge_id = "+Integer.parseInt(valor);
        }else if(coluna=="uf"){
            sql = " where uf = '"+valor+"'";
        }else if(coluna=="capital"){
            sql = " where capital = "+Boolean.parseBoolean(valor);
        }else if(coluna=="lon"){
            sql = " where lon = "+Double.parseDouble(valor);
        }else if(coluna=="lat"){
            sql = " where lat = "+Double.parseDouble(valor);
        }else if(coluna=="no_accents"){
            sql = " where no_accents = '"+valor+"'";
        }else if(coluna=="alterative_names"){
            sql = " where alterative_names = '"+valor+"'";
        }else if(coluna=="microregion"){
            sql = " where microregion = '"+valor+"'";
        }else if(coluna=="mesoregion"){
            sql = " where mesoregion = '"+valor+"'";
        }
        
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from cidade "+sql);
            System.out.println("select * from cidade where " + coluna + " = " + valor);
            while (rs.next()) {
                int ibge_id = rs.getInt("ibge_id");
                String uf = rs.getString("uf");
                String name = rs.getString("name");
                boolean capital = rs.getBoolean("capital");
                float lon = rs.getFloat("lon");
                float lat = rs.getFloat("lat");
                String no_accents = rs.getString("no_accents");
                String alternative_names = rs.getString("alterative_names");
                String microregion = rs.getString("microregion");
                String mesoregion = rs.getString("mesoregion");

                cidade = new Cidade(ibge_id, uf, name, capital, lon, lat, no_accents, alternative_names, microregion, mesoregion);
                list_cidades.add(cidade);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return list_cidades;
    }

    public static int getQuantidade(Connection conn) {
        int quantidade = 0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from cidade");
            quantidade = rs.getInt(1);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return quantidade;
    }

    public static List<CidadeEstatistica> getDistancia(Connection conn, double lat, double lon) {
        List<CidadeEstatistica> list_cidade = new ArrayList<CidadeEstatistica>();
        List<CidadeEstatistica> list_final = new ArrayList<CidadeEstatistica>();
        CidadeEstatistica cidade = null;
        Haversine calc = new Haversine();

        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from cidade");
            while (rs.next()) {
                String name = rs.getString("name");
                double aux_lon = rs.getDouble("lon");
                double aux_lat = rs.getDouble("lat");
                double distancia = calc.distance(lat, lon, aux_lat, aux_lon);
                cidade = new CidadeEstatistica(name, (int) distancia);
                list_cidade.add(cidade);
                //System.out.println("Distancia da cidade " + cidade.getUf() + " é de " + cidade.getQuantidade());
                Collections.sort(list_cidade);
            }

        } catch (Exception e) {
        }

        
        list_final.add(list_cidade.get(0));
        list_final.add(list_cidade.get(1));
        return list_final;

    }
    
    public static int getQuantidadeTotal(Connection conn){
        int quantidade = 0;
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from cidade");
            while(rs.next()){
                quantidade = rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return quantidade;
    }
}
