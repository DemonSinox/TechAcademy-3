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
//            case "START":
//                comandosDAO.start(usuario);
//                break;
            case "HELP":
            System.out.println("Comandos disponíveis:");
            System.out.println("START: Inicia ou reinicia o jogo.");
            System.out.println("SAIR: Encerra o jogo ou pausa o jogo em andamento.");
            System.out.println("USE NOME_DO_ITEM: Usa um item do inventário (pode ser usado apenas na cena correta).");
            System.out.println("GET NOME_DO_ITEM: Adiciona um item disponível da cena ao seu inventário.");
            System.out.println("CHECK NOME_DO_ITEM: Inspeciona um item do inventário e exibe sua descrição.");
            System.out.println("INVENTORY: Exibe os itens presentes no seu inventário.");
            System.out.println("SAVE: Salva o progresso do jogo.");
            System.out.println("LOAD: Carrega o jogo salvo na última cena.");
            System.out.println("RESTART: Reinicia o jogo da cena 1, removendo todos os itens coletados.");
            break;
            case "SAIR":
                System.out.println(" ");
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
