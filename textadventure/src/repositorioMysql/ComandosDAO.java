package repositorioMysql;

import modelo.Usuario;
import modelo.Itens;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ComandosDAO {
    private final Connection conn;

    public ComandosDAO() throws SQLException {
        this.conn = db.conectar();
    }

//    public boolean start(){
//
//        boolean Start = true;
//        System.out.println("Jogo iniciado. Use SAIR para para sair do jogo.");
//        return Start;
//
//    }
//
//    public boolean sair() {
//
//        boolean Start = false;
//        System.out.println("Jogo pausado. Use START para continuar.");
//        return Start;
//
//    }

    // use
    public void usarItem(Usuario usuario, String nomeItem) {
        try {
            // Verificar se o item está no inventário do jogador
            String sqlInventario = "SELECT i.id_itens, i.cena_id_destino FROM inventario_jogador ij " +
                    "JOIN itens i ON ij.item_id = i.id_itens " +
                    "WHERE ij.usuario_id = ? AND i.nome = ?";


            PreparedStatement stmt;
            stmt = conn.prepareStatement(sqlInventario);
            stmt.setInt(1, usuario.getCenaIdSave());
            stmt.setString(2, nomeItem);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                // O item está no inventário, agora podemos verificar e usar
                int cenaDestino = rs.getInt("cena_id_destino");

                // Atualizar a cena do usuário para a cena de destino do item
                String sqlAtualizarCena = "UPDATE usuarios SET cena_id_save = ? WHERE id_usuario = ?";

                PreparedStatement stmtUpdate = conn.prepareStatement(sqlAtualizarCena);
                stmtUpdate.setInt(1, cenaDestino);
                stmtUpdate.setInt(2, usuario.getCenaIdSave());

                int linhasAfetadas = stmtUpdate.executeUpdate();

                if (linhasAfetadas > 0) {
                    System.out.println("Você usou o item '" + nomeItem + "' e foi movido para a próxima cena.");
                } else {
                    System.out.println("Não foi possível atualizar sua cena.");
                }
            } else {
                // O item não está no inventário
                System.out.println("Você não possui o item '" + nomeItem + "' no seu inventário.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao usar o item: " + e.getMessage());
        }
    }

//    public void usarItem(Usuario usuario, String nomeItem) throws SQLException {
//        ItensDAO itensDAO = new ItensDAO();
//        Itens item = itensDAO.buscarItemPorNomeECena(nomeItem, usuario.getCenaIdSave());
//
//        if (item != null) {
//            System.out.println("Usando item: " + item.getNome());
//            System.out.println("Cena de destino: " + item.getCenaDestino());
//
//            // Save altomatico
//            usuario.setCenaIdSave(item.getCenaDestino());
//            salvarProgresso(usuario);
//
//            // delete o iten do iventario apos o uso
////            String query = "DELETE FROM inventario_jogador WHERE usuario_id = ?";
////            try {
////                PreparedStatement stmt = conn.prepareStatement(query);
////                stmt.setInt(1, usuario.getId());
////                stmt.executeUpdate();
////                System.out.println("Inventário limpo.");
////            } catch (SQLException e) {
////                System.out.println("Erro ao limpar inventário: " + e.getMessage());
////            }
//
//        } else {
//            System.out.println("Item não disponível nesta cena.");
//        }
//    }


    // get
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

    // check
    public void inspecionarItem(Usuario usuario, String nomeItem) throws SQLException {
        ItensDAO itensDAO = new ItensDAO();
        Itens item = itensDAO.buscarItemPorNomeECena(nomeItem, usuario.getCenaIdSave());

        if (item != null) {
            System.out.println("Inspecionando item: " + item.getDescricao());
        } else {
            System.out.println("Item não encontrado para inspecionar.");
        }
    }

    // save
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

    // start
    public void carregarProgresso(Usuario usuario) throws SQLException {
        System.out.println("Carregando cena salva...");
        CenaDAO cenaDAO = new CenaDAO();
        System.out.println(cenaDAO.buscarCenaPorId(usuario.getCenaIdSave()).getDescricao());
    }

    // restart
    public void reiniciarJogo(Usuario usuario) throws SQLException {
        usuario.setCenaIdSave(1);  // Cena inicial
        salvarProgresso(usuario);
        InventarioDAO inventarioJogadorDAO = new InventarioDAO();
        inventarioJogadorDAO.limparInventario(usuario);
        System.out.println("Jogo reiniciado. Cena inicial carregada.");
    }
}
