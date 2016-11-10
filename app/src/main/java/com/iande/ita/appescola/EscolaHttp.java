package com.iande.ita.appescola;

/**
 * Created by Usuario on 06/11/2016.
 */

import com.iande.ita.appescola.modelo.Escola;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EscolaHttp {
    private static String BASE_URL = "http://mobile-aceite.tcu.gov.br/nossaEscolaRS/rest/escolas";
    private static Properties parametros;


    public EscolaHttp(){
        parametros = new Properties();
    }

    public List<Escola> getEscolas(String email) throws InterruptedException{
        String url = BASE_URL;
        List<Escola> list = new ArrayList<Escola>();
        parametros.setProperty("email",email);
        try {
            HttpURLConnection conexao = abrirConexao(BASE_URL,"GET",false);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
                String jsonString = streamToString(conexao.getInputStream());
                JSONArray json = new JSONArray(jsonString);
                for (int i = 0; i < json.length(); i++){
                    JSONObject pessoaJSON = json.getJSONObject(i);
                    Escola escola = new Escola(
                            pessoaJSON.getLong("codEscola"),
                            pessoaJSON.getString("nome"),
                            pessoaJSON.getString("email"));
                    list.add(escola);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    private String streamToString (InputStream is) throws IOException{
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int lidos;
        while ((lidos = is.read(bytes)) > 0){
            baos.write(bytes,0,lidos);
        }
        return new String(baos.toByteArray());
    }

    private byte[] escolaToJsonBytes(Escola escola){
        try {
            JSONObject jsonEscola = new JSONObject();
            jsonEscola.put("cod", escola.getCodEscola());
            jsonEscola.put("nome", escola.getNome());
            jsonEscola.put("email",escola.getEmail());
            String json = jsonEscola.toString();
            return json.getBytes();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    private HttpURLConnection abrirConexao(String url, String metodo, boolean doOutput) throws Exception{
        URL urlCon = new URL(url);
        HttpURLConnection conexao = (HttpURLConnection) urlCon.openConnection();

        if (! parametros.getProperty("email").isEmpty()){
            conexao.addRequestProperty("email",parametros.getProperty("email"));
        }

        conexao.setReadTimeout(15000);
        conexao.setConnectTimeout(15000);
        conexao.setRequestMethod(metodo);
        conexao.setDoInput(true);
        conexao.setDoOutput(doOutput);
        if (doOutput){
            conexao.addRequestProperty("Content-Type","application/json");
        }
        conexao.connect();
        return conexao;
    }

}
