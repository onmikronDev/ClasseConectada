package mikron.classeconectada.System;


import mikron.classeconectada.User.Professor;
import mikron.classeconectada.db.DBUtil;

import javax.swing.*;
import java.util.Date;

public class Calendario {

	private int id;
	private Date data;

	private String evento;

	private String descricao;

	private Turma turma;

	public Calendario(int id,Date data, String evento,Turma turma,String descricao) {
		this.id = id;
		this.data = data;
		this.evento = evento;
		this.turma = turma;
		this.descricao = descricao;
	}


	public void visualizarEventos() {
		JOptionPane.showMessageDialog(null, "Data: " + data + "\nEvento: " + evento + "\nDescrição: " + descricao, "Eventos", JOptionPane.INFORMATION_MESSAGE);
	}

	public void editarEvento(Date data, String evento, String descricao) {
		this.data = data;
		this.evento = evento;
		this.descricao = descricao;
		DBUtil.atualizarEvento(this);
	}

	public int getId() {
		return id;
	}

	public Date getData() {
		System.out.println("Data: " + data);
		return data;
	}

	public String getEvento() {
		System.out.println("Evento: " + evento);
		return evento;
	}

	public Turma getTurma() {
		return turma;
	}

	public String getDescricao() {
		return descricao;
	}

	public void deletarEvento() {
		int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar o evento?", "Deletar Evento", JOptionPane.YES_NO_OPTION);
		if (resposta == JOptionPane.YES_OPTION) {
			DBUtil.deletarEvento(this);
			JOptionPane.showMessageDialog(null, "Evento deletado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "Evento não deletado!", "Cancelado", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
