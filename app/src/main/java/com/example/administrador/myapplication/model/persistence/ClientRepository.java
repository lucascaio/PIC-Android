package com.example.administrador.myapplication.model.persistence;

import com.example.administrador.myapplication.model.entities.Client;

import java.util.List;

public interface ClientRepository {

    void save(Client client);
    List<Client> getAll();
    List<Client> getAllUsers();
    void delete(Client cliente);

}


