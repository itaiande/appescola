package com.iande.ita.appescola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Criando Atributos XML to JAVA

    private Button btnPais,btnEscola;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ligando XML com JAVA e criando instancia do objeto
        btnEscola = (Button)findViewById(R.id.btnEscola);
        btnPais = (Button)findViewById(R.id.btnPais);

        Toast.makeText(this,"ON CREATE",Toast.LENGTH_LONG).show();


    }

    @Override
    protected void onStart() {
        super.onStart();


        Toast.makeText(this,"ON START",Toast.LENGTH_LONG).show();




    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    //Ações do Botão:
    public void acessoPais(View view){
        //Chamando tela menu Pais
        startActivity(new Intent(this,MenuPaisActivity.class));
    }

    public void acessoEscola(View view){
        Log.d("BTN","TA FUNCIONANDO");
    }
}
