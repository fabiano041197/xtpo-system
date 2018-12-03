/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author fabiano.lemos
 */
public class CidadeEstatistica implements Comparable<CidadeEstatistica>{
    String uf ;
    int  quantidade;

    public CidadeEstatistica(String uf, int quantidade) {
        this.uf = uf;
        this.quantidade = quantidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
    public int compareTo(CidadeEstatistica cidade) {
        //int result = this.getUf().compareTo(cidade.getUf());
        if(this.getQuantidade() < cidade.getQuantidade()){
            return 1;
        }
        if(this.getQuantidade() > cidade.getQuantidade()){
            return  -1;
        }else{
            return 0;
        }
    };
}
