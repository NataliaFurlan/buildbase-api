package io.github.nataliafurlan.buildbase;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

public class Main {
    
    public static void main(String[] args) throws IOException {
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        server.createContext("/health", (exchange) -> {
            if ("GET".equalsIgnoreCase(exchange.getRequestMethod())) {
                String response = "{\"status\": \"UP\"}";
                byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
                
                exchange.sendResponseHeaders(200, bytes.length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(bytes);
                }
            } else {
                exchange.sendResponseHeaders(405, -1);
            }
        });
        
        server.setExecutor(null);
        server.start();
        System.out.println("Servidor rodando em http://localhost:8080/health");
    }
}
