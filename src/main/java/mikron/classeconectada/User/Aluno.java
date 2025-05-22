package mikron.classeconectada.User;

import mikron.classeconectada.System.Notas;
import mikron.classeconectada.System.Relatorio;
import mikron.classeconectada.System.Turma;
import mikron.classeconectada.db.DBUtil;

import java.util.ArrayList;

public class Aluno extends User {

	private int id;

	private String Nome;
	private String CPF;
	private String Senha;
	private String Email;
	private String Telefone;
	private String Endereco;
	private String observacao;
	private String pai;
	private String mae;



	private Turma turma;
	private int turmaID;

	private ArrayList<Notas> Notas;

	private Relatorio Relatorio;


	public Aluno(int id,String nome, int turmaID){
        super(id, nome);
        this.id = id;
		this.Nome = nome;
		this.turmaID = turmaID;
		setDados();
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return Nome;
	}

	public void setNome(String nome) {
		Nome = nome;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String CPF) {
		this.CPF = CPF;
	}

	public String getSenha() {
		return Senha;
	}

	public void setSenha(String senha) {
		Senha = senha;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getTelefone() {
		return Telefone;
	}

	public void setTelefone(String telefone) {
		Telefone = telefone;
	}

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getPai() {
		return pai;
	}

	public void setPai(String pai) {
		this.pai = pai;
	}

	public String getMae() {
		return mae;
	}

	public void setMae(String mae) {
		this.mae = mae;
	}

	public Turma getTurma() {
		if(turma == null) {
			turma = DBUtil.getTurma(turmaID);
		}
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public ArrayList<Notas> getNotas() {
		return Notas;
	}

	public double calcularMedia(String disciplina) {
		double soma = 0;
		int count = 0;
		if(getNotas().isEmpty()){
			return -1;
		}
		for (Notas nota : Notas) {
			if (nota.getDisciplina().equals(disciplina)) {
				soma += nota.getNota();
				count++;
			}
		}
		return count > 0 ? soma / count : 0;
	}


	public void setNotas(ArrayList<Notas> notas) {
		Notas = notas;
	}

	public Relatorio getRelatorio() {
		return Relatorio;
	}

	public void setRelatorio(Relatorio relatorio) {
		Relatorio = relatorio;
	}

	public static Aluno getALunoByID(int id) {
		Aluno aluno = DBUtil.getAlunoByID(id);
		if (aluno != null) {
			return aluno;
		} else {
			System.out.println("Aluno não encontrado.");
			return null;
		}
	}

	public void setDados(){
		turma = DBUtil.getTurma(id);
		setNotas((ArrayList<Notas>) DBUtil.listarNotas(this));

		String[] userdata = DBUtil.getUserDataByID(id);
		String[] user = DBUtil.getAlunoDataByID(id);
		this.CPF = userdata[1];
		this.Email = userdata[2];
		this.Endereco = userdata[3];
		this.Telefone = userdata[4];
		this.observacao = userdata[5];
		this.pai = user[0];
		this.mae = user[1];

//		Notas = DBUtil.getNotasByAlunoId(id);
//		Relatorio = DBUtil.getRelatorioByAlunoId(id);
	}
}
