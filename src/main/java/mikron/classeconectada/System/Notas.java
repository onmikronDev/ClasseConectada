package mikron.classeconectada.System;

import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;
import mikron.classeconectada.db.DBUtil;

import java.sql.Date;

public class Notas {


	private int id;
	private Aluno aluno;

	private String disciplina;

	private double nota;
	private Date data;
	private String descricao;


	public Notas(Aluno aluno, double nota, String disciplina,String descricao) {
		this.aluno = aluno;
		this.nota = nota;
		this.disciplina = disciplina;
		this.data = Util.getSQLDate();
		this.descricao = descricao;
	}

	public Notas(int id, Aluno aluno, double nota, String disciplina, Date data,String descricao) {
		this.id = id;
		this.aluno = aluno;
		this.nota = nota;
		this.disciplina = disciplina;
		this.data = data;
		this.descricao = descricao;
	}

	public void aplicarNota() {
		DBUtil.aplicarNota(aluno.getId(),disciplina, String.valueOf(nota), descricao);
	}

	public int getId() {
		return id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public double getNota() {
		return nota;
	}

	public Date getData() {
		return data;
	}

	public String getDescricao() {
		return descricao;
	}

	public void notasVisualizacao() {
		System.out.println("Aluno: " + aluno.getNome());
		System.out.println("Disciplina: " + disciplina);
		System.out.println("Nota: " + nota);
	}

	public void editarNota(double notaedit, String conteudo) {
		try {
			this.descricao = conteudo;
			DBUtil.editarNotas(id,notaedit,conteudo);
		} catch (NumberFormatException e) {
			System.out.println("Erro ao converter a nota: " + e.getMessage());
		}
		this.nota = notaedit;
		this.descricao = conteudo;

	}
}
