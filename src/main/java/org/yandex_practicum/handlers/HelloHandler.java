package org.yandex_practicum.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class HelloHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String response = "Glad to see your here";

        System.out.println("Success: " + LocalDateTime.now());
        String uri = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();
        String decodeUri = URLDecoder.decode(uri, StandardCharsets.UTF_8);


        String[] split = decodeUri.split("/");
        switch (method) {
            case "GET" -> {
                exchange.sendResponseHeaders(200, 0);
                try (OutputStream body = exchange.getResponseBody()) {
                    body.write(response.getBytes());
                }
            }
            case "POST" -> {
                if (exchange.getRequestHeaders().get("Glad-To-See-You-X").contains("true")) {
                    exchange.getResponseHeaders().add("Glad-To-See-You-X", "true");
                    String format = String.format("%s %s %s!", response, split[2], split[3]);
                    exchange.sendResponseHeaders(201, format.getBytes(StandardCharsets.UTF_8).length);
                    try (OutputStream body = exchange.getResponseBody()) {
                        body.write(format.getBytes());
                    }
                } else {
                    String format = String.format("%s %s!", response, split[2]);
                    exchange.sendResponseHeaders(206, format.getBytes(StandardCharsets.UTF_8).length);
                    try (OutputStream body = exchange.getResponseBody()) {
                        body.write(format.getBytes());
                    }
                }
            }
            default -> System.out.println("Некорректный ввод!");
        }
    }

}
