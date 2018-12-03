/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.util.Comparator;

/**
 *
 * @author fabiano.lemos
 */
public class Cidade{

    int idbg_id;
    String uf;
    String name;
    boolean capital;
    float lon;
    float lat;
    String no_accents;
    String alterative_names;
    String microregion;
    String mesoregion;

    public Cidade(int idbg_id, String uf, String name, boolean capital, float lon, float lat, String no_accents, String alterative_names, String microregion, String mesoregion) {
        this.idbg_id = idbg_id;
        this.uf = uf;
        this.name = name;
        this.capital = capital;
        this.lon = lon;
        this.lat = lat;
        this.no_accents = no_accents;
        this.alterative_names = alterative_names;
        this.microregion = microregion;
        this.mesoregion = mesoregion;
    }

    public int getIdbg_id() {
        return idbg_id;
    }

    public void setIdbg_id(int idbg_id) {
        this.idbg_id = idbg_id;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCapital() {
        return capital;
    }

    public void setCapital(boolean capital) {
        this.capital = capital;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public String getNo_accents() {
        return no_accents;
    }

    public void setNo_accents(String no_accents) {
        this.no_accents = no_accents;
    }

    public String getAlterative_names() {
        return alterative_names;
    }

    public void setAlterative_names(String alterative_names) {
        this.alterative_names = alterative_names;
    }

    public String getMicroregion() {
        return microregion;
    }

    public void setMicroregion(String microregion) {
        this.microregion = microregion;
    }

    public String getMesoregion() {
        return mesoregion;
    }

    public void setMesoregion(String mesoregion) {
        this.mesoregion = mesoregion;
    }


    

}
