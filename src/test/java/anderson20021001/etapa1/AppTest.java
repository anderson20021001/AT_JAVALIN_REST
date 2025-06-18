package anderson20021001.etapa1;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Testa se o endpoint /hello retorna status 200 e a string correta
    @Test
    void helloEndpointRetornaMensagemCorreta() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/hello");
            assertEquals(200, response.code());
            assertEquals("Hello, Javalin!", response.body().string());
        });
    }

    // Testa se o endpoint /usuarios (POST) cadastra um novo usuário
    @Test
    void postUsuarioRetornaStatus201() {
        JavalinTest.test(App::createApp, (server, client) -> {
            String json = """
                {
                    "nome": "Maria Teste",
                    "email": "maria@test.com",
                    "idade": 28
                }
            """;

            var response = client.post("/usuarios", json);
            assertEquals(201, response.code());
        });
    }

    // Testa se o usuário recém cadastrado pode ser encontrado pelo e-mail
    @Test
    void buscarUsuarioPorEmailRetornaCorretamente() {
        JavalinTest.test(App::createApp, (server, client) -> {
            String json = """
                {
                    "nome": "Carlos Teste",
                    "email": "carlos@email.com",
                    "idade": 40
                }
            """;
            client.post("/usuarios", json);

            var response = client.get("/usuarios/carlos@email.com");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("Carlos Teste"));
        });
    }

    // Testa se a listagem de usuários retorna um array não vazio
    @Test
    void getUsuariosRetornaListaNaoVazia() {
        JavalinTest.test(App::createApp, (server, client) -> {
            String json = """
                {
                    "nome": "Ana Clara",
                    "email": "ana@teste.com",
                    "idade": 35
                }
            """;
            client.post("/usuarios", json);

            var response = client.get("/usuarios");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.startsWith("["), "Deve retornar uma lista");
            assertTrue(body.contains("Ana Clara"), "Deve conter o usuário cadastrado");
        });
    }

    // Testa se o echo retorna o mesmo conteúdo enviado
    @Test
    void echoPostRetornaMesmoConteudo() {
        JavalinTest.test(App::createApp, (server, client) -> {
            String json = "{\"mensagem\":\"Testando echo\"}";
            var response = client.post("/echo", json);

            assertEquals(200, response.code());
            assertEquals(json, response.body().string());
        });
    }

    // Testa se o /status retorna ok e um timestamp
    @Test
    void statusEndpointRetornaOkETimestamp() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/status");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("\"status\":\"ok\""));
            assertTrue(body.contains("timestamp"));
        });
    }

    // Testa a saudação personalizada
    @Test
    void saudacaoRetornaMensagemComNome() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/saudacao/Joana");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("Olá, Joana"));
        });
    }
}
