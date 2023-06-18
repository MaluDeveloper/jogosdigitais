package daos;

import entidades.Plataforma;
import conexoes.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PlataformaDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public Plataforma pesquisaPorId(Long idPlataforma) {
        String sql = "SELECT * FROM plataforma WHERE id_plataforma = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idPlataforma);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Plataforma plataforma = new Plataforma();
                plataforma.setIdPlataforma(resultSet.getLong("id_plataforma"));
                plataforma.setNome(resultSet.getString("nome"));
                return plataforma;
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao pesquisar plataforma. Erro: " + e.getMessage());
        } finally {
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        // Fecha conexao com o banco de dados
        throw new RuntimeException("Plataforma não encontrada. Id: " + idPlataforma);
    }

    public Plataforma pesquisaPorNome(String nome) {
        String sql = "SELECT * FROM plataforma WHERE nome = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Plataforma plataforma = new Plataforma();
                plataforma.setIdPlataforma(resultSet.getLong("id_plataforma"));
                plataforma.setNome(resultSet.getString("nome"));
                return plataforma;
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao pesquisar plataforma. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        throw new RuntimeException("Plataforma inválida. Opções disponíveis: [Console, Computador ou Celular]");
    }
}
