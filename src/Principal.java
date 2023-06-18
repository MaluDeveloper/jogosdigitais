import daos.EmpresaDAO;
import daos.JogoDigitalDAO;
import daos.PlataformaDAO;
import entidades.Empresa;
import entidades.JogoDigital;
import entidades.Plataforma;

import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {
        // O método main chama o método inicia
        inicia();
    }

    private static void inicia() {
        // Instancia classes de acesso a banco de dados
        JogoDigitalDAO jogoDigitalDAO = new JogoDigitalDAO();
        PlataformaDAO plataformaDAO = new PlataformaDAO();
        EmpresaDAO empresaDAO = new EmpresaDAO();

        // Instancia a classe Scanner para ler as informações inseridas pelo usuário
        Scanner scanner = new Scanner(System.in);

        // Demais atributos que serão utilizados
        JogoDigital jogoDigital = new JogoDigital();
        long idJogoDigital;
        int opcao;

        // Estrutura de repeticao do/while para exibir o menu até que o usuário insira a opcao 0
        do {
            System.out.println();
            System.out.println("================ MENU JOGOS DIGITAIS ================");
            System.out.println("|       Digite 1 para listar os jogos               |");
            System.out.println("|       Digite 2 para cadastrar um jogo             |");
            System.out.println("|       Digite 3 para pesquisar um jogo             |");
            System.out.println("|       Digite 4 para atualizar um jogo             |");
            System.out.println("|       Digite 5 para deletar um jogo               |");
            System.out.println("|       Digite 0 para finalizar o programa          |");
            System.out.println("=====================================================");

            System.out.print("Opção ==> ");
            // Ler a opcao escolhida pelo o usuário (Utilizamos o parseInt para que o scanner nao se perca)
            opcao = Integer.parseInt(scanner.nextLine());
            System.out.println();

            // Bloco try/catch para capturar possíveis erros
            try {
                // Estrutura condicional switch/case para verificar a opcao escolhida
                switch (opcao) {
                    case 1:
                        System.out.println("========= Iniciando Listagem de Jogos Digitais ========");
                        // Chama método de listagem de jogos digitais da clase jogoDigitalDAO
                        List<JogoDigital> jogosDigitais = jogoDigitalDAO.lista();
                        // Percorre lista de jogos digitais
                        for (JogoDigital jogo : jogosDigitais) {
                            // Imprime informacoes do jogo digital através do método toString()
                            System.out.println(jogo.toString());
                        }
                        System.out.println("========= Listagem de Jogos Digitais concluído ========");
                        break;

                    case 2:
                        System.out.println("========== Iniciando Cadastro de Jogo Digital =========");
                        Empresa empresa = new Empresa();

                        System.out.print("Insira o nome do jogo: ");
                        jogoDigital.setNome(scanner.nextLine());

                        System.out.print("Insira o ano de lançamento: ");
                        jogoDigital.setAnoLancamento(Integer.parseInt(scanner.nextLine()));

                        System.out.print("Insira o nome da empresa desenvolvedora: ");
                        empresa.setNome(scanner.nextLine());

                        // Chama metodo de pesquisa de empresa por nome
                        Empresa empresaExistente = empresaDAO.pesquisaPorNome(empresa.getNome());
                        // Verifica se a empresa informada pelo usuário já existe
                        if (empresaExistente != null) {
                            // Se a empresa já existir, então a mesma é utilizada
                            empresa = empresaExistente;
                        } else {
                            // Se a empresa ainda não existir, então solicita ao usuário para inserir o CNPJ
                            System.out.print("Insira o CNPJ da empresa desenvolvedora: ");
                            empresa.setCnpj(String.valueOf(scanner.nextLine()));
                            // Cadastra empresa
                            empresa = empresaDAO.salva(empresa);
                        }

                        System.out.print("Insira o nome plataforma [Console, Computador ou Celular]: ");
                        String nomePlataforma = scanner.nextLine();
                        // Busca a plataforma a partir do nome inserido pelo usuário
                        Plataforma plataforma = plataformaDAO.pesquisaPorNome(nomePlataforma.trim());

                        // Atribui dados da plataforma e da empresa a entidade jogo digital
                        jogoDigital.setPlataforma(plataforma);
                        jogoDigital.setEmpresa(empresa);

                        // Cadastra jogo digital e imprime dados do jogo após salvar
                        System.out.println(jogoDigitalDAO.salva(jogoDigital).toString());
                        System.out.println("========== Cadastro de Jogo Digital concluída =========");
                        break;

                    case 3:
                        System.out.println("=========== Iniciando Pesquisa de Jogo Digital ========");
                        System.out.print("Insira o Id do jogo digital: ");
                        idJogoDigital = Long.parseLong(scanner.nextLine());
                        System.out.println(jogoDigitalDAO.pesquisaPorId(idJogoDigital));
                        System.out.println("=========== Pesquisa de Jogo Digital concluída ========");
                        break;

                    case 4:
                        System.out.println("========== Iniciando Atualização de Jogo Digital =======");
                        System.out.print("Insira o Id do jogo digital: ");
                        idJogoDigital = Long.parseLong(scanner.nextLine());
                        jogoDigital = jogoDigitalDAO.pesquisaPorId(idJogoDigital);

                        System.out.print("Insira o novo nome do jogo digital: ");
                        jogoDigital.setNome(scanner.nextLine());

                        System.out.print("Insira o novo ano de lançamento: ");
                        jogoDigital.setAnoLancamento(Integer.parseInt(scanner.nextLine()));

                        System.out.println(jogoDigitalDAO.atualiza(jogoDigital).toString());
                        System.out.println("========== Atualização de Jogo Digital concluída ========");
                        break;

                    case 5:
                        System.out.println("========== Iniciando Exclusão de Jogo Digital ==========");
                        System.out.print("Insira o Id do jogo digital: ");
                        idJogoDigital = Long.parseLong(scanner.nextLine());
                        jogoDigital = jogoDigitalDAO.pesquisaPorId(idJogoDigital);
                        jogoDigitalDAO.deleta(jogoDigital.getIdJogo());
                        System.out.println("========== Exclusão de Jogo Digital concluída ==========");
                        break;

                    default:
                        // Imprimime a mensagem de opcao invalida, caso o usuário não insira uma opcao de 0 a 5
                        System.out.println("Opção inválida!");
                }

                // Captura erros e os imprimem
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }

            // Executa o programa enquanto a opcao for diferente de 0, caso contrário o mesmo é encerrado
        } while (opcao != 0);

        System.out.println("Programa finalizado!");
        // Fecha a instancia da classe Scanner
        scanner.close();
    }
}