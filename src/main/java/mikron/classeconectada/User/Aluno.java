package mikron.classeconectada.User;

import mikron.classeconectada.System.Notas;
import mikron.classeconectada.System.Relatorio;
import mikron.classeconectada.System.Turma;

import java.util.ArrayList;

public class Aluno extends User {

	private int id;

	private String Nome;

	private String CPF;

	private String Senha;

	private String Email;


	private Turma Turma;

	private ArrayList<Notas> Notas;

	private Relatorio Relatorio;


	public Aluno(int id, String nome, String CPF, String email) {
		super(id, nome, CPF, CPF, email);
		this.id = id;
		Nome = nome;
		this.CPF = CPF;
		Email = email;
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

	public Turma getTurma() {
		return Turma;
	}

	public ArrayList<Notas> getNotas() {
		return Notas;
	}

	public Relatorio getRelatorio() {
		return Relatorio;
	}
}
