package br.com.ifba.emerson.androidcommysql.models;

import java.io.Serializable;

/**
 * Created by Emerson Santos on 30/07/18.
 */

public class Cliente implements Serializable {

    private int id;
    private String nome;
    private String email;


    public Cliente(int id, String nome, String email){
        this.id = id;
        this.nome = nome;
        this.email = email;
    }

    public int getId(){ return this.id; }
    public String getNome(){ return this.nome; }
    public String getEmail(){ return this.email; }

    @Override
    public boolean equals(Object o){
        return this.id == ((Cliente)o).id;
    }

    @Override
    public int hashCode(){
        return this.id;
    }
}
