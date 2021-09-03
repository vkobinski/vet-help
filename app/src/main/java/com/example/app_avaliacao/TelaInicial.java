package com.example.app_avaliacao;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TelaInicial extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private Button btn_criar, btn_existente, btn_atualizar;
    private TextView nomeFazenda;
    private String escolhaString;
    private String stringFazenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        inicializarComponentes();
    }

    @Override
    public void onClick(View v) {

        if(v.equals(this.btn_criar)) {
            this.stringFazenda = this.nomeFazenda.getText().toString();
            if(this.stringFazenda.isEmpty()){
                Context context = getApplicationContext();
                CharSequence texto = "Há campos vazios!";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
            }else{
                Intent myIntent = new Intent(this, MainActivity.class);
                myIntent.putExtra("nomeFazenda", this.stringFazenda);
                startActivity(myIntent);

            }

        }else if(v.equals(this.btn_atualizar)){
            getFiles();
        }else if(v.equals(this.btn_existente)){
            Intent myIntent = new Intent(this, MainActivity.class);
            String stringFazenda2 = this.spinner.getSelectedItem().toString();
            myIntent.putExtra("nomeFazenda", stringFazenda2);
            startActivity(myIntent);
        }

    }

    public void inicializarComponentes(){
        this.spinner = (Spinner) findViewById(R.id.spinner);
        getFiles();
        this.btn_atualizar = findViewById(R.id.btn_atualizar);
        this.btn_existente = findViewById(R.id.btn_abrir);
        this.btn_criar = findViewById(R.id.btn_criar);
        this.btn_criar.setOnClickListener(this);
        this.btn_atualizar.setOnClickListener(this);
        this.btn_existente.setOnClickListener(this);
        this.nomeFazenda = findViewById(R.id.nomePlanilha);

    }

    public void getFiles(){
        File folder = getExternalFilesDir(null);
        File[] listOfFiles = folder.listFiles();
        List<String> arquivos = new ArrayList<>();
        for(int i = 0; i < listOfFiles.length; i++){
            arquivos.add(listOfFiles[i].getName().replace(".xls", ""));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arquivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinner.setAdapter(adapter);

    }


}