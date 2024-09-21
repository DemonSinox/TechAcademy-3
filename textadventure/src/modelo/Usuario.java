package modelo;

public class Usuario {
    private int id;
    private String login;
    private String senha;
    private int cenaIdSave;

    public Usuario(int id, String login, String senha, int cenaIdSave) {
        this.id = id;
        this.login = login;
        this.senha = senha;
        this.cenaIdSave = cenaIdSave;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public int getCenaIdSave() {
        return cenaIdSave;
    }

    public void setCenaIdSave(int cenaIdSave) {
        this.cenaIdSave = cenaIdSave;
    }
}
