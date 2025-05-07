package mikron.classeconectada.System;


import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;

import java.util.Date;
import java.util.List;

public class Chamada extends Turma {

	private Aluno aluno;

	private boolean presenca;

	private Professor professor;

	private Date data;

	public Chamada(int id, String sala) {
		super(id, sala);
	}

	public void RegistrarChamada() {

	}

}
