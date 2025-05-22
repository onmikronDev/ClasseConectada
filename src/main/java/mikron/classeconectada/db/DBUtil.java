package mikron.classeconectada.db;

import mikron.classeconectada.System.*;
import mikron.classeconectada.User.Aluno;
import mikron.classeconectada.User.Professor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DBUtil {

    static Connection conn;
    static Statement st;


    // URL do banco de dados via localhost
    private static String url = "jdbc:mysql://localhost:3306/classeconectada";
    private static String user = "root"; //
    private static String password = "kekw"; //


    // ------------------------------------------------------------------------
    public static void sendQuery(String query){
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public static boolean conectarStatment(){
        try {
            if(conn == null) return false;
            st = conn.createStatement();
            System.out.println("Conexão realizada com sucesso Com Statement");
            return true;
        } catch (SQLException ex) {
            System.out.println("Falha na conexão com o banco " + ex.getMessage());
            return false;
        }
    }

    public static boolean conectarDB(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url,user,password);
            System.out.println("Conexão realizada com sucesso com o banco");
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Falha na conexão com o banco " + ex.getMessage());
            return false;
        }
    }

    public static void desconectar(){
        try {
            conn.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar a conexão " + ex.getMessage());
        } finally {
            System.out.println("Conexão fechada");
        }
    }

    public static void conexaoGeral(){
        if(conectarDB() && conectarStatment()){
            System.out.println("Conexão Geral realizada com sucesso");
        }else{
            System.out.println("Falha na conexão com o banco");
        }
    }

// ------------------------------------------------------------------------
public static List<Calendario> listarEventos() {
    List<Calendario> eventos = new ArrayList<>();
    String query = "SELECT * FROM eventos";
    try (PreparedStatement ps = conn.prepareStatement(query);
         ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            int id = rs.getInt("id");
            Date data = rs.getDate("data");
            String evento = rs.getString("evento");
            String descricao = rs.getString("descricao");
            Turma turma = Turma.getTurmaByID(rs.getInt("id_turma"));
            Calendario obj = new Calendario(id, data, evento, turma,descricao);
            eventos.add(obj);
        }
    } catch (SQLException ex) {
        System.out.println("Erro ao executar a query " + ex.getMessage());
    }
    return eventos;
}

    public static int getUserIDByName(String name){
        String query = "SELECT id FROM user WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return -1;
    }

    public static String[] getAlunoDataByID(int id){
        String query = "SELECT * FROM aluno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pai = rs.getString("pai");
                String mae = rs.getString("mae");
                return new String[]{pai,mae};
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public static ArrayList<Turma> listarTurmasSQL() {
        ArrayList<Turma> turmas = new ArrayList<>();
        String query = "SELECT * FROM turmas";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String sala = rs.getString("nome");
                int ano = rs.getInt("ano");
                Turma turma = new Turma(id, sala, ano);
                turmas.add(turma);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return turmas;
    }

    public static ArrayList<String> listarDisciplina() {
        ArrayList<String> disciplina = new ArrayList<>();
        String query = "SELECT * FROM disciplina";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                disciplina.add(nome);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return disciplina;
    }

    public static ArrayList<Aluno> listarAlunosSQL(int turmaID) {
        ArrayList<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM aluno where turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, turmaID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                try {
                    String query2 = "SELECT nome FROM user WHERE id = ?";
                    PreparedStatement ps2 = conn.prepareStatement(query2);
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        String nome = rs2.getString("nome");
                        Aluno aluno = new Aluno(id, nome, turmaID);
                        alunos.add(aluno);
                    }
                } catch (SQLException ex) {
                    System.out.println("Erro ao executar a query ripo" + ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query ACHEIIII " + ex.getMessage());
        }
        return alunos;
    }

    public static int  cadrastoUser(String nome, String email, String senha, String cpf,String endereco,String Telefone,String observacao, String tipo) {
        String query = "INSERT INTO user (nome, email, senha, cpf, endereco, telefone,observacao,tipo) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nome);
            ps.setString(2, email);
            ps.setString(3, senha);
            ps.setString(4, cpf);
            ps.setString(5, endereco);
            ps.setString(6, Telefone);
            ps.setString(7, observacao);
            ps.setString(8, tipo);
            ps.executeUpdate();
            String query2 = "SELECT id FROM user WHERE email = ?";
            try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
                ps2.setString(1, email);
                ResultSet rs = ps2.executeQuery();
                if (rs.next()) {
                    return rs.getInt("id");
                }
            } catch (SQLException ex) {
                System.out.println("Erro ao executar a query " + ex.getMessage());
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return -1;
    }

    public static void cadrastarAluno(int id, String pai, String mae, int turmaID) {
        String query = "INSERT INTO aluno (id,pai,mae,turma_id, recado) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, pai);
            ps.setString(3, mae);
            ps.setInt(4, turmaID);
            ps.setString(5, "Nenhum recado");
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }
    public static void cadrastarProfessor(int id, String disciplina) {
        String query = "INSERT INTO professor (id, disciplina) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, disciplina);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public static Turma getTurma(int id) {
        String query = "SELECT * FROM turmas WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                int ano = rs.getInt("ano");
                return new Turma(id, nome, ano);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query esta aq" + ex.getMessage());
        }
        return null;
    }

    public static int getTurmaIDByName(String name){
        String query = "SELECT id FROM turmas WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query Turma HERE" + ex.getMessage());
        }
        return -1;
    }

    public static void listarUserByProfissao(JTable tabelas,String profissao) {
        String query = "SELECT * FROM user WHERE tipo = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, profissao);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaArrayLista = (DefaultTableModel) tabelas.getModel();
            tabelas.setRowSorter(new TableRowSorter(tabelaArrayLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("observacao")
                };
                tabelaArrayLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public static String[] getUserDataByID(int id){
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                String endereco = rs.getString("endereco");
                String telefone = rs.getString("telefone");
                String observacao = rs.getString("observacao");
                return new String[]{nome, cpf, email, endereco, telefone, observacao};
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public static Aluno getAlunoByID(int id){
        String query = "SELECT * FROM aluno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int alunoID = rs.getInt("id");
                int turmaid = rs.getInt("turma_id");
                try {
                    String query2 = "SELECT nome FROM user WHERE id = ?";
                    PreparedStatement ps2 = conn.prepareStatement(query2);
                    ps2.setInt(1, alunoID);
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        String nome = rs2.getString("nome");
                        return new Aluno(alunoID, nome, turmaid);
                    }
                }catch (SQLException ex) {
                    System.out.println("Erro ao executar a query query2" + ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query getAlunoByID " + ex.getMessage());
        }
        return null;
    }
    
    public static void chamadaSQL(int alunoID, Date data,int turmaID,String status) {
        String query = "INSERT INTO chamada (aluno_id,turma_id,data, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setDate(3, data);
            ps.setInt(2, turmaID);
            ps.setString(4, status);
            ps.executeUpdate();
            System.out.println("Chamada registrada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public static boolean checkChamadaSQL(int alunoID, Date data,int turmaID) {
        String query = "SELECT * FROM chamada WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setDate(2, data);
            ps.setInt(3, turmaID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Chamada já registrada checkChamadaSQL");
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return false;
    }


    public static void updateChamada(int alunoID, Date data,int turmaID,String status) {
        String query = "UPDATE chamada SET status = ? WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, alunoID);
            ps.setDate(3, data);
            ps.setInt(4, turmaID);
            ps.executeUpdate();
            System.out.println("VAlues? " + status + " " + alunoID + " " + data + " " + turmaID);
            System.out.println("Chamada atualizada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
            System.out.println("ERRO AQUI CHAMADA UPDATE");
        }
    }

    public static String getChamadaStatus(int alunoID, Date data,int turmaID) {
        String query = "SELECT status FROM chamada WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setDate(2, data);
            ps.setInt(3, turmaID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("VAlues? " + alunoID + " " + data + " " + rs.getString("status"));
                return rs.getString("status");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public static String login(String login, String senha) {
        String query = "SELECT * FROM user WHERE email = ? AND senha = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, login);
            ps.setString(2, senha);
            ResultSet x = ps.executeQuery();
            if (x.next()) {
                System.out.println("Login realizado com sucesso");
                System.out.println(x.getString("tipo"));
                return x.getString("tipo");
            } else {
                System.out.println("Login falhou: usuário ou senha inválidos");
                return null;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
            return null;
        }
    }

    public static void aplicarNota(int idAluno, String materia, String nota, String descricao) {
        String query = "INSERT INTO nota (aluno_id, disciplina, nota,data, descricao) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, idAluno);
            ps.setString(2, materia);
            ps.setString(3, nota);
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            ps.setString(5, descricao);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao aplicar a nota " + ex.getMessage());
        }
    }

    public static void editarNotas(int id, double nota, String descricao) {
        String query = "UPDATE nota SET nota = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDouble(1, nota);
            ps.setString(2, descricao);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Nota editada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao editar a nota " + ex.getMessage());
        }
    }

    public static void deletarNota(int id) {
        String query = "DELETE FROM nota WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar a nota " + ex.getMessage());
        }
    }

    public static List<Aluno> getListAlunos() {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT a.id, u.nome, a.turma_id FROM aluno a INNER JOIN user u ON a.id = u.id";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int turmaId = rs.getInt("turma_id");
                Aluno aluno = new Aluno(id, nome, turmaId);
                alunos.add(aluno);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return alunos;
    }

    public static void inserirRelatorio(int Alunoid, String conteudo) {
        String query = "INSERT INTO relatorio (aluno_id, data, conteudo) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, Alunoid);
            ps.setDate(2, Util.getSQLDate());
            ps.setString(3, conteudo);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao inserir o relatorio " + ex.getMessage());
        }
    }

    public static void cadrastarProfessorTurma(int id, int idturma) {
        String query = "INSERT INTO professor_turmas (professor_id, turma_id) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setInt(2, idturma);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao cadastrar o professor na turma " + ex.getMessage());
        }
    }

    public static Relatorio getRelatorioByAlunoID(int id) {
        String query = "SELECT * FROM relatorio WHERE aluno_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int relatorioID = rs.getInt("id");
                String conteudo = rs.getString("conteudo");
                Date data = rs.getDate("data");
                return new Relatorio(relatorioID, getAlunoByID(id), conteudo, data);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public static void atualizarRelatorio(int id, String relatorio) {
        String query = "UPDATE relatorio SET conteudo = ? WHERE aluno_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, relatorio);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar o relatorio " + ex.getMessage());
        }
    }

    public static List<Observacao> listarObservacao(Aluno aluno) {
        List<Observacao> observacoes = new ArrayList<>();
        String query = "SELECT * FROM observacao WHERE aluno_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, aluno.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String conteudo = rs.getString("conteudo");
                String data = rs.getString("data");
                Observacao obj = new Observacao(id, conteudo, data, aluno);
                observacoes.add(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar observações " + ex.getMessage());
        }
        return observacoes;
    }

    public static void editarObservacao(Observacao obs) {
        String query = "UPDATE observacao SET conteudo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, obs.getObservacao());
            ps.setInt(2, obs.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao editar observação " + ex.getMessage());
        }
    }

    public static void deletarObservacao(Observacao obs) {
        String query = "DELETE FROM observacao WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, obs.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar observação " + ex.getMessage());
        }
    }

    public static List<Notas> listarNotas(Aluno aluno) {
        List<Notas> notas = new ArrayList<>();
        String query = "SELECT * FROM nota WHERE aluno_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, aluno.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                double nota = rs.getDouble("nota");
                String descricao = rs.getString("descricao");
                String disciplina = rs.getString("disciplina");
                Date data = rs.getDate("data");
                Notas obj = new Notas(id, aluno, nota, disciplina, data, descricao);
                notas.add(obj);

            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar notas " + ex.getMessage());
        }
        return notas;
    }

    public static List<Chamada> listarChamadaSQL(int id) {
        List<Chamada> chamadas = new ArrayList<>();
        String query = "SELECT * FROM chamada WHERE turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int alunoID = rs.getInt("aluno_id");
                Date data = rs.getDate("data");
                String status = rs.getString("status");
                Chamada chamada = new Chamada(Objects.requireNonNull(Aluno.getALunoByID(alunoID)), data, Objects.requireNonNull(getTurma(id)), status);
                chamadas.add(chamada);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query listarChamadaSQL" + ex.getMessage());
        }
        return chamadas;
    }

    public static void atualizarEvento(Calendario calendario) {
        String query = "UPDATE eventos SET data = ?, evento = ?, descricao = ? WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, new java.sql.Date(calendario.getData().getTime()));
            ps.setString(2, calendario.getEvento());
            ps.setString(3, calendario.getDescricao());
            ps.setInt(4, calendario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar o evento " + ex.getMessage());
        }
    }

    public static Turma getTurmaByName(String turmaNome) {
        String query = "SELECT * FROM turmas WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, turmaNome);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                int ano = rs.getInt("ano");
                return new Turma(id, nome, ano);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public static void adicionarEvento(Calendario calendario) {
        String query = "INSERT INTO eventos (data, evento, descricao, id_turma) VALUES (?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setDate(1, new java.sql.Date(calendario.getData().getTime()));
            ps.setString(2, calendario.getEvento());
            ps.setString(3, calendario.getDescricao());
            ps.setInt(4, calendario.getTurma().getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao adicionar o evento " + ex.getMessage());
        }
    }

    public static void deletarEvento(Calendario calendario) {
        String query = "DELETE FROM eventos WHERE id = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, calendario.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar o evento " + ex.getMessage());
        }
    }

    public static String getProfessor(int userID) {
        String query = "SELECT * FROM professor WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String disciplina = rs.getString("disciplina");
                return disciplina;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }
}
