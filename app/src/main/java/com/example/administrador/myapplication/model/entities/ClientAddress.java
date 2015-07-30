package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrador on 27/07/2015.
 */
public class ClientAddress{

    private Long id;

    private String cep;

    private String tipoDeLogradouro;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;

    public ClientAddress(){
        super();
    }

    public ClientAddress(Parcel parcel){
        super();
        readToParcel(parcel);
    }

    private void readToParcel(Parcel parcel) {
        id = parcel.readLong();
        cep = parcel.readString();
        tipoDeLogradouro = parcel.readString();
        logradouro = parcel.readString();
        bairro = parcel.readString();
        cidade = parcel.readString();
        estado = parcel.readString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTipoDeLogradouro() {
        return tipoDeLogradouro;
    }

    public void setTipoDeLogradouro(String tipoDeLogradouro) {
        this.tipoDeLogradouro = tipoDeLogradouro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
