package mikron.classeconectada.System;


import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;
import mikron.classeconectada.db.DBUtil;

import java.sql.Date;
import java.util.ArrayList;

public class Relatorio {

	int id;
	private Aluno aluno;

	private Professor professor;

	private String relatorio;
	private Date data;

	public Relatorio(Aluno aluno) {
		this.aluno = aluno;
	}

	public Relatorio(int id, Aluno aluno, String relatorio, Date data) {
		this.id = id;
		this.aluno = aluno;
		this.relatorio = relatorio;
		this.data = data;
	}

	public void gerarRelatorio() {



		ArrayList<String> relatiorio = new ArrayList<>();
		for (String Displ : DBUtil.listarDisciplina()){
			if(aluno.calcularMedia(Displ) == -1){
				System.out.println("Disciplina: " + Displ + " - Média: Não há notas");
				relatiorio.add("Disciplina: " + Displ + " - Média: Não há notas\n");
				return;
			}
			System.out.println("Disciplina: " + Displ + " - Média: " + String.format("%.2f", aluno.calcularMedia(Displ)));
			relatiorio.add("Disciplina: " + Displ + " - Média: " + String.format("%.2f", aluno.calcularMedia(Displ)) + " " +
					((aluno.calcularMedia(Displ) > 6) ? "Na media" : "Baixo da media") + "\n");
		}
		relatorio = String.valueOf(relatiorio).replace("[", "").replace("]", "").replace(",", "");
		if(DBUtil.getRelatorioByAlunoID(aluno.getId()) != null){
			DBUtil.atualizarRelatorio(aluno.getId(), relatorio);
		}
		DBUtil.inserirRelatorio(aluno.getId(),relatorio);
	}

	public static Relatorio buscarRelatorio(Aluno aluno) {
		return DBUtil.getRelatorioByAlunoID(aluno.getId());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Professor getProfessor() {
		return professor;
	}

	public void setProfessor(Professor professor) {
		this.professor = professor;
	}

	public String getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(String relatorio) {
		this.relatorio = relatorio;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}
}
