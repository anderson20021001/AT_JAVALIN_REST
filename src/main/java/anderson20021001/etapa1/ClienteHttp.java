package anderson20021001.etapa1;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ClienteHttp {

    public static void main(String[] args) throws Exception {
        // 1️⃣ Enviar POST com novo usuário
        enviarPost();

        // 2️⃣ Fazer GET para listar usuários
        fazerGetListagem();

        // 3️⃣ Fazer GET para buscar usuário por email
        fazerGetPorEmail("joao@email.com");

        // 4️⃣ Fazer GET para /status
        fazerGetStatus();
    }

    // Método para enviar um POST com um novo usuário
    private static void enviarPost() throws IOException {
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
        conn.disconnect();
    }

    // Método para listar todos os usuários (GET /usuarios)
    private static void fazerGetListagem() throws IOException {
        URL url = new URL("http://localhost:7000/usuarios");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("🟢 GET /usuarios → Código de resposta: " + responseCode);
        System.out.println("Resposta:");
        imprimirResposta(conn);
        conn.disconnect();
    }

    // Método para buscar usuário por email (GET /usuarios/{email})
    private static void fazerGetPorEmail(String email) throws IOException {
        URL url = new URL("http://localhost:7000/usuarios/" + email);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int responseCode = conn.getResponseCode();
        System.out.println("🟡 GET /usuarios/" + email + " → Código de resposta: " + responseCode);
        System.out.println("Resposta:");
        imprimirResposta(conn);
        conn.disconnect();
    }

    // Método para consultar status da aplicação (GET /status)
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

    // Função utilitária para imprimir corpo da resposta
    private static void imprimirResposta(HttpURLConnection conn) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
            }
        }
    }
}
