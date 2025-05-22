package mikron.classeconectada.System;

import mikron.classeconectada.User.Aluno;

public class Observacao {

    private int id;
    private String observacao;
    private String data;
    private Aluno aluno;

    public Observacao(int id, String observacao, String data, Aluno aluno) {
        this.id = id;
        this.observacao = observacao;
        this.data = data;
        this.aluno = aluno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }
}
