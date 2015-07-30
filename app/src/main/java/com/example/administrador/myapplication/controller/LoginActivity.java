package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.util.FormHelper;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private List<Client> listaUser;
    private Client client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        listaUser = Client.getAllUsers();
        client = listaUser.get(0);
        criarBotaoLogin();
    }

    private void criarBotaoLogin() {
        Button botaoLogin = (Button) findViewById(R.id.btnlogin);
        botaoLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                EditText editTextUserName = (EditText) findViewById(R.id.editTextUserName);
                EditText editTextPassword = (EditText) findViewById(R.id.editTextPassword);
                if (FormHelper.requireValidate(LoginActivity.this, editTextPassword, editTextUserName)) {
                    if (editTextUserName.getText().toString().equals(client.getName_admin()) && editTextPassword.getText().toString().equals(client.getPassword_admin())) {
                        Intent novaPagina = new Intent(LoginActivity.this, ClientListActivity.class);
                        startActivity(novaPagina);

                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), "Senha Incorreta!", Toast.LENGTH_SHORT);
                        toast.show();
                    }

                }
            }
        });
    }
}
