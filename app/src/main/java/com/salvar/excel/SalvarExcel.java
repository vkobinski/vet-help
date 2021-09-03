package com.salvar.excel;

import java.io.File;

public class SalvarExcel {

    private String caminho;

    public SalvarExcel(String caminho){
        this.caminho = caminho;
        File file = new File(this.caminho);
    }

}
