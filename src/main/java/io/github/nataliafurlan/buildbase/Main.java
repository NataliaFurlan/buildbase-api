package io.github.nataliafurlan.buildbase;

import io.github.nataliafurlan.buildbase.db.DatabaseConnection;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        String senha = System.getenv("BUILDBASE_DB_PASSWORD");
        if (senha == null || senha.isEmpty()) {
            throw new IllegalStateException("Defina BUILDBASE_DB_PASSWORD para executar o teste.");
        }

        DatabaseConnection banco = new DatabaseConnection(
                "PostgreSql", "127.0.0.1", "15432", "buildbase", "buildbase_app", senha
        );

        banco.conect();
        if (banco.getC() == null) {
            throw new IllegalStateException("A conexão falhou. Confira o erro JDBC acima.");
        }

        try (var conexao = banco.getC(); var consulta = conexao.createStatement(); var resultado = consulta.executeQuery("SELECT current_database(), current_user")) {
            if (resultado.next()) {
                System.out.printf("Banco %s, usuário %s%n",
                        resultado.getString(1), resultado.getString(2));
            }
        } catch (java.sql.SQLException e) {
            throw new IllegalStateException("Falha ao executar a consulta de teste.", e);
        }

//        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//        
//        server.createContext("/health", (exchange) -> {
//            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
//                String response = "{\"status\": \"UP\"}";
//                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
//                
//                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
//                
//                exchange.sendResponseHeaders(200, bytes.length);
//                
//                try (OutputStream os = exchange.getResponseBody()) {
//                    os.write(bytes);
//                }
//            } else {
//                exchange.sendResponseHeaders(405, -1);
//            }
//        });
//        
//        server.setExecutor(null);
//        server.start();
//        System.out.println("Servidor rodando em http://localhost:8080/health");
    }
}
