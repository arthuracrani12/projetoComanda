package com.primeiro.comandaeestoque.model;

import android.provider.ContactsContract;

import com.google.firebase.database.DatabaseReference;
import com.primeiro.comandaeestoque.helper.ConfiguracaoFirebase;

public class Mesa {

    private String idUsuario;
    private String nome;
    private String descricao;

    public Mesa() {
    }
    public void salvar(){

        DatabaseReference firebaseRef = ConfiguracaoFirebase.getFirebase();
        DatabaseReference mesaRef = firebaseRef.child("mesas").child(getIdUsuario());
        mesaRef.setValue(this);
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
