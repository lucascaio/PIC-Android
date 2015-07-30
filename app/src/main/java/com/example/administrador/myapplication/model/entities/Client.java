package com.example.administrador.myapplication.model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.administrador.myapplication.model.persistence.SQLiteClientRepository;

import java.io.Serializable;
import java.util.List;

public class Client implements Parcelable{

    private Long codigo_admmin;

    private String name_admin;

    private String password_admin;

    public Long getCodigo_admmin() {
        return codigo_admmin;
    }

    public void setCodigo_admmin(Long codigo_admmin) {
        this.codigo_admmin = codigo_admmin;
    }

    public String getName_admin() {
        return name_admin;
    }

    public void setName_admin(String name_admin) {
        this.name_admin = name_admin;
    }

    public String getPassword_admin() {
        return password_admin;
    }

    public void setPassword_admin(String password_admin) {
        this.password_admin = password_admin;
    }

    private Long codigo;

    private String name;

    private Integer age;

    private String phone;

    private String cep;

    private String tipoDeLogradouro;

    private String logradouro;

    private String bairro;

    private String cidade;

    private String estado;

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

    public Client(){}

    public Client(Parcel parcel){
        super();
        readToParcel(parcel);
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void save(){
        SQLiteClientRepository.getInstanceClientRepository().save(this);
    }

    public static List<Client> getAll(){
        return SQLiteClientRepository.getInstanceClientRepository().getAll();
    }

    public static List<Client> getAllUsers(){
        return SQLiteClientRepository.getInstanceClientRepository().getAllUsers();
    }


    public static void delete(Client client) {
        SQLiteClientRepository.getInstanceClientRepository().delete(client);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(codigo);
        dest.writeString(name == null ? "" : name);
        dest.writeInt(age == null ? -1 : age.intValue());
        dest.writeString(phone == null ? "" : phone);
        dest.writeString(cep == null ? "" :cep);
        dest.writeString(tipoDeLogradouro == null ? "" : tipoDeLogradouro);
        dest.writeString(logradouro == null ? "" : logradouro);
        dest.writeString(bairro == null ? "" : bairro);
        dest.writeString(cidade == null ? "" : cidade);
        dest.writeString(estado == null ? "" : estado);
    }

    private void readToParcel(Parcel parcel) {
        codigo = parcel.readLong();
        name = parcel.readString();
        age = parcel.readInt();
        if(age == -1) age = null;
        phone = parcel.readString();
        cep = parcel.readString();
        tipoDeLogradouro = parcel.readString();
        logradouro = parcel.readString();
        bairro = parcel.readString();
        cidade = parcel.readString();
        estado = parcel.readString();

    }

    public static final Parcelable.Creator<Client> CREATOR = new Parcelable.Creator<Client>(){

        @Override
        public Client createFromParcel(Parcel source) {
            return new Client(source);
        }

        @Override
        public Client[] newArray(int size) {
            return new Client[size];
        }
    };

}
