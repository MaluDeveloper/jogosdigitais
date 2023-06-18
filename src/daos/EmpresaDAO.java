package daos;

import entidades.Empresa;
import conexoes.ConexaoBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class EmpresaDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public Empresa salva(Empresa empresa) {
        String sql = "INSERT INTO empresa (nome, cnpj) VALUES (?, ?)";
        // Bloco try/catch para capturar possíveis erros
        try {
            String[] colunaGerada = { "ID" };
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql, colunaGerada);
            preparedStatement.setString(1, empresa.getNome());
            preparedStatement.setString(2, empresa.getCnpj());;
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                empresa.setIdEmpresa(resultSet.getLong(1));
            }
            // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao salvar empresa. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return empresa;
    }

    public Empresa pesquisaPorNome(String nome) {
        String sql = "SELECT * FROM empresa WHERE nome = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(resultSet.getLong("id_empresa"));
                empresa.setNome(resultSet.getString("nome"));
                empresa.setCnpj(resultSet.getString("cnpj"));
                return empresa;
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao pesquisar empresa. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return null;
    }

    public Empresa pesquisaPorId(Long idEmpresa) {
        String sql = "SELECT * FROM empresa WHERE id_empresa = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idEmpresa);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Empresa empresa = new Empresa();
                empresa.setIdEmpresa(resultSet.getLong("id_empresa"));
                empresa.setNome(resultSet.getString("nome"));
                empresa.setCnpj(resultSet.getString("cnpj"));
                return empresa;
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao pesquisar empresa. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return null;
    }

}
