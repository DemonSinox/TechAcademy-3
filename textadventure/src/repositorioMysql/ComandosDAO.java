package repositorioMysql;

import modelo.Usuario;
import modelo.Itens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComandosDAO {
    private final Connection conn;

    public ComandosDAO() throws SQLException {
        this.conn = db.conectar();
    }

    public void start(Usuario usuario) throws SQLException {
        CenaDAO cenaDAO = new CenaDAO();
        System.out.println("Cena inicial: " + cenaDAO.buscarCenaPorId(usuario.getCenaIdSave()).getDescricao());
    }

    public void sair() {
        System.out.println("Jogo pausado. Use START para continuar.");
    }

    public void usarItem(Usuario usuario, String nomeItem) throws SQLException {
        ItensDAO itensDAO = new ItensDAO();
        Itens item = itensDAO.buscarItemPorNomeECena(nomeItem, usuario.getCenaIdSave());

        if (item != null) {
            System.out.println("Usando item: " + item.getNome());
            System.out.println("Cena de destino: " + item.getCenaDestino());
            // Atualizar cena do usuário
            usuario.setCenaIdSave(item.getCenaDestino());
            salvarProgresso(usuario);
        } else {
            System.out.println("Item não disponível nesta cena.");
        }
    }

    public void pegarItem(Usuario usuario, String nomeItem) throws SQLException {
        ItensDAO itensDAO = new ItensDAO();
        Itens item = itensDAO.buscarItemPorNomeECena(nomeItem, usuario.getCenaIdSave());

        if (item != null) {
            InventarioDAO inventarioDAO = new InventarioDAO();
            inventarioDAO.adicionarAoInventario(usuario, item);
            System.out.println("Você pegou o item: " + item.getNome());
        } else {
            System.out.println("Item não encontrado nesta cena.");
        }
    }

    public void inspecionarItem(Usuario usuario, String nomeItem) throws SQLException {
        ItensDAO itensDAO = new ItensDAO();
        Itens item = itensDAO.buscarItemPorNomeECena(nomeItem, usuario.getCenaIdSave());

        if (item != null) {
            System.out.println("Inspecionando item: " + item.getDescricao());
        } else {
            System.out.println("Item não encontrado para inspecionar.");
        }
    }

    public void salvarProgresso(Usuario usuario) {
        String query = "UPDATE usuarios SET cena_id_save = ? WHERE id_usuario = ?";
        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, usuario.getCenaIdSave());
            stmt.setInt(2, usuario.getId());
            stmt.executeUpdate();
            System.out.println("Progresso salvo.");
        } catch (SQLException e) {
            System.out.println("Erro ao salvar progresso: " + e.getMessage());
        }
    }

    public void carregarProgresso(Usuario usuario) throws SQLException {
        System.out.println("Carregando cena salva...");
        CenaDAO cenaDAO = new CenaDAO();
        System.out.println(cenaDAO.buscarCenaPorId(usuario.getCenaIdSave()).getDescricao());
    }

    public void reiniciarJogo(Usuario usuario) throws SQLException {
        usuario.setCenaIdSave(1);  // Cena inicial
        salvarProgresso(usuario);
        InventarioDAO inventarioJogadorDAO = new InventarioDAO();
        inventarioJogadorDAO.limparInventario(usuario);
        System.out.println("Jogo reiniciado. Cena inicial carregada.");
    }
}
