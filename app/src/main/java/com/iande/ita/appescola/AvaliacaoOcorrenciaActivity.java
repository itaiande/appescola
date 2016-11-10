package com.iande.ita.appescola;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.iande.ita.appescola.modelo.Escola;
import com.oceanbrasil.libocean.Ocean;
import com.oceanbrasil.libocean.control.glide.GlideRequest;
import com.oceanbrasil.libocean.control.glide.ImageDelegate;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

public class AvaliacaoOcorrenciaActivity extends AppCompatActivity implements ImageDelegate.BytesListener {
    //Arrays
    private ArrayList<String> categorias = new ArrayList<>();
    private ArrayList<Escola> minhasEscolas = new ArrayList<>();//usuario cadastrado

    //Constantes
    private static final int REQUEST_INTENT_CAMERA = 1;
    private static final int REQUEST_PERMISSION = 13;

    //Permissoes
    private static final String[] PERMISSIONS_READ_WRITE = {Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private ImageView imagemOcorrencia;
    private Spinner spnCategoria;
    private Spinner spnEscola;

    private File caminhoImage;
    private byte byteImagem[];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avaliacao_ocorrencia);

        imagemOcorrencia = (ImageView)findViewById(R.id.imageOCorrencia);
        spnCategoria = (Spinner)findViewById(R.id.spnCategoria);
        spnEscola = (Spinner)findViewById(R.id.spnEscola);

        categorias.add("Educação");
        categorias.add("Acessibilidade");
        categorias.add("Segurança Pública");
        categorias.add("Limpeza Pública");
        categorias.add("Iluminação Pública");
        categorias.add("Infraestrutura");
        categorias.add("Disponibilidade");

        categorias.add("Outros");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,categorias);

        spnCategoria.setAdapter(adapter);

        Escola tal = new Escola();
        tal.setNome("Escola Estadual Alguma coisa");

        minhasEscolas.add(tal);

        ArrayAdapter<Escola> adapterEscola  = new ArrayAdapter<Escola>(this,android.R.layout.simple_list_item_1,minhasEscolas);

        spnEscola.setAdapter(adapterEscola);

    }

    @Override
    protected void onResume() {
        super.onResume();

        imagemOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamera();
            }
        });
    }

    private void abrirCamera(){
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            verificaChamarPermissao();
        } else {
            // tenho permissao, chama a intent de camera
            intentAbrirCamera();
        }
    }

    private void intentAbrirCamera(){
        String nomeFoto = DateFormat.format("yyyy-MM-dd_hhmmss",new Date()).toString()+"firebase.jpg";

        caminhoImage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),nomeFoto);
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(caminhoImage));
        startActivityForResult(it,REQUEST_INTENT_CAMERA);
    }

    private void verificaChamarPermissao() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) || ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // exibir o motivo de esta precisando da permissao
            ActivityCompat.requestPermissions(AvaliacaoOcorrenciaActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        } else {
            ActivityCompat.requestPermissions(AvaliacaoOcorrenciaActivity.this, PERMISSIONS_READ_WRITE, REQUEST_PERMISSION);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_INTENT_CAMERA && resultCode == RESULT_OK){
            if (caminhoImage != null && caminhoImage.exists()){
                Ocean.glide(this)
                        .load(Uri.fromFile(caminhoImage))
                        .build(GlideRequest.BYTES)
                        .addDelegateImageBytes(this)
                        .toBytes(300, 300);
            }else{
                Log.e("Ale","FILE null");
            }
        }else{
            Log.d("Ale","nao usou a camera");
        }
    }

    @Override
    public void createdImageBytes(byte[] bytes) {
        byteImagem = bytes;
        Bitmap bitmap = Ocean.byteToBitmap(bytes);
        imagemOcorrencia.setImageBitmap(bitmap);
    }
}


