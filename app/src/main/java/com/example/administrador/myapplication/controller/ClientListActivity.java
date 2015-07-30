package com.example.administrador.myapplication.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;

public class ClientListActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private ListView listViewClients;
    private Client client;

    private FloatingActionButton faAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindClientList();
        bindFab();
        onItemClick();
        registerForContextMenu(listViewClients);

    }

    private void onItemClick() {
        listViewClients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Client client = (Client) parent.getItemAtPosition(position);
                // Best Practices: http://stackoverflow.com/questions/4275678/how-to-make-phone-call-using-intent-in-android
                final Intent goToSOPhoneCall = new Intent(Intent.ACTION_CALL /* or Intent.ACTION_DIAL (no manifest permission needed) */);
                goToSOPhoneCall.setData(Uri.parse("tel:" + client.getPhone()));
                startActivity(goToSOPhoneCall);
            }
        });
    }

    private void bindClientList() {
        listViewClients = (ListView) findViewById(R.id.listViewClients);
        ClientListAdapter adapter = new ClientListAdapter(ClientListActivity.this);
        listViewClients.setAdapter(adapter);


        listViewClients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                client = (Client) parent.getItemAtPosition(position);
                return false;
            }

        });
    }

    private void bindFab() {
        faAdd = (FloatingActionButton) findViewById(R.id.fabAdd);
        faAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMainActivity = new Intent(ClientListActivity.this, RegisterActivity.class);
                startActivity(goToMainActivity);
               /* if (item.getItemId() == R.id.action_settings) {
                }

                return super.onOptionsItemSelected(item); */

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        atualizarLista();
    }

    private void atualizarLista() {
        ClientListAdapter adapter = (ClientListAdapter) listViewClients.getAdapter();
        adapter.setListaClient(Client.getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_settings) {
            startActivity(new Intent(ClientListActivity.this, RegisterActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    private Client criarCliente(String nome, Integer idade) {
        Client cliente = new Client();
        cliente.setName(nome);
        cliente.setAge(idade);
        return cliente;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_client_list_context, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuEdit) {
            Intent edicao = new Intent(ClientListActivity.this, RegisterActivity.class);
            edicao.putExtra(CLIENT_PARAM, client);
            startActivity(edicao);
        }
        if (item.getItemId() == R.id.menuRemove) {
            AlertDialog.Builder builder =
                    new AlertDialog.Builder(ClientListActivity.this)
                            .setMessage(R.string.confirmDelete)
                            .setTitle(R.string.titleConfirmDelete)
                            .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Client.delete(client);
                                    atualizarLista();
                                }
                            }).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            builder.create();
            builder.show();
        }


        return super.onContextItemSelected(item);
    }
}
