package com.iande.ita.appescola;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.iande.ita.appescola.modelo.Escola;
import com.iande.ita.appescola.modelo.Estado;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PesquisaEscolaActivity extends AppCompatActivity implements Request.RequestListener {

    private final String URL_ESTADOS = "https://gist.githubusercontent.com/letanure/3012978/raw/36fc21d9e2fc45c078e0e0e07cce3c81965db8f9/estados-cidades.json";
    Spinner spnCategoria,spnCidade,spnBairro,spnUf;

    ArrayList<String> UFs = new ArrayList<>();
    ArrayList<Estado> estados = new ArrayList<>();


    ArrayList<Escola> escolas = new ArrayList<>();

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_escola);
        //Instanciando Spinner para Filtragem
        progressBar= (ProgressBar)findViewById(R.id.pbDownload);

        spnBairro = (Spinner)findViewById(R.id.spnBairro);
        spnCategoria = (Spinner)findViewById(R.id.spnCategoria);
        spnCidade = (Spinner)findViewById(R.id.spnCidade);
        spnUf = (Spinner)findViewById(R.id.spnUf);


        //progressBar.setVisibility(View.VISIBLE);
        Ocean.newRequest(URL_ESTADOS, this).get().send();


        spnUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                populaSpinnerCidade(estados.get(position));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();




    }

    @Override
    protected void onResume() {
        super.onResume();



    }


    public void populaSpinnerUF(ArrayList<Estado> estados){

        ArrayList<String> ufs = new ArrayList<>();
        for (Estado est:estados) {

            ufs.add(est.getSigla());

        }

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ufs);
        spnUf.setAdapter(adapter);

    }

    public void populaSpinnerCidade(Estado estado){

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,estado.getCidades());
        spnCidade.setAdapter(adapter);
    }


    @Override
    public void onRequestOk(String s, JSONObject jsonObject, int code) {
        if(Request.NENHUM_ERROR == code){
            try {
                JSONArray jsonArray = jsonObject.getJSONArray("estados");
                Gson gson = new Gson();
                for (int i =0;i< jsonArray.length();i++) {
                    Estado estado = gson.fromJson(jsonArray.get(i).toString(), Estado.class);
                    estados.add(estado);

                }
                if(estados.size() ==27) {
                    progressBar.setVisibility(View.GONE);
                    populaSpinnerUF(estados);
                    populaSpinnerCidade(estados.get(0));
                    //Log.i("CIDADE",estados.get(0).getCidade().get(0));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}
