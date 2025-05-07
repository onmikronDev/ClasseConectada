package mikron.classeconectada.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {

    Connection conn;
    Statement st;


    // URL do banco de dados via localhost
    private String url = "jdbc:mysql://localhost:3306/classeconectada";
    private String user = "root"; //
    private String password = "kekw"; //



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



}
