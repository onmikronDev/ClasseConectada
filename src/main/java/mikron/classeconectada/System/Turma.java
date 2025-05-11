package mikron.classeconectada.System;


import mikron.classeconectada.System.Chamada;
import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;

import java.util.ArrayList;
import java.util.List;

public class Turma {

	private int id;
	private int ano;

	private String sala;

	private List<Aluno> alunos;

	private List<Professor> professores;

	private Chamada chamada;


	public Turma(int id, String sala, int ano) {
		alunos = new ArrayList<Aluno>();
		this.id = id;
		this.sala = sala;
		this.ano = ano;
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

	public String getSala() {
		return sala;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public List<Professor> getProfessores() {
		return professores;
	}

	public Chamada getChamada() {
		return chamada;
	}

	public int getAno() {
		return ano;
	}

	public void addProfessor(Professor professor) {
		if (professor != null) {
			professores.add(professor);
		} else {
			System.out.println("Professor não pode ser nulo.");
		}
	}
}
