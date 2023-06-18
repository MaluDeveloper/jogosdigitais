package conexoes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConexaoBD {

    private static final String url = "jdbc:mysql://localhost:3306/registrojogos";
    private static final String user = "root";
    private static final String password = "12345678";

    public static Connection criaConexaoBD() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, password);
    }

    public static void fechaConexaoBD(Connection connection, PreparedStatement preparedStatement) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            } if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Falha ao fechar conexão com o banco de dados. Erro: " + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Connection connection = criaConexaoBD();
        if(connection != null) {
            System.out.println("Conexão obtida com sucesso!");
        }
    }
}
