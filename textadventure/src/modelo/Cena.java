package modelo;

public class Cena {
    private int id;
    private String descricao;

    public Cena(int id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }
}
