package anderson20021001.etapa1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClienteHttp {

    public static void main(String[] args) throws Exception {
        // Enviar POST com novo usuário
        enviarPostUsuario();

        // Fazer GET para listar usuários
        fazerGetUsuarios();

        // Fazer GET para buscar usuário por email
        fazerGetUsuarioPorEmail("joao@email.com");

        // Fazer GET para /status
        fazerGetStatus();

        // Enviar POST com nova tarefa
        criarTarefa();

        // Fazer GET para listar tarefas
        listarTarefas();

        // Fazer GET para buscar tarefa por ID
        buscarTarefaPorId(1);
    }

    private static void enviarPostUsuario() throws IOException {
        URL url = new URL("http://localhost:7000/usuarios");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String json = """
            {
                "nome": "João Silva",
                "email": "joao@email.com",
                "idade": 30
            }
        """;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int responseCode = conn.getResponseCode();
        System.out.println("🔵 POST /usuarios → Código de resposta: " + responseCode);
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void fazerGetUsuarios() throws IOException {
        URL url = new URL("http://localhost:7000/usuarios");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("🟢 GET /usuarios → Código de resposta: " + responseCode);
        System.out.println("Resposta:");
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void fazerGetUsuarioPorEmail(String email) throws IOException {
        URL url = new URL("http://localhost:7000/usuarios/" + email);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("🟡 GET /usuarios/" + email + " → Código de resposta: " + responseCode);
        System.out.println("Resposta:");
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void fazerGetStatus() throws IOException {
        URL url = new URL("http://localhost:7000/status");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("🔍 GET /status → Código de resposta: " + responseCode);
        System.out.println("Resposta:");
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void criarTarefa() throws IOException {
        URL url = new URL("http://localhost:7000/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");

        String json = """
            {
                "id": 1,
                "descricao": "Estudar para prova",
                "concluida": false
            }
        """;

        try (OutputStream os = conn.getOutputStream()) {
            os.write(json.getBytes(StandardCharsets.UTF_8));
        }

        int code = conn.getResponseCode();
        System.out.println("📘 POST /tarefas → Código: " + code);
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void listarTarefas() throws IOException {
        URL url = new URL("http://localhost:7000/tarefas");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int code = conn.getResponseCode();
        System.out.println("📒 GET /tarefas → Código: " + code);
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void buscarTarefaPorId(int id) throws IOException {
        URL url = new URL("http://localhost:7000/tarefas/" + id);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int code = conn.getResponseCode();
        System.out.println("🔎 GET /tarefas/" + id + " → Código: " + code);
        imprimirResposta(conn);
        conn.disconnect();
    }

    private static void imprimirResposta(HttpURLConnection conn) throws IOException {
        InputStream stream;
        int code = conn.getResponseCode();
        if (code >= 200 && code < 300) {
            stream = conn.getInputStream();
        } else {
            stream = conn.getErrorStream();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        }
    }
}
