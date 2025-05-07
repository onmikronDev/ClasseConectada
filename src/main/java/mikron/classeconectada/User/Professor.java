package mikron.classeconectada.User;


import mikron.classeconectada.System.Turma;

import java.util.List;

public class Professor extends User {

	private int id;

	private String Nome;

	private String CPF;

	private String Senha;

	private String Email;

	private List<Turma> Turmas;

	private String MateriaEspecializante;


	public Professor(int id, String nome, String CPF, String senha, String email, String materiaEspecializante, List<Turma> turmas) {
		super(id, nome, CPF, senha, email);
		this.id = id;
		Nome = nome;
		this.CPF = CPF;
		Senha = senha;
		Email = email;
		MateriaEspecializante = materiaEspecializante;
		Turmas = turmas;
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return Nome;
	}

	public String getCPF() {
		return CPF;
	}

	public String getSenha() {
		return Senha;
	}

	public String getEmail() {
		return Email;
	}

	public List<Turma> getTurmas() {
		return Turmas;
	}

	public String getMateriaEspecializante() {
		return MateriaEspecializante;
	}
}
