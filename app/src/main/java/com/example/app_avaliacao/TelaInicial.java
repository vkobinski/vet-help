package com.example.app_avaliacao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class TelaInicial extends AppCompatActivity implements View.OnClickListener {

    private Spinner spinner;
    private Button btn_criar, btn_existente, btn_atualizar, btn_share;
    private TextView nomeFazenda;
    private String escolhaString;
    private String stringFazenda;
    private File compartilhar;

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
                CharSequence texto = "Nome vazio";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, texto, duration);
                toast.show();
            }else{
                try {
                    salvar(this.stringFazenda);
                } catch (IOException | BiffException e) {
                    e.printStackTrace();
                }
            }



        }else if(v.equals(this.btn_atualizar)){
            getFiles();
        }else if(v.equals(this.btn_existente)){
            Intent myIntent = new Intent(this, MainActivity.class);
            String stringFazenda2 = this.spinner.getSelectedItem().toString();
            myIntent.putExtra("nomeFazenda", stringFazenda2);
            startActivity(myIntent);
        }
        else if (v.equals(this.btn_share)) {
            File folder = getExternalFilesDir(null);
            File[] listOfFiles = folder.listFiles();
            assert listOfFiles != null;
            for(File arquivo : listOfFiles){
                if(arquivo.getName().equals(this.spinner.getSelectedItem().toString() + ".xls")){
                    this.compartilhar = arquivo;
                    System.out.println(arquivo);
                }
            }

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, this.compartilhar);
            shareIntent.setType("application/excel");
            startActivity(Intent.createChooser(shareIntent, "Enviar para:"));

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
        this.btn_share = findViewById(R.id.btn_share);
        this.btn_share.setOnClickListener(this);
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


    public void salvar(String caminho) throws IOException, BiffException {

        File file = new File(getExternalFilesDir(null), caminho + ".xls");
        if (!file.exists()) {
            WritableWorkbook wb = null;
            try {
                wb = Workbook.createWorkbook(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert wb != null;
            wb.createSheet("Planilha", 0);
            WritableSheet plan = wb.getSheet(0);

            Label label = new Label(0, 0, "ID");
            Label label1 = new Label(1, 0, "Score Corporal");
            Label label2 = new Label(2, 0, "Score Locomotor");
            Label label3 = new Label(3, 0, "Observação");
            // Como o método pode levantar exceção
            // iremos coloca-lo dentro de um try/catch
            try {
                plan.addCell(label);
                plan.addCell(label1);
                plan.addCell(label2);
                plan.addCell(label3);
            } catch (WriteException e1) {
                e1.printStackTrace();
            }

            try {
                wb.write();
                wb.close();

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            Context context = getApplicationContext();
            CharSequence texto = "Planilha Criada!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, texto, duration);
            toast.show();

            finish();
            startActivity(getIntent());


        }

    }}