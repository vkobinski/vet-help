package com.example.app_avaliacao;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.avaliacao.modelos.Vaca;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import jxl.Cell;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import static android.view.inputmethod.InputMethodManager.SHOW_FORCED;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText id;
    private RadioGroup scoreLocomotor, scoreCorporal, scoreCorporalDecimal, observacao;
    private Button salvar, sair;
    private String respostaLocomotor, respostaCorporal, respostaCorporalDecimal, respostaObservacao;
    private Vaca vaca;
    private RadioButton corporalDecimalInput, locomotorInput, corporalInput, observacaoInput;
    private String nomeFazenda;
    private boolean novaVaca = false;

    public void setNomeFazenda(){
        Intent myIntent = getIntent();
        this.nomeFazenda = myIntent.getStringExtra("nomeFazenda");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicializarComponentes();
        setNomeFazenda();

        salvar.setOnClickListener(this);
        scoreLocomotor.setOnCheckedChangeListener((group, checkedId) ->{
           RadioButton button = (RadioButton) group.findViewById(checkedId);
           this.locomotorInput = button;
           this.respostaLocomotor = button.getText().toString();
        });
        scoreCorporal.setOnCheckedChangeListener((group, checkedId) ->{
            RadioButton button = (RadioButton) group.findViewById(checkedId);
            this.corporalInput = button;
            this.respostaCorporal = button.getText().toString();
        });
        scoreCorporalDecimal.setOnCheckedChangeListener((group, checkedId) ->{
            RadioButton button = (RadioButton) group.findViewById(checkedId);
            this.corporalDecimalInput = button;
            this.respostaCorporalDecimal = button.getText().toString();
        });
        observacao.setOnCheckedChangeListener((group, checkedId) ->{
            RadioButton button = (RadioButton) group.findViewById(checkedId);
            this.respostaObservacao = button.getText().toString();
            this.observacaoInput = button;
        });
    }

    public void inicializarComponentes(){
        id = (EditText) findViewById(R.id.idVacaCampo);

        id.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(id, InputMethodManager.SHOW_IMPLICIT);
        imm.toggleSoftInput(SHOW_FORCED, 0);

        scoreCorporal = (RadioGroup) findViewById(R.id.corporalButtons);
        scoreCorporalDecimal = (RadioGroup) findViewById(R.id.corporalDecimal);
        scoreLocomotor = (RadioGroup) findViewById(R.id.scoreLocomotorButtons);
        salvar = findViewById(R.id.salvar);
        this.sair = findViewById(R.id.btn_sair);
        observacao = (RadioGroup) findViewById(R.id.observacaoButtons);
        salvar.setOnClickListener(this);
        sair.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {

        if(v.equals(this.sair)){
            finish();
        }

        if(this.respostaObservacao == null || this.respostaLocomotor == null || this.respostaCorporal == null || this.respostaCorporalDecimal == null || this.id.getText().toString().isEmpty()){
            Context context = getApplicationContext();
            CharSequence texto = "Há campos vazios!";
            int duration = Toast.LENGTH_LONG;

            Toast toast = Toast.makeText(context, texto, duration);
            toast.show();
        }else{
            String corporal;

            if(this.respostaCorporalDecimal.equals("0")){
                corporal = this.respostaCorporal;
            }else{
                corporal = this.respostaCorporal + this.respostaCorporalDecimal;
            }
            this.vaca = new Vaca(id.getText().toString(), corporal, this.respostaLocomotor, this.respostaObservacao);

            salvar();

        }
    }

    public void salvar(){

            String caminho = this.nomeFazenda;
            File file = new File(getExternalFilesDir(null), caminho + ".xls");

            WritableWorkbook workbook;
            try {
                Workbook wb = Workbook.getWorkbook(file.getAbsoluteFile());
                workbook = Workbook.createWorkbook(file.getAbsoluteFile(), wb);
                assert workbook != null;
                WritableSheet plan = workbook.getSheet(0);;
                        Label labelID = new Label(0, plan.getRows(), this.vaca.getId());
                        Label labelCorporal = new Label(1, plan.getRows(), this.vaca.getScoreCorporal().replace('.',','));
                        Label labelLocomotor = new Label(2, plan.getRows(), this.vaca.getScoreLocomotor());
                        Label labelObservacao = new Label(3, plan.getRows(), this.vaca.getObservacao());
                        plan.addCell(labelID);
                        plan.addCell(labelCorporal);
                        plan.addCell(labelLocomotor);
                        plan.addCell(labelObservacao);

                        Context context = getApplicationContext();
                        CharSequence texto = "Cadastro Adicionado!";
                        int duration = Toast.LENGTH_LONG;

                        Toast toast = Toast.makeText(context, texto, duration);
                        toast.show();
                workbook.write();
                workbook.close();
                    } catch (Exception e) {
                System.out.println(e);
            }

            finish();
            startActivity(getIntent());

        }


    }