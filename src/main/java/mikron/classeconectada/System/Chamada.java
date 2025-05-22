package mikron.classeconectada.System;


import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;
import mikron.classeconectada.db.DBUtil;

import java.util.Date;
import java.util.List;

public class Chamada {

	private Aluno aluno;

	private Date data;

	private Turma turma;

	private String status;

	public Chamada(Aluno aluno, Date data, Turma turma, String status) {
		this.aluno = aluno;
		this.data = data;
		this.turma = turma;
		this.status = status;
	}

	public Chamada(Aluno aluno, Date data, Turma turma) {
		this.aluno = aluno;
		this.data = data;
		this.turma = turma;
		this.status = DBUtil.getChamadaStatus(aluno.getId(), (java.sql.Date) data, turma.getId());
		if(status == null) {
			DBUtil.chamadaSQL(aluno.getId(), (java.sql.Date) data, turma.getId(), "Presente");
		}
	}

	public void aplicarChamada(String status) {
		this.status = status;
		if (DBUtil.checkChamadaSQL(getAluno().getId(), (java.sql.Date) data, getTurma().getId())) {
			DBUtil.updateChamada(getAluno().getId(), (java.sql.Date) data, getTurma().getId(), status);
		} else {
			DBUtil.chamadaSQL(getAluno().getId(), (java.sql.Date) data, getTurma().getId(), status);
		}
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Turma getTurma() {
		return turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
