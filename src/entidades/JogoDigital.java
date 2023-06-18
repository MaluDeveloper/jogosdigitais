package entidades;

public class JogoDigital {

    private Long idJogo;
    private String nome;
    private Integer anoLancamento;
    private Empresa empresa;
    private Plataforma plataforma;

    public Long getIdJogo() {
        return idJogo;
    }

    public void setIdJogo(Long idJogo) {
        this.idJogo = idJogo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public Plataforma getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(Plataforma plataforma) {
        this.plataforma = plataforma;
    }

    // Método toString() para exibir as informacoes detalhadas do jogo digital
    @Override
    public String toString() {
        return "Jogo Digital => {" +
                " Id = " + idJogo +
                ", Nome do jogo = " + nome  +
                ", Ano de lançamento = " + anoLancamento +
                ", Empresa => {" +
                " Nome da empresa = " + empresa.getNome()  +
                ", CNPJ = " + empresa.getCnpj() +
                " } Plataforma = " + plataforma.getNome() +
                " }";
    }
}
