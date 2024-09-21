package modelo;

import repositorioMysql.ComandosDAO;
import repositorioMysql.InventarioDAO;

import repositorioMysql.ItensDAO;

import java.sql.SQLException;

public class Comandos {
    private Usuario usuario;
    private ComandosDAO comandosDAO;
    private ItensDAO itensDAO;
    private InventarioDAO inventarioDAO;

    public Comandos(Usuario usuario) throws SQLException {
        this.usuario = usuario;
        this.comandosDAO = new ComandosDAO();
        this.itensDAO = new ItensDAO();
        this.inventarioDAO = new InventarioDAO();
    }

    public void processarComando(String comando) throws SQLException {
        String[] partes = comando.split(" ");
        String acao = partes[0].toUpperCase();

        switch (acao) {
            case "START":
                comandosDAO.start(usuario);
                break;
            case "SAIR":
                comandosDAO.sair();
                break;
            case "USE":
                if (partes.length > 1) {
                    comandosDAO.usarItem(usuario, partes[1]);
                } else {
                    System.out.println("Especifique um item para usar.");
                }
                break;
            case "GET":
                if (partes.length > 1) {
                    comandosDAO.pegarItem(usuario, partes[1]);
                } else {
                    System.out.println("Especifique um item para pegar.");
                }
                break;
            case "CHECK":
                if (partes.length > 1) {
                    comandosDAO.inspecionarItem(usuario, partes[1]);
                } else {
                    System.out.println("Especifique um item para inspecionar.");
                }
                break;
            case "INVENTORY":
                inventarioDAO.verInventario(usuario);
                break;
            case "SAVE":
                comandosDAO.salvarProgresso(usuario);
                break;
            case "LOAD":
                comandosDAO.carregarProgresso(usuario);
                break;
            case "RESTART":
                comandosDAO.reiniciarJogo(usuario);
                break;
            default:
                System.out.println("Comando inválido.");
        }
    }
}
