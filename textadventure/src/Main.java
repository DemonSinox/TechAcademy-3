import modelo.Usuario;
import modelo.Itens;
import repositorioMysql.UsuarioDAO;
import repositorioMysql.CenaDAO;
import repositorioMysql.ItensDAO;
import modelo.Comandos;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CenaDAO cenaDAO = new CenaDAO();
        ItensDAO itensDAO = new ItensDAO();

        System.out.println("Escolha uma opção:");
        System.out.println("1. Login");
        System.out.println("2. Registro");
        int escolha = scanner.nextInt();
        scanner.nextLine();  // Consumir a nova linha

        Usuario usuario = null;

        if (escolha == 2) {
            // Registro de um novo usuário
            System.out.println("Vamos registrar uma nova conta.");
            System.out.print("Escolha um login: ");
            String login = scanner.nextLine();

            // Verifica se o login já existe
            if (usuarioDAO.verificarLoginExistente(login)) {
                System.out.println("Esse login já está em uso. Por favor, tente outro.");
                return;  // Finaliza o programa caso o login já esteja em uso
            } else {
                System.out.print("Escolha uma senha: ");
                String senha = scanner.nextLine();

                // Registrar o novo usuário
                if (usuarioDAO.registrarUsuario(login, senha)) {
                    System.out.println("Usuário registrado com sucesso! Faça login para jogar.");
                } else {
                    System.out.println("Erro no registro. Tente novamente.");
                    return;  // Finaliza o programa em caso de erro no registro
                }
            }
        }

        // Fluxo de login (ou após o registro)
        System.out.println("Faça login para continuar.");
        System.out.print("Login: ");
        String login = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        usuario = usuarioDAO.validarLogin(login, senha);
        if (usuario != null) {
            System.out.println("Bem-vindo, " + usuario.getLogin());

            while (true) {
                // Obter a cena atual do usuário
                int cenaAtual = usuario.getCenaIdSave();
                String descricaoCena = cenaDAO.buscarDescricaoCena(cenaAtual);
                System.out.println("Você está em: " + descricaoCena);

                // Exibir os itens disponíveis na cena atual
                List<Itens> itensDisponiveis = itensDAO.buscarItensPorCena(cenaAtual);
                if (itensDisponiveis.isEmpty()) {
                    System.out.println("Não há itens disponíveis nesta cena.");
                } else {
                    System.out.println("Itens disponíveis na cena:");
                    for (Itens item : itensDisponiveis) {
                        System.out.println("- " + item.getNome() + ": " + item.getDescricao());
                    }
                }

                // Processar o comando do jogador
                Comandos comandos = new Comandos(usuario);
                System.out.println("Digite um comando:");
                String inputComando = scanner.nextLine();
                comandos.processarComando(inputComando);
            }
        } else {
            System.out.println("Login ou senha inválidos.");
        }
    }
}
