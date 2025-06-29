package anderson20021001.etapa1;

import io.javalin.testtools.JavalinTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // Testa o endpoint GET /hello
    @Test
    void helloEndpointRetornaMensagemCorreta() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/hello");
            assertEquals(200, response.code());
            assertEquals("Hello, Javalin!", response.body().string());
        });
    }

    // Testa o POST /usuarios com status 201
    @Test
    void postUsuarioRetornaStatus201() {
        String usuarioJson = """
            {
                "nome": "Maria Teste",
                "email": "maria@test.com",
                "idade": 28
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.post("/usuarios", usuarioJson);
            assertEquals(201, response.code());
        });
    }

    // Testa GET /usuarios/{email} para um usuário existente
    @Test
    void buscarUsuarioPorEmailRetornaCorretamente() {
        String usuarioJson = """
            {
                "nome": "Carlos Teste",
                "email": "carlos@email.com",
                "idade": 40
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            client.post("/usuarios", usuarioJson);

            var response = client.get("/usuarios/carlos@email.com");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("Carlos Teste"));
        });
    }

    // Testa GET /usuarios retornando lista não vazia
    @Test
    void getUsuariosRetornaListaNaoVazia() {
        String usuarioJson = """
            {
                "nome": "Ana Clara",
                "email": "ana@teste.com",
                "idade": 35
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            client.post("/usuarios", usuarioJson);

            var response = client.get("/usuarios");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.startsWith("["));
            assertTrue(body.contains("Ana Clara"));
        });
    }

    // Testa POST /echo retornando mesmo JSON enviado
    @Test
    void echoPostRetornaMesmoConteudo() {
        String json = "{\"mensagem\":\"Testando echo\"}";

        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.post("/echo", json);

            assertEquals(200, response.code());
            assertEquals(json, response.body().string());
        });
    }

    // Testa GET /status retornando JSON com status e timestamp
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

    // Testa GET /saudacao/{nome} retornando mensagem personalizada
    @Test
    void saudacaoRetornaMensagemComNome() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/saudacao/Joana");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("Olá, Joana"));
        });
    }

    // ===================
    // Testes para /tarefas
    // ===================

    // Testa criação de tarefa via POST /tarefas
    @Test
    void createTaskTest() {
        String tarefaJson = """
            {
                "id": 1,
                "descricao": "Estudar Javalin",
                "concluida": false
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.post("/tarefas", tarefaJson);
            assertEquals(201, response.code());
        });
    }

    // Testa GET /tarefas/{id} para tarefa existente
    @Test
    void findTaskByIdTest() {
        String tarefaJson = """
            {
                "id": 2,
                "descricao": "Escrever testes",
                "concluida": true
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            client.post("/tarefas", tarefaJson);

            var response = client.get("/tarefas/2");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.contains("Escrever testes"));
        });
    }

    // Testa GET /tarefas listando todas as tarefas
    @Test
    void listTasksTest() {
        String tarefaJson = """
            {
                "id": 3,
                "descricao": "Revisar código",
                "concluida": false
            }
            """;

        JavalinTest.test(App::createApp, (server, client) -> {
            client.post("/tarefas", tarefaJson);

            var response = client.get("/tarefas");
            String body = response.body().string();

            assertEquals(200, response.code());
            assertTrue(body.startsWith("["));
            assertTrue(body.contains("Revisar código"));
        });
    }

    // Testa GET /tarefas/{id} com ID inválido (não numérico)
    @Test
    void getTarefaPorIdComIdInvalido() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/tarefas/abc");
            String body = response.body().string();

            assertEquals(400, response.code());
            assertTrue(body.contains("ID inválido"));
        });
    }

    // Testa GET /tarefas/{id} para tarefa não encontrada
    @Test
    void getTarefaPorIdNaoEncontrada() {
        JavalinTest.test(App::createApp, (server, client) -> {
            var response = client.get("/tarefas/9999");
            String body = response.body().string();

            assertEquals(404, response.code());
            assertTrue(body.contains("Tarefa não encontrada"));
        });
    }

}
