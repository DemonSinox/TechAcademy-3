package repositorioMysql;

import modelo.Cena;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CenaDAO {
    private final Connection conn;

    public CenaDAO() throws SQLException {
        this.conn = db.conectar();
    }

    public Cena buscarCenaPorId(int cenaId) {
        String query = "SELECT * FROM cena WHERE id_cena = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cenaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cena(rs.getInt("id_cena"), rs.getString("descricao"));
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar cena: " + e.getMessage());
        }
        return null;
    }

    // buscar a descrição da cena pelo ID da cena
    public String buscarDescricaoCena(int cenaId) {
        String query = "SELECT descricao FROM cena WHERE id_cena = ?";
        String descricao = null;

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, cenaId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                descricao = rs.getString("descricao");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar descrição da cena: " + e.getMessage());
        }
        return descricao;
    }
}
