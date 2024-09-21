package modelo;

public class Itens {
    private int id;
    private String nome;
    private String descricao;
    private int cenaAtual;
    private int cenaDestino;

    public Itens(int id, String nome, String descricao, int cenaAtual, int cenaDestino) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.cenaAtual = cenaAtual;
        this.cenaDestino = cenaDestino;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCenaAtual() {
        return cenaAtual;
    }

    public int getCenaDestino() {
        return cenaDestino;
    }
}
