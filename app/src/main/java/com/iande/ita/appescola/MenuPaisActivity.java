package com.iande.ita.appescola;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MenuPaisActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pais);
    }

    public void pesquisarEscola(View view){
        startActivity(new Intent(this,PesquisaEscolaActivity.class));
    }
}
