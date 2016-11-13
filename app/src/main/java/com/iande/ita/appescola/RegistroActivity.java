package com.iande.ita.appescola;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.iande.ita.appescola.modelo.Pessoa;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONObject;

public class RegistroActivity extends AppCompatActivity {

    EditText  nomeCompleto,email,senha,confirmaSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        nomeCompleto = (EditText)findViewById(R.id.edtNome);
        email = (EditText)findViewById(R.id.edtEmail);
        senha = (EditText)findViewById(R.id.edtSenha);
        confirmaSenha = (EditText)findViewById(R.id.edtConfirmaSenha);



    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    private Pessoa criarPessoa(){
        Pessoa pessoa = new Pessoa(email.getText().toString(),
                nomeCompleto.getText().toString(),
                senha.getText().toString()
                );
        return pessoa;
    }

    public void registrarPais(View view){
        //Toast.makeText(this,"Teste",Toast.LENGTH_LONG).show();

        try {
            PessoaHttp pessoaHttp = new PessoaHttp();
            Pessoa pessoa = criarPessoa();
            pessoaHttp.inserir(pessoa);


            Log.i("COD",pessoa.getCod()+"");

        }catch (Exception ex){
            ex.printStackTrace();
        }


    }

    Request.RequestListener callback = new Request.RequestListener() {
        @Override
        public void onRequestOk(String response, JSONObject jsonObject, int error) {
            Toast.makeText(getApplicationContext(),"TETE",Toast.LENGTH_LONG).show();
        }
    };
}
