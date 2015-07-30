package com.example.administrador.myapplication.controller;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.administrador.myapplication.R;
import com.example.administrador.myapplication.model.entities.Client;
import com.example.administrador.myapplication.model.entities.ClientAddress;
import com.example.administrador.myapplication.model.services.CepService;
import com.example.administrador.myapplication.util.FormHelper;

import org.apache.http.protocol.HTTP;

import java.util.concurrent.ExecutionException;


public class RegisterActivity extends AppCompatActivity {

    public static String CLIENT_PARAM = "CLIENT_PARAM";

    private Client client;
    private EditText clientName;
    private EditText clientAge;
    private EditText clientPhone;
    private EditText clientCep;
    private EditText clientTipoDeLogradouro;
    private Button buttonFindCep;
    private EditText clientLogradouro;
    private EditText clientBairro;
    private EditText clientCidade;
    private EditText clientEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            client = (Client) extras.getParcelable(CLIENT_PARAM);
        }
        bindComponents();
        loadIfEdit();
    }

    private void createButtonFindCep() {
        clientCep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AsyncTask<String, Void, ClientAddress> addressPicker = new GetAddressByCep().execute(clientCep.getText().toString());
                try {
                    ClientAddress clientAddress = addressPicker.get();
                    clientTipoDeLogradouro.setText(clientAddress.getTipoDeLogradouro());
                    clientLogradouro.setText(clientAddress.getLogradouro());
                    clientBairro.setText(clientAddress.getBairro());
                    clientCidade.setText(clientAddress.getCidade());
                    clientEstado.setText(clientAddress.getEstado());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private void loadIfEdit() {
        if (client != null) {
            clientName.setText(client.getName());
            clientAge.setText(client.getAge().toString());
            clientPhone.setText(client.getPhone());
            clientCep.setText(client.getCep());
            clientTipoDeLogradouro.setText(client.getTipoDeLogradouro());
            clientLogradouro.setText(client.getLogradouro());
            clientBairro.setText(client.getBairro());
            clientCidade.setText(client.getCidade());
            clientEstado.setText(client.getEstado());
        }
    }

    private void bindComponents() {
        clientName = (EditText) findViewById(R.id.clientName);
        clientAge = (EditText) findViewById(R.id.clientAge);
        clientPhone = (EditText) findViewById(R.id.clientPhone);
        clientCep = (EditText) findViewById(R.id.clientCep);
        clientTipoDeLogradouro = (EditText) findViewById(R.id.clientTipoDeLogradouro);
        clientLogradouro = (EditText) findViewById(R.id.clientLogradouro);
        clientBairro = (EditText) findViewById(R.id.clientBairro);
        clientCidade = (EditText) findViewById(R.id.clientCidade);
        clientEstado = (EditText) findViewById(R.id.clientEstado);

        clientName.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_edittext_client, 0);
        clientName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (clientName.getRight() - clientName.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        //TODO: Explanation 2:
                        final Intent goToSOContacts = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                        goToSOContacts.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
                        startActivityForResult(goToSOContacts, 999);
                    }
                }
                return false;
            }
        });
        clientCep.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.mipmap.ic_launcher, 0);
        clientCep.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (clientCep.getRight() - clientCep.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        createButtonFindCep();
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final Uri contactUri = data.getData();
                    final String[] projection = {
                            ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME,
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                    };
                    final Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    cursor.moveToFirst();

                    clientName.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Identity.DISPLAY_NAME)));
                    clientPhone.setText(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                    cursor.close();
                } catch (Exception e) {
                    Log.d("TAG", "Unexpected error");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_client_persist, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menuSave) {
            tentarSalvarClient();
        }
        return super.onOptionsItemSelected(item);

    }

    private void tentarSalvarClient() {
        if (saveClient()) {
            Toast.makeText(RegisterActivity.this, "Cliente Cadastrado", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private boolean saveClient() {
        if (FormHelper.requireValidate(RegisterActivity.this, clientName, clientAge, clientPhone, clientCep)) {
            if (client == null) {
                client = new Client();
                client.setName(clientName.getText().toString());
                client.setAge(Integer.parseInt(clientAge.getText().toString()));
                client.setPhone(clientPhone.getText().toString());
                client.setCep(clientCep.getText().toString());
                client.setTipoDeLogradouro(clientTipoDeLogradouro.getText().toString());
                client.setLogradouro(clientLogradouro.getText().toString());
                client.setBairro(clientBairro.getText().toString());
                client.setCidade(clientCidade.getText().toString());
                client.setEstado(clientEstado.getText().toString());
            } else {
                client.setName(clientName.getText().toString());
                client.setAge(Integer.parseInt(clientAge.getText().toString()));
                client.setPhone(clientPhone.getText().toString());
                client.setCep(clientCep.getText().toString());
                client.setTipoDeLogradouro(clientTipoDeLogradouro.getText().toString());
                client.setLogradouro(clientLogradouro.getText().toString());
                client.setBairro(clientBairro.getText().toString());
                client.setCidade(clientCidade.getText().toString());
                client.setEstado(clientEstado.getText().toString());
            }
            client.save();
            return true;
        }
        return false;
    }

    private class GetAddressByCep extends AsyncTask<String, Void, ClientAddress> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(RegisterActivity.this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.show();
        }


        @Override
        protected ClientAddress doInBackground(String... params) {
            return CepService.getAddress(params[0]);
        }

        @Override
        protected void onPostExecute(ClientAddress clientAddress) {
            progressDialog.dismiss();
        }


    }

}
