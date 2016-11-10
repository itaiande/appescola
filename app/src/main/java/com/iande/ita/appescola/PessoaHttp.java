package com.iande.ita.appescola;

import com.iande.ita.appescola.modelo.Pessoa;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Usuario on 01/11/2016.
 */

public class PessoaHttp {
    private static String BASE_URL = "http://mobile-aceite.tcu.gov.br:80/appCivicoRS/rest/pessoas";
    private static Properties parametros;

    public PessoaHttp(){
        parametros = new Properties();
    }

    // Autentica a pessoa, pelo email e senha retornando um token de acesso para realizar operações
    // que exigem autenticação.
    // O token gerado é válido por 7 dias.
    public String autenticar(String email, String senha){
        String url = BASE_URL;
        String token = "";
        url += "/autenticar";
        parametros.setProperty("email",email);
        parametros.setProperty("senha",senha);
        try {
            HttpURLConnection conexao = abrirConexao(url,"GET",false,parametros);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
                token = conexao.getHeaderField("apptoken");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

    //Usado para funcionalidade de esqueci minha senha.
    // O servidor gera uma nova senha aleatória para o usuário e envia para o email do mesmo.
    public boolean redefinirSenha(String email) throws Exception{
        String url = BASE_URL;
        url += "/redefinirSenha?email=" + email ;
        boolean senhaRedefinida = false;
        parametros = new Properties();
        int statusCode = 0; // 200 - Senha redefinida com sucesso 404 - email não cadastrado
        try {
            HttpURLConnection conexao = abrirConexao(url,"POST",false,parametros);
            statusCode = conexao.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK){
                senhaRedefinida = (statusCode == 200);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return senhaRedefinida;
    }

    /*
    Cadastra Pessoa
     */
    private boolean enviarPessoa(String metodoHttp, Pessoa pessoa) throws Exception {
        boolean sucesso = false;
        boolean doOutput = !"DELETE".equals(metodoHttp);
        parametros = new Properties();
        String url = BASE_URL;
        if (!doOutput){
            url += "/"+ pessoa.getCod();
        }
        HttpURLConnection conexao = abrirConexao(url,metodoHttp,doOutput,parametros);
        if (doOutput){
            OutputStream os = conexao.getOutputStream();
            os.write(pessoaToJsonBytes(pessoa));
            os.flush();
            os.close();
        }
        int responseCode = conexao.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            InputStream is = conexao.getInputStream();
            String s = streamToString(is);
            is.close();

            JSONObject json = new JSONObject(s);
            int cod = json.getInt("cod");
            pessoa.setCod(cod);
            sucesso = true;
        } else {
            throw new RuntimeException("Erro ao realizar operação");
        }
        conexao.disconnect();
        return sucesso;
    }

    private List<Pessoa> getPessoas() throws Exception{
        parametros = new Properties();
        HttpURLConnection conexao = abrirConexao(BASE_URL,"GET",false,parametros);
        List<Pessoa> list = new ArrayList<Pessoa>();
        if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
            String jsonString = streamToString(conexao.getInputStream());
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); i++){
                JSONObject pessoaJSON = json.getJSONObject(i);
                Pessoa p = new Pessoa(
                        pessoaJSON.getInt("cod"),
                        pessoaJSON.getString("email"),
                        pessoaJSON.getString("nomeUsuario"),
                        "",
                        pessoaJSON.getBoolean("emailVerificado"),
                        "");
                list.add(p);
            }
        }
        return list;
    }

    public List<Pessoa> getPessoas(int cod) throws Exception{
        parametros = new Properties();
        String url = BASE_URL;
        url += "/"+ cod;
        HttpURLConnection conexao = abrirConexao(BASE_URL,"GET",false,parametros);
        List<Pessoa> list = new ArrayList<Pessoa>();
        if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
            String jsonString = streamToString(conexao.getInputStream());
            JSONArray json = new JSONArray(jsonString);
            for (int i = 0; i < json.length(); i++){
                JSONObject pessoaJSON = json.getJSONObject(i);
                Pessoa p = new Pessoa(
                        pessoaJSON.getInt("cod"),
                        pessoaJSON.getString("email"),
                        pessoaJSON.getString("nomeUsuario"),
                        "",
                        pessoaJSON.getBoolean("emailVerificado"),
                        "");
                list.add(p);
            }
        }
        return list;
    }

    public List<Pessoa> getPessoas(String email) throws InterruptedException{
        parametros = new Properties();
        String url = BASE_URL;
        List<Pessoa> list = new ArrayList<Pessoa>();
        parametros.setProperty("email",email);
        try {
            HttpURLConnection conexao = abrirConexao(BASE_URL,"GET",false,parametros);
            if (conexao.getResponseCode() == HttpURLConnection.HTTP_OK){
                String jsonString = streamToString(conexao.getInputStream());
                JSONArray json = new JSONArray(jsonString);
                for (int i = 0; i < json.length(); i++){
                    JSONObject pessoaJSON = json.getJSONObject(i);
                    Pessoa p = new Pessoa(
                            pessoaJSON.getInt("cod"),
                            pessoaJSON.getString("email"),
                            pessoaJSON.getString("nomeUsuario"),
                            "",
                            pessoaJSON.getBoolean("emailVerificado"),
                            "");
                    list.add(p);
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

    private byte[] pessoaToJsonBytes(Pessoa pessoa){
        byte[] jByte = null;
        try {
            JSONObject jsonPessoa = new JSONObject();
            jsonPessoa.put("biografia", pessoa.getBiografia());
            jsonPessoa.put("email",pessoa.getEmail());
            jsonPessoa.put("nomeCompleto",pessoa.getNomeUsuario());
            jsonPessoa.put("senha",pessoa.getSenha());
            jsonPessoa.put("emailVerificado",pessoa.isEmailVerificado());
            String json = jsonPessoa.toString();
            jByte = json.getBytes();
        } catch (JSONException e){
            e.printStackTrace();
        }
        return jByte;
    }

    private HttpURLConnection abrirConexao(String url, String metodo, boolean doOutput, Properties params) throws Exception{
        URL urlCon = new URL(url);
        HttpURLConnection conexao = (HttpURLConnection) urlCon.openConnection();

        if (params.containsKey("email")){
            conexao.addRequestProperty("email",params.getProperty("email"));
        }
        if (params.containsKey("senha")){
            conexao.addRequestProperty("senha",params.getProperty("senha"));
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

    public boolean inserir(Pessoa pessoa){
        boolean sucesso = false;
        try {
            sucesso = enviarPessoa("POST", pessoa);
        } catch (Exception e){
            return false;
        }
        return sucesso;
    }

}

