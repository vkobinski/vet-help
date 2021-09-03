package com.avaliacao.modelos;

public class Vaca {

    private String id;
    private String scoreCorporal;
    private String scoreLocomotor;
    private String observacao;

    public Vaca(String id, String scoreCorporal, String scoreLocomotor, String observacao){
        this.id = id;
        this.scoreCorporal = scoreCorporal;
        this.scoreLocomotor = scoreLocomotor;
        this.observacao = observacao;
    }

    public String getId() {
        return id;
    }

    public String getScoreCorporal() {
        return scoreCorporal;
    }

    public String getScoreLocomotor() {
        return scoreLocomotor;
    }

    public String getObservacao() {
        return observacao;
    }

    @Override
    public String toString() {
        return "Vaca{" +
                "id='" + id + '\'' +
                ", scoreCorporal='" + scoreCorporal + '\'' +
                ", scoreLocomotor='" + scoreLocomotor + '\'' +
                ", observacao='" + observacao + '\'' +
                '}';
    }
}
