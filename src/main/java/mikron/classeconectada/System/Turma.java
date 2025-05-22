package mikron.classeconectada.System;


import mikron.classeconectada.System.Chamada;
import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;
import mikron.classeconectada.db.DBUtil;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Turma {

	private int id;
	private int ano;
	private String nome;
	private List<Aluno> alunos;
	private List<Professor> professores;
	private List<Chamada> chamadas;


	public Turma(int id, String nome, int ano) {
		alunos = DBUtil.listarAlunosSQL(id);
		this.id = id;
		this.nome = nome;
		this.ano = ano;
	}

	public static Turma getTurmaByID(int turmaID) {
		Turma turma = DBUtil.getTurmaByID(turmaID);
		if (turma != null) {
			return turma;
		} else {
			System.out.println("Turma não encontrada.");
			return null;
		}
	}

	public void addAluno(Aluno aluno) {
		if (aluno != null) {
			alunos.add(aluno);
		} else {
			System.out.println("Aluno não pode ser nulo.");
		}
	}

	public void removerAluno(Aluno aluno) {

	}

	public void consultarAlunoByID(int id) {
		for (Aluno aluno : alunos) {
			if (aluno.getId() == id) {
				System.out.println("ID: " + aluno.getId() + ", Nome: " + aluno.getNome());
				return;
			}
		}
		System.out.println("Aluno não encontrado.");
	}

	public void listarAlunos() {
		for (Aluno aluno : alunos) {
			System.out.println("ID: " + aluno.getId() + ", Nome: " + aluno.getNome());
		}
	}

	public void consultarAlunoByNome(String nome) {

	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public int getAno() {
		return ano;
	}

	public void setProfessores(List<Professor> professores) {
		this.professores = professores;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}

	public List<Aluno> getAlunosList() {
		return alunos;
	}

	public Aluno getAlunoByName(String aluno1) {
		for (Aluno aluno : alunos) {
			if (aluno.getNome().equalsIgnoreCase(aluno1)) {
				return aluno;
			}
		}
		return null;
	}

	public void addChamada(Chamada chamada) {
		this.chamadas.add(chamada);
	}

	public void setChamada(List<Chamada> chamada) {
		this.chamadas = chamada;
	}

	public List<Chamada> getChamadas() {
		return chamadas;
	}
}
