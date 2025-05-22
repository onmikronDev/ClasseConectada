package mikron.classeconectada.db;

import com.mysql.cj.protocol.Resultset;
import mikron.classeconectada.System.Turma;
import mikron.classeconectada.User.Aluno;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBUtil {

    Connection conn;
    Statement st;


    // URL do banco de dados via localhost
    private String url = "jdbc:mysql://localhost:3306/classeconectada";
    private String user = "root"; //
    private String password = "kekw"; //


    public DBUtil() {
        conexaoGeral();
    }

    public void sendQuery(String query){
        try {
            st.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public boolean conectarStatment(){
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

    public boolean conectarDB(){
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

    public void desconectar(){
        try {
            conn.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println("Erro ao fechar a conexão " + ex.getMessage());
        } finally {
            System.out.println("Conexão fechada");
        }
    }

    public void conexaoGeral(){
        if(conectarDB() && conectarStatment()){
            System.out.println("Conexão Geral realizada com sucesso");
        }else{
            System.out.println("Falha na conexão com o banco");
        }
    }


    public void listarEventosTabelas(JTable tabelas) {
        String query = "SELECT * FROM eventos";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
            tabelas.setRowSorter(new TableRowSorter<>(tabelaLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("evento"),
                        rs.getString("data"),
                };
                tabelaLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public List<String> listarDisciplicasNaTabela(JTable tabelas) {
        List<String> listakekw = listarDisciplina();
        DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
        tabelas.setRowSorter(new TableRowSorter(tabelaLista));
        for(String disciplina : listakekw){
            Object[] obj = new Object[]{
                    disciplina
            };
            tabelaLista.addRow(obj);
        }
        return listakekw;
    }


    public int getUserIDByName(String name){
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

    public void listarTipoUserNaTabela(JTable tabelas) {
        String query = "SELECT tipo FROM user";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
            tabelas.setRowSorter(new TableRowSorter(tabelaLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("tipo")
                };
                tabelaLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public int getAlunoByName(String name){
        String query = "SELECT * FROM user WHERE nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                Aluno aluno = new Aluno(id, name, cpf, email);
                return aluno.getId();
            } else {
                System.out.println("Aluno não encontrado");
                return -1;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return -1;
    }

    public List<Turma> listarTurmasSQL() {
        List<Turma> turmas = new ArrayList<>();
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

    public List<String> listarDisciplina() {
        List<String> disciplina = new ArrayList<>();
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



    public void listarDisciplinaNaTabela(JTable tabelas) {
        List<String> listakekw = listarDisciplina();
        DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
        tabelas.setRowSorter(new TableRowSorter(tabelaLista));
        for(String disciplina2 : listakekw){
            Object[] obj = new Object[]{
                    disciplina2
            };
            tabelaLista.addRow(obj);
        }
    }

    public List<Aluno> getListAlunosAll(){
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM user WHERE tipo = 'aluno'";

        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String email = rs.getString("email");
                Aluno aluno = new Aluno(id, nome, cpf, email);
                alunos.add(aluno);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }

        return alunos;
    }

    public List<Aluno> listarAlunosSQL(int turmaID) {
        List<Aluno> alunos = new ArrayList<>();
        String query = "SELECT * FROM aluno WHERE turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, turmaID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");

                String query2 = "SELECT * FROM user WHERE id = ?";
                try (PreparedStatement ps2 = conn.prepareStatement(query2)) {
                    ps2.setInt(1, id);
                    ResultSet rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        String nome = rs2.getString("nome");
                        String cpf = rs2.getString("cpf");
                        String email = rs2.getString("email");
                        Aluno aluno = new Aluno(id, nome, cpf, email);
                        alunos.add(aluno);
                    }
                } catch (SQLException ex) {
                    System.out.println("Erro ao executar a query " + ex.getMessage());
                }
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return alunos;
    }

    public void cadrastoUser(String nome, String email, String senha, String cpf,String endereco,String Telefone,String observacao, String tipo, String pai, String mae, String formacao) {
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

        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public int cadrastoUser(String nome, String email, String senha, String cpf,String endereco,String Telefone,String observacao, String tipo) {
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

    public void cadrastarAluno(int id, String pai, String mae, int turmaID) {
        String query = "INSERT INTO aluno (id,pai,mae,turma_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, pai);
            ps.setString(3, mae);
            ps.setInt(4, turmaID);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }
    public void cadrastarProfessor(int id, String formacao) {
        String query = "INSERT INTO professor (id, formação) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.setString(2, formacao);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public int getTurmaIDByName(String name){
        String query = "SELECT id FROM turmas WHERE nome = ?";
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

    public String getTurmaNameByID(int id){
        String query = "SELECT nome FROM turmas WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public String getTurmaNameByAlunoName(String alunoName){
        String query = "SELECT turmas.nome FROM turmas INNER JOIN aluno ON turmas.id = aluno.turma_id INNER JOIN user ON aluno.id = user.id WHERE user.nome = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, alunoName);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("nome");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }


    public void listasTurma(JTable tabelas) {
        List<Turma> listakekw = listarTurmasSQL();
        DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
        tabelas.setRowSorter(new TableRowSorter(tabelaLista));
        for(Turma turma : listakekw){
            Object[] obj = new Object[]{
                    turma.getSala(),
            };
            tabelaLista.addRow(obj);
        }
    }


    public void listarUserByProfissao(JTable tabelas,String profissao) {
        String query = "SELECT * FROM user WHERE tipo = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, profissao);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
            tabelas.setRowSorter(new TableRowSorter(tabelaLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("endereco"),
                        rs.getString("telefone"),
                        rs.getString("observacao")
                };
                tabelaLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public String[] getUserDataByID(int id){
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

    public String[] getAlunoDataByID(int id){
        String query = "SELECT * FROM aluno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String pai = rs.getString("pai");
                String mae = rs.getString("mae");
                String turma_id = rs.getString("turma_id");
                return new String[]{pai, mae, turma_id};
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public String[] getProfessorDataByID(int id){
        String query = "SELECT * FROM professor WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String formacao = rs.getString("formação");
                return new String[]{formacao};
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public void listarAlunosNaTabela(JTable tabelas) {
        List<Aluno> listakekw = listarAlunosSQL(-1);
        DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
        tabelas.setRowSorter(new TableRowSorter(tabelaLista));
        for(Aluno aluno : listakekw){
            Object[] obj = new Object[]{
                    aluno.getNome()
            };
            tabelaLista.addRow(obj);
        }
    }

    public void chamadaSQL(int alunoID, Date data,int turmaID,String status) {
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

    public boolean checkChamadaSQL(int alunoID, Date data,int turmaID) {
        String query = "SELECT * FROM chamada WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setDate(2, data);
            ps.setInt(3, turmaID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return false;
    }


    public void updateChamada(int alunoID, Date data,int turmaID,String status) {
        String query = "UPDATE chamada SET status = ? WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, alunoID);
            ps.setDate(3, data);
            ps.setInt(4, turmaID);
            ps.executeUpdate();
            System.out.println("Chamada atualizada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
            System.out.println("ERRO AQUI CHAMADA UPDATE");
        }
    }

    public String getChamadaStatus(int alunoID, Date data,int turmaID) {
        String query = "SELECT status FROM chamada WHERE aluno_id = ? AND data = ? AND turma_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setDate(2, data);
            ps.setInt(3, turmaID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("status");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public void getStatusChamadaByAluno(String alunoName, Date data) {
        String query = "SELECT status FROM chamada WHERE aluno_id = (SELECT id FROM user WHERE nome = ?) AND data = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, alunoName);
            ps.setDate(2, data);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Status: " + rs.getString("status"));
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
    }

    public void listarAlunosNaTabela(JTable tabelas,String turma) {
        if(turma == null){
            System.out.println("Turma não encontrada");
            return;
        }

        List<Aluno> listakekw = listarAlunosSQL(getTurmaIDByName(turma));
        DefaultTableModel tabelaLista = (DefaultTableModel) tabelas.getModel();
        tabelas.setRowSorter(new TableRowSorter(tabelaLista));
        for(Aluno aluno : listakekw){
            Object[] obj = new Object[]{
                    aluno.getNome(),

            };
            tabelaLista.addRow(obj);
        }
    }

    public String login(String login, String senha) {
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

    public void aplicarNota(int idAluno, String materia, String nota, String descricao) {
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

    public void editarNotas(int id, String nota, String descricao) {
        String query = "UPDATE nota SET nota = ?, descricao = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nota);
            ps.setString(2, descricao);
            ps.setInt(3, id);
            ps.executeUpdate();
            System.out.println("Nota editada com sucesso");
        } catch (SQLException ex) {
            System.out.println("Erro ao editar a nota " + ex.getMessage());
        }
    }

    public void deletarNota(int id) {
        String query = "DELETE FROM nota WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar a nota " + ex.getMessage());
        }
    }


    public void listarObservacaoNaTabela(JTable jTable1, int alunoID) {
        String query = "SELECT * FROM observacao WHERE aluno_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaLista = (DefaultTableModel) jTable1.getModel();
            jTable1.setRowSorter(new TableRowSorter(tabelaLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("id"),
                        rs.getString("data"),
                };
                tabelaLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar observações " + ex.getMessage());
        }
    }

    public void editarObservacao(int id, String conteudo) {
        String query = "UPDATE observacao SET conteudo = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, conteudo);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao editar observação " + ex.getMessage());
        }
    }

    public void deletarObservacao(int id) {
        String query = "DELETE FROM observacao WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Erro ao deletar observação " + ex.getMessage());
        }
    }

    public String getObservacaoByID(int id) {
        String query = "SELECT * FROM observacao WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("conteudo");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }

    public void listarNotasNaTabela(JTable jTable1, int alunoID, String disciplina) {
        String query = "SELECT * FROM nota WHERE aluno_id = ? AND disciplina = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, alunoID);
            ps.setString(2, disciplina);
            ResultSet rs = ps.executeQuery();
            DefaultTableModel tabelaLista = (DefaultTableModel) jTable1.getModel();
            jTable1.setRowSorter(new TableRowSorter(tabelaLista));
            while (rs.next()) {
                Object[] obj = new Object[]{
                        rs.getString("id"),
                        rs.getString("nota"),
                        rs.getString("data"),
                };
                tabelaLista.addRow(obj);
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao listar notas " + ex.getMessage());
        }
    }

    public String getNotaDescricaoByID(int id) {
        String query = "SELECT * FROM nota WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("descricao");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao executar a query " + ex.getMessage());
        }
        return null;
    }
}
