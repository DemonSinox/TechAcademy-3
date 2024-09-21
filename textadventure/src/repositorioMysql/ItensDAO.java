package repositorioMysql;

import modelo.Itens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItensDAO {
    private final Connection conn;

    public ItensDAO() throws SQLException {
        this.conn = db.conectar();
    }

    // buscar item por nome e id de cena
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

    //  tive que fazer para resulver um bug só muda que é um list e procura por id só
    public List<Itens> buscarItensPorCena(int cenaId) {
        String query = "SELECT * FROM itens WHERE cena_id_atual = ?";
        List<Itens> itens;
        itens = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cenaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Itens item = new Itens(
                        rs.getInt("id_itens"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getInt("cena_id_atual"),
                        rs.getInt("cena_id_destino")
                );
                itens.add(item);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar itens da cena: " + e.getMessage());
        }
        return itens;
    }
}
