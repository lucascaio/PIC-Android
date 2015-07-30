package com.example.administrador.myapplication.model.services;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrador on 27/07/2015.
 */
public final class CepService {

    private static final String URL = "http://correiosapi.apphb.com/cep/";

    private CepService(){
        super();
    }

    public static ClientAddress getAddress(String cep){
        ClientAddress address = null;
        try {
            URL url = new URL(URL+cep);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Accept", "application/json");

            if(urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Error code: " + urlConnection.getResponseCode());
            }

            urlConnection.connect();

            ObjectMapper objectMapper = new ObjectMapper();
            address = objectMapper.readValue(urlConnection.getInputStream(), ClientAddress.class);


        } catch (IOException e) {
            e.printStackTrace();
        }
        return address;
    }

}
