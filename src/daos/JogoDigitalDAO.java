package daos;

import entidades.Empresa;
import entidades.JogoDigital;
import conexoes.ConexaoBD;
import entidades.Plataforma;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class JogoDigitalDAO {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public JogoDigital salva(JogoDigital jogoDigital) {
        String sql = "INSERT INTO jogo_digital (nome, ano_lancamento, id_empresa, id_plataforma) VALUES (?, ?, ?, ?)";
        // Bloco try/catch para capturar possíveis erros
        try {
            String[] colunaGerada = { "ID" };
            // Cria conexao com o banco de dados
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql, colunaGerada);
            // Atribui os parametros na query
            preparedStatement.setString(1, jogoDigital.getNome());
            preparedStatement.setLong(2, jogoDigital.getAnoLancamento());
            preparedStatement.setLong(3, jogoDigital.getEmpresa().getIdEmpresa());
            preparedStatement.setLong(4, jogoDigital.getPlataforma().getIdPlataforma());
            // Executa query no banco de dados
            preparedStatement.executeUpdate();
            // Obtem dados do ultimo jogo cadastrado
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                // Se encontrar dados do ultimo jogo cadastrado, obtem o Id gerado automaticamente
                jogoDigital.setIdJogo(resultSet.getLong(1));
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao savar jogo digital. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return jogoDigital;
    }

    public JogoDigital pesquisaPorId(Long idJogoDigital) {
        String sql = "SELECT * FROM jogo_digital WHERE id_jogo = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            // Cria conexao com o banco de dados
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            // Atribui os parametros na query
            preparedStatement.setLong(1, idJogoDigital);
            // Se encontrar dados do ultimo jogo cadastrado, obtem o Id gerado automaticamente
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // Se encontrar dados do ultimo jogo cadastrado, obtem o Id gerado automaticamente e demais atributos
                JogoDigital jogoDigital = new JogoDigital();
                jogoDigital.setIdJogo(resultSet.getLong("id_jogo"));
                jogoDigital.setNome(resultSet.getString("nome"));
                jogoDigital.setAnoLancamento(resultSet.getInt("ano_lancamento"));

                // Obtem o Id da empresa contida na tabela de jogo digital
                Long idEmpresa = resultSet.getLong("id_empresa");
                // Pesquisa empresa por id no banco de dados
                Empresa empresa = new EmpresaDAO().pesquisaPorId(idEmpresa);
                empresa.setIdEmpresa(idEmpresa);
                empresa.setNome(empresa.getNome());
                empresa.setCnpj(empresa.getCnpj());
                // Atribui as dados da empresa ao jogo digital
                jogoDigital.setEmpresa(empresa);

                // Obtem o Id da empresa contida na tabela de jogo digital
                Long idPlataforma = resultSet.getLong("id_plataforma");
                // Pesquisa plataforma por id no banco de dados
                Plataforma plataforma = new PlataformaDAO().pesquisaPorId(idPlataforma);
                // Atribui as dados da plataforma ao jogo digital
                jogoDigital.setPlataforma(plataforma);

                return jogoDigital;
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao pesquisar jogo digital. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        throw new RuntimeException("Jogo digital não encontrado. Id: " + idJogoDigital);
    }

    public List<JogoDigital> lista() {
        String sql = "SELECT * FROM jogo_digital";
        List<JogoDigital> jogosDigitais = new ArrayList<>();
        // Bloco try/catch para capturar possíveis erros
        try {
            // Cria conexao com o banco de dados
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                JogoDigital jogoDigital = new JogoDigital();
                jogoDigital.setIdJogo(resultSet.getLong("id_jogo"));
                jogoDigital.setNome(resultSet.getString("nome"));
                jogoDigital.setAnoLancamento(resultSet.getInt("ano_lancamento"));

                Long idEmpresa = resultSet.getLong("id_empresa");
                Empresa empresa = new EmpresaDAO().pesquisaPorId(idEmpresa);
                empresa.setIdEmpresa(idEmpresa);
                empresa.setNome(empresa.getNome());
                empresa.setCnpj(empresa.getCnpj());
                jogoDigital.setEmpresa(empresa);

                Long idPlataforma = resultSet.getLong("id_plataforma");
                Plataforma plataforma = new PlataformaDAO().pesquisaPorId(idPlataforma);
                jogoDigital.setPlataforma(plataforma);

                jogosDigitais.add(jogoDigital);
            }
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao listar jogo digital. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return jogosDigitais;
    }

    public JogoDigital atualiza(JogoDigital jogoDigital) {
        String sql = "UPDATE jogo_digital SET nome = ?, ano_lancamento = ? WHERE id_jogo = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, jogoDigital.getNome());
            preparedStatement.setLong(2, jogoDigital.getAnoLancamento());
            preparedStatement.setLong(3, jogoDigital.getIdJogo());
            preparedStatement.executeUpdate();
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao atualizar jogo digital. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
        return jogoDigital;
    }

    public void deleta(Long idJogoDigital) {
        String sql = "DELETE FROM jogo_digital WHERE id_jogo = ?";
        // Bloco try/catch para capturar possíveis erros
        try {
            connection = ConexaoBD.criaConexaoBD();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, idJogoDigital);
            preparedStatement.executeUpdate();
        // Captura erros
        } catch (Exception e) {
            // Lanca erro com mensagem personalizada
            throw new RuntimeException("Falha ao deletar jogo digital. Erro: " + e.getMessage());
        } finally {
            // Fecha conexao com o banco de dados
            ConexaoBD.fechaConexaoBD(connection, preparedStatement);
        }
    }

}
