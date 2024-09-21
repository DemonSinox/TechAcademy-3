package repositorioMysql;

import modelo.Itens;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class InventarioDAO {

    private Connection conn;

    public InventarioDAO() throws SQLException {
        this.conn = db.conectar();
    }

    // acão do GET
    public void adicionarAoInventario(Usuario usuario, Itens item) {
        String query = "INSERT INTO inventario_jogador (usuario_id, item_id) VALUES (?, ?)";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, item.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar item ao inventário: " + e.getMessage());
        }
    }
    // Iventory
    public void verInventario(Usuario usuario) {
        String query = "SELECT i.nome, i.descricao FROM inventario_jogador ij " +
                "JOIN itens i ON ij.item_id = i.id_itens WHERE ij.usuario_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, usuario.getId());
            ResultSet rs = stmt.executeQuery();
            System.out.println("Itens no inventário:");
            while (rs.next()) {
                System.out.println("- " + rs.getString("nome") + ": " + rs.getString("descricao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar inventário: " + e.getMessage());
        }
    }

    // Reset
    public void limparInventario(Usuario usuario) {
        String query = "DELETE FROM inventario_jogador WHERE usuario_id = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Inventário limpo.");
        } catch (SQLException e) {
            System.out.println("Erro ao limpar inventário: " + e.getMessage());
        }
    }
}


