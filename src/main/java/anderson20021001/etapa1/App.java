// Declara o pacote onde a classe está localizada
package anderson20021001.etapa1;

// Importa a biblioteca Javalin, usada para criar aplicações web leves
import io.javalin.Javalin;

// Importa classes utilitárias para datas, coleções e listas thread-safe
import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

// Classe principal da aplicação
public class App {

    // Lista thread-safe para armazenar objetos do tipo Usuario (simulando um banco de dados em memória)
    static List<Usuario> usuarios = new CopyOnWriteArrayList<>();

    // Método responsável por criar e configurar a aplicação Javalin
    public static Javalin createApp() {
        Javalin app = Javalin.create(); // Inicializa a instância da aplicação Javalin

        // Rota GET simples que retorna um texto fixo
        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        // Rota GET que retorna o status da aplicação e a data/hora atual no formato ISO
        app.get("/status", ctx -> {
            Map<String, String> status = new HashMap<>();
            status.put("status", "ok"); // Status fixo
            status.put("timestamp", Instant.now().toString()); // Data/hora atual
            ctx.json(status); // Envia o JSON como resposta
        });

        // Rota POST que recebe um corpo JSON e devolve o mesmo JSON de volta (efeito "eco")
        app.post("/echo", ctx -> {
            Map<String, String> body = ctx.bodyAsClass(Map.class); // Converte o corpo para um Map
            ctx.json(body); // Retorna o mesmo corpo
        });

        // Rota GET com parâmetro de caminho, que retorna uma saudação personalizada
        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome"); // Lê o parâmetro de caminho "nome"
            ctx.json(Map.of("mensagem", "Olá, " + nome + "!")); // Retorna uma mensagem de saudação
        });

        // Rota POST para cadastrar um novo usuário na lista
        app.post("/usuarios", ctx -> {
            Usuario usuario = ctx.bodyAsClass(Usuario.class); // Converte o corpo JSON para objeto Usuario
            usuarios.add(usuario); // Adiciona o usuário à lista
            ctx.status(201); // Retorna status HTTP 201 (Created)
        });

        // Rota GET que retorna todos os usuários cadastrados
        app.get("/usuarios", ctx -> {
            ctx.json(usuarios); // Retorna a lista de usuários como JSON
        });

        // Rota GET para buscar um usuário pelo e-mail
        app.get("/usuarios/{email}", ctx -> {
            String email = ctx.pathParam("email"); // Lê o e-mail da URL
            Optional<Usuario> usuario = usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email)) // Filtra pela lista ignorando caixa alta/baixa
                    .findFirst(); // Retorna o primeiro usuário encontrado (se existir)

            if (usuario.isPresent()) {
                ctx.json(usuario.get()); // Se encontrado, retorna o usuário
            } else {
                ctx.status(404).json(Map.of("erro", "Usuário não encontrado")); // Se não encontrado, retorna erro 404
            }
        });

        // Retorna a aplicação configurada
        return app;
    }

    // Método principal que inicia o servidor Javalin na porta 7000
    public static void main(String[] args) {
        createApp().start(7000);
    }
}
