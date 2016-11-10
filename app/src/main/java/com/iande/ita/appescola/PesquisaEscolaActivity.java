package com.iande.ita.appescola;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.iande.ita.appescola.modelo.Endereco;
import com.iande.ita.appescola.modelo.Escola;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PesquisaEscolaActivity extends AppCompatActivity implements Request.RequestListener {

    Spinner spnCategoria,spnCidade,spnBairro,spnEscola,spnUf;

    ArrayList<Escola> escolas = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesquisa_escola);

        //Instanciando Spinner para Filtragem
        spnBairro = (Spinner)findViewById(R.id.spnBairro);
        spnCategoria = (Spinner)findViewById(R.id.spnCategoria);
        spnCidade = (Spinner)findViewById(R.id.spnCidade);
        spnEscola = (Spinner)findViewById(R.id.spnEscola);
        spnUf = (Spinner)findViewById(R.id.spnUf);




        Ocean.newRequest("http://mobile-aceite.tcu.gov.br:80/nossaEscolaRS/rest/escolas?campos=municipio,email,cidade,bairro,endereco,nome,esferaAdministrativa&quantidadeDeItens=10",
                    this).get().send();



    }

    @Override
    public void onRequestOk(String s, JSONObject jsonObject, int code) {
        if(Request.NENHUM_ERROR == code){
            Log.i("JSON",s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                for (int i=0;i<jsonArray.length();i++){
                    Escola escola = new Escola();
                    Endereco endereco = new Endereco();

                    escola.setCodEscola(jsonArray.getJSONObject(i).getLong("codEscola"));
                    escola.setNome(jsonArray.getJSONObject(i).getString("nome"));
                    escola.setEsferaAdministrativa(jsonArray.getJSONObject(i).getString("esferaAdministrativa"));

                    endereco.setBairro(jsonArray.getJSONObject(i).getJSONObject("endereco").getString("bairro"));
                    endereco.setCep(jsonArray.getJSONObject(i).getJSONObject("endereco").getString("cep"));
                    endereco.setDescricao(jsonArray.getJSONObject(i).getJSONObject("endereco").getString("descricao"));
                    endereco.setMunicipio(jsonArray.getJSONObject(i).getJSONObject("endereco").getString("municipio"));
                    endereco.setUf(jsonArray.getJSONObject(i).getJSONObject("endereco").getString("uf"));

                    escola.setEndereco(endereco);

                    escolas.add(escola);
                }

                ArrayAdapter<Escola> adapter = new ArrayAdapter<Escola>(this,android.R.layout.simple_list_item_1,escolas);
                spnEscola.setAdapter(adapter);

                populaSpinnerUF(escolas);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void populaSpinnerUF(ArrayList<Escola> escolas){

        ArrayList<String> ufs = new ArrayList<>();
        for (Escola esc:escolas) {

            ufs.add(esc.getEndereco().getUf());

        }

        Log.i("UF",ufs.get(0));
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,ufs);
        spnUf.setAdapter(adapter);

    }
}
