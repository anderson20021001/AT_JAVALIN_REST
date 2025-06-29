package anderson20021001.etapa1;

import io.javalin.Javalin;
import io.javalin.http.NotFoundResponse;
import io.javalin.http.BadRequestResponse;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class App {

    static List<Usuario> usuarios = new CopyOnWriteArrayList<>();
    static List<Tarefa> tarefas = new CopyOnWriteArrayList<>();

    public static Javalin createApp() {
        Javalin app = Javalin.create(config -> {
            config.http.defaultContentType = "application/json";
        });

        // Endpoints utilitários
        app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));

        app.get("/status", ctx -> ctx.json(Map.of(
                "status", "ok",
                "timestamp", Instant.now().toString()
        )));

        app.post("/echo", ctx -> {
            try {
                @SuppressWarnings("unchecked")
                Map<String, Object> body = (Map<String, Object>) ctx.bodyAsClass(Map.class);
                ctx.json(body);
            } catch (Exception e) {
                throw new BadRequestResponse("Formato JSON inválido");
            }
        });

        app.get("/saudacao/{nome}", ctx -> {
            String nome = ctx.pathParam("nome");
            ctx.json(Map.of("mensagem", "Olá, " + nome + "!"));
        });

        // CRUD de usuários
        app.post("/usuarios", ctx -> {
            Usuario usuario = ctx.bodyAsClass(Usuario.class);
            usuarios.add(usuario);
            ctx.status(201).json(usuario);
        });

        app.get("/usuarios", ctx -> {
            if (usuarios.isEmpty()) {
                ctx.status(204);
            } else {
                ctx.json(usuarios);
            }
        });

        app.get("/usuarios/{email}", ctx -> {
            String email = ctx.pathParam("email");
            Usuario usuario = usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst()
                    .orElseThrow(() -> new NotFoundResponse("Usuário não encontrado"));
            ctx.json(usuario);
        });

        app.put("/usuarios/{email}", ctx -> {
            String email = ctx.pathParam("email");
            Usuario novoUsuario = ctx.bodyAsClass(Usuario.class);
            for (int i = 0; i < usuarios.size(); i++) {
                if (usuarios.get(i).getEmail().equalsIgnoreCase(email)) {
                    usuarios.set(i, novoUsuario);
                    ctx.json(novoUsuario);
                    return;
                }
            }
            throw new NotFoundResponse("Usuário para atualizar não encontrado");
        });

        app.delete("/usuarios/{email}", ctx -> {
            String email = ctx.pathParam("email");
            boolean removido = usuarios.removeIf(u -> u.getEmail().equalsIgnoreCase(email));
            if (removido) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("Usuário para remoção não encontrado");
            }
        });

        // CRUD de tarefas
        app.post("/tarefas", ctx -> {
            try {
                Tarefa tarefa = ctx.bodyAsClass(Tarefa.class);
                tarefas.add(tarefa);
                ctx.status(201).json(tarefa);
            } catch (Exception e) {
                throw new BadRequestResponse("Requisição inválida para criação de tarefa");
            }
        });

        app.get("/tarefas", ctx -> {
            if (tarefas.isEmpty()) {
                ctx.status(204);
            } else {
                ctx.json(tarefas);
            }
        });

        app.get("/tarefas/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Tarefa tarefa = tarefas.stream()
                        .filter(t -> t.getId() == id)
                        .findFirst()
                        .orElseThrow(() -> new NotFoundResponse("Tarefa não encontrada"));
                ctx.json(tarefa);
            } catch (NumberFormatException e) {
                throw new BadRequestResponse("ID inválido para tarefa");
            }
        });

        app.put("/tarefas/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Tarefa novaTarefa = ctx.bodyAsClass(Tarefa.class);
            for (int i = 0; i < tarefas.size(); i++) {
                if (tarefas.get(i).getId() == id) {
                    tarefas.set(i, novaTarefa);
                    ctx.json(novaTarefa);
                    return;
                }
            }
            throw new NotFoundResponse("Tarefa para atualização não encontrada");
        });

        app.delete("/tarefas/{id}", ctx -> {
            int id = Integer.parseInt(ctx.pathParam("id"));
            boolean removida = tarefas.removeIf(t -> t.getId() == id);
            if (removida) {
                ctx.status(204);
            } else {
                throw new NotFoundResponse("Tarefa para remoção não encontrada");
            }
        });

        return app;
    }

    public static void main(String[] args) {
        createApp().start(7000);
    }
}
