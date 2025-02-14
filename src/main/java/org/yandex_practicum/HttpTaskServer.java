package org.yandex_practicum;

import com.sun.net.httpserver.HttpServer;
import org.yandex_practicum.handlers.HelloHandler;
import org.yandex_practicum.handlers.TaskHandler;

import java.io.IOException;
import java.net.InetSocketAddress;

public class HttpTaskServer {
    private static final int PORT = 8080;

    public static void main(String[] args) throws IOException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/hello", new HelloHandler());
        httpServer.createContext("/tasks", new TaskHandler());

        httpServer.start();
        Runtime.getRuntime().addShutdownHook(new Thread(()->stopServer(httpServer)));
        System.out.println("HttpServer запущен на порту 8080");
    }

    private static void stopServer(HttpServer httpServer) {
        httpServer.stop(0);
        System.out.println("HttpServer остановлен");
    }
}