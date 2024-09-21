package repositorioMysql;

import modelo.Itens;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection conn;

    public UsuarioDAO() throws SQLException {
        this.conn = db.conectar();
    }

    public Itens buscarItemPorNomeECena(String nome, int cenaId) {
        String query = "SELECT * FROM itens WHERE nome = ? AND cena_id_atual = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, nome);
            stmt.setInt(2, cenaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Itens(rs.getInt("id_itens"), rs.getString("nome"),
                        rs.getString("descricao"), rs.getInt("cena_id_atual"), rs.getInt("cena_id_destino"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar item: " + e.getMessage());
        }
        return null;
    }

    // validar login existente
    public Usuario validarLogin(String login, String senha) {
        String query = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Usuario(rs.getInt("id_usuario"), rs.getString("login"), rs.getString("senha"),
                        rs.getInt("cena_id_save"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
        }
        return null;
    }

    // registrar um novo usuário
    public boolean registrarUsuario(String login, String senha) {
        String query = "INSERT INTO usuarios (login, senha, cena_id_save) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            stmt.setString(2, senha);
            stmt.setInt(3, 1);  // Cena inicial padrão para novos usuários
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erro ao registrar usuário: " + e.getMessage());
            return false;
        }
    }

    // verificar se o login já existe
    public boolean verificarLoginExistente(String login) {
        String query = "SELECT * FROM usuarios WHERE login = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, login);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Erro ao verificar login: " + e.getMessage());
        }
        return false;
    }
}
