package mikron.classeconectada.User;


import mikron.classeconectada.System.Turma;

import java.util.Scanner;

public class Diretor extends User {

	private int id;

	private String Nome;

	private String CPF;

	private String Senha;

	private String Email;

	public Diretor(int id, String email, String senha, String CPF, String nome) {
		super(id, senha, CPF, nome, email);
		this.id = id;
		Email = email;
		Senha = senha;
		this.CPF = CPF;
		Nome = nome;
	}

	public void cadastroDeUser(User User, Turma turma) {
		if(User instanceof Aluno aluno) {
            turma.addAluno(aluno);
		} else if(User instanceof Professor) {
			Professor professor = (Professor) User;
			//turma.addProfessor(professor);
		}
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return Email;
	}

	public String getSenha() {
		return Senha;
	}

	public String getCPF() {
		return CPF;
	}

	public String getNome() {
		return Nome;
	}
}
