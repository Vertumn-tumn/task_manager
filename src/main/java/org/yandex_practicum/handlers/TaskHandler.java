package org.yandex_practicum.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.yandex_practicum.manager.FileBackedTasksManager;
import org.yandex_practicum.manager.Managers;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.HttpMethod;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.List;

public class TaskHandler implements HttpHandler {
    private FileBackedTasksManager manager;
    private Gson gson;

    public TaskHandler() {
        this.manager = (FileBackedTasksManager) Managers.getDefault();
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .registerTypeAdapter(ZonedDateTime.class, new ZonedDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String uri = exchange.getRequestURI().toString().stripTrailing();
        HttpMethod method = Enum.valueOf(HttpMethod.class, exchange.getRequestMethod().toUpperCase());
        switch (method) {
            case GET -> {
                if (uri.equals("/tasks/task")) {
                    List<Task> tasks = manager.getTasks().values().stream().toList();
                    sendSuccessJsonResponse(exchange, tasks);
                }
                if (uri.equals("/tasks/subtask")) {
                    List<Subtask> subtaskList = manager.getSubtasks().values().stream().toList();
                    sendSuccessJsonResponse(exchange, subtaskList);
                }
                if (uri.equals("/tasks/epic")) {
                    List<Epic> epics = manager.getEpics().values().stream().toList();
                    sendSuccessJsonResponse(exchange, epics);
                }
                if (uri.contains("/tasks/task/?id=")) {
                    String[] strings = uri.split("=");
                    int id;
                    try {
                        id = Integer.parseInt(strings[1]);
                        Task byId = manager.getById(id);
                        sendSuccessJsonResponse(exchange, byId);
                    } catch (NumberFormatException e) {
                        String response = "Неправильно введен GET запрос на получение ресурса по ID";
                        sendClientFailJsonResponse(exchange, response);
                    }
                }
                if (uri.contains("/tasks/subtask/epic/?id=")) {
                    String[] strings = uri.split("=");
                    int id;
                    try {
                        id = Integer.parseInt(strings[1]);
                        Epic epic = manager.getEpics().get(id);
                        if (epic == null) {
                            String response = "Эпика по указанному ID не существует";
                            sendClientFailJsonResponse(exchange, response);
                        } else {
                            List<Subtask> subtasks = manager.getSubtasksByEpicName(epic.getName());
                            sendSuccessJsonResponse(exchange, subtasks);
                        }
                    } catch (NumberFormatException e) {
                        String response = "Неправильно введен GET запрос на получение подзадач по ID эпика";
                        sendClientFailJsonResponse(exchange, response);
                    }
                }
                if (uri.equals("/tasks/history")) {
                    List<Integer> history = manager.history();
                    sendSuccessJsonResponse(exchange, history);
                }
                if (uri.equals("/tasks")) {
                    List<Task> tasks = manager.getPrioritizedTasks().values().stream().toList();
                    if (tasks.isEmpty()) {
                        sendSuccessJsonResponse(exchange, "Список задач пуcт");
                    } else {
                        sendSuccessJsonResponse(exchange, tasks);
                    }
                }
            }
            case POST -> {
                if (uri.equals("/tasks/task")) {
                    String body;
                    try (InputStream requestBody = exchange.getRequestBody()) {
                        body = new String(requestBody.readAllBytes(), StandardCharsets.UTF_8);
                    }

                    List<String> type = exchange.getRequestHeaders().get("type");
                    if (type.contains("task")) {
                        Task task = gson.fromJson(body, Task.class);
                        manager.createOrUpdateTask(task);
                        exchange.sendResponseHeaders(201, -1);
                    }
                    if (type.contains("epic")) {
                        Epic task = gson.fromJson(body, Epic.class);
                        manager.createOrUpdateTask(task);
                        exchange.sendResponseHeaders(201, -1);
                    }
                    if (type.contains("subtask")) {
                        Subtask task = gson.fromJson(body, Subtask.class);
                        manager.createOrUpdateTask(task);
                        exchange.sendResponseHeaders(201, -1);
                    }
                } else {
                    String response = "Неправильно введен POST запрос";
                    sendClientFailJsonResponse(exchange, response);
                }
            }
            case DELETE -> {
                if (uri.contains("/tasks/task/?id=")) {
                    String[] strings = uri.split("=");
                    int id;
                    try {
                        id = Integer.parseInt(strings[1]);
                        boolean status = manager.deleteById(id);
                        if (status) {
                            sendSuccessJsonResponse(exchange, "removed");
                        } else {
                            sendClientFailJsonResponse(exchange, "Not found");
                        }
                    } catch (NumberFormatException e) {
                        String response = "Неправильно введен DELETE запрос на удаление ресурса по ID";
                        sendClientFailJsonResponse(exchange, response);
                    }
                }
                if (uri.equals("/tasks/task")) {
                    boolean status = manager.deleteAllTask();
                    if (status) {
                        sendSuccessJsonResponse(exchange, "All removed");
                    } else {
                        sendClientFailJsonResponse(exchange, "Something wrong with delete all");
                    }
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + method);
        }
    }

    private <T> void sendSuccessJsonResponse(HttpExchange exchange, T object) throws IOException {
        String json = gson.toJson(object);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(200, json.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }

    private <T> void sendClientFailJsonResponse(HttpExchange exchange, T object) throws IOException {
        String json = gson.toJson(object);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        exchange.sendResponseHeaders(400, json.getBytes(StandardCharsets.UTF_8).length);
        try (OutputStream responseBody = exchange.getResponseBody()) {
            responseBody.write(json.getBytes(StandardCharsets.UTF_8));
        }
    }
}
