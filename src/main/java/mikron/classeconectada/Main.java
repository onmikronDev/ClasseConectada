package mikron.classeconectada;

import mikron.classeconectada.System.Turma;
import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Diretor;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        List<Turma> turmas = new ArrayList<Turma>();

        Diretor diretor = new Diretor(1,"Diretor@gmail.com","123456789", "12341234", "Jão");

        Aluno aluno = new Aluno(1,"joão","123","1123","@gmail");

        Turma turma = new Turma(1, "Sala 12");
        turmas.add(turma);


        diretor.cadastroDeUser(aluno,turmas.getFirst());

        turmas.getFirst().listarAlunos();




    }
}
