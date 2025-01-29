package org.yandex_practicum.manager;

import org.yandex_practicum.exceptions.ManagerSaveException;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private String title = "id,type,name,status,description,epic";
    private Path file;

    public FileBackedTasksManager(String path) {
        this.file = Path.of(path);
    }

    @Override
    public Task getById(int id) {
        Task byId = super.getById(id);
        save();
        return byId;
    }

    @Override
    public boolean deleteAllTask() {
        boolean result = super.deleteAllTask();
        save();
        return result;
    }

    @Override
    public boolean createOrUpdateTask(Task task) {
        boolean result = super.createOrUpdateTask(task);
        save();
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = super.deleteById(id);
        save();
        return result;
    }

    private void save() {
        Map<Integer, Subtask> subtasks = super.getSubtasks();
        Map<Integer, Task> tasks = super.getTasks();
        Map<Integer, Epic> epics = super.getEpics();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file.toString()))) {
            writer.write(title + "\n");

            if (!tasks.isEmpty()) tasks.forEach((integer, task) -> {
                try {
                    writer.write(task.toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            if (!epics.isEmpty()) epics.forEach((integer, epic) -> {
                try {
                    writer.write(epic.toString() + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            if (!subtasks.isEmpty()) subtasks.forEach((integer, subtask) -> {
                try {
                    writer.write(subtask.toString() + getEpicIdBySubtaskName(subtask.getEpicName()) + "\n");
                } catch (IOException e) {
                    throw new ManagerSaveException(e);
                }
            });
            writer.newLine();
            writer.write(FileBackedTasksManager.toString(super.historyManager));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String toString(HistoryManager historyManager) {
        StringBuilder builder = new StringBuilder();
        List<Integer> history = historyManager.getHistory();
        history.forEach(id -> builder.append(id).append(","));
        return builder.toString();
    }

    public static FileBackedTasksManager loadFromFile(Path file) {
        FileBackedTasksManager manager = (FileBackedTasksManager) Managers.getDefault();
        try {
            ArrayList<String> strings = (ArrayList<String>) Files.readAllLines(file, StandardCharsets.UTF_8);
            String[] split = strings.get(strings.size() - 1).split(",");
            manager.getHistoryManager().setHistory(Arrays.asList(split));
            manager.fillOut(strings.subList(1, strings.size() - 2));

        } catch (IOException e) {
            throw new ManagerSaveException(e);
        }
        return manager;
    }

    private void fillOut(List<String> subList) {
        for (String desc : subList) {
            String[] split = desc.split(",");
            switch (split[1]) {
                case "TASK" -> {
                    Task task = toTask(desc);
                    createOrUpdateTask(task);
                }
                case "EPIC" -> {
                    Epic epic = toEpic(desc);
                    createOrUpdateTask(epic);
                }
                case "SUBTASK" -> {
                    Subtask subtask = toSubTask(desc);
                    createOrUpdateTask(subtask);
                }
                default -> System.out.println("Wrong input");
            }
        }
    }

    private Task toTask(String description) {
        String[] split = description.split(",");
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        String desc = split[4];
        TaskStatus status = Enum.valueOf(TaskStatus.class, split[3]);
        return new Task(name, desc, id, status);
    }

    private Epic toEpic(String description) {
        String[] split = description.split(",");
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        String desc = split[4];
        TaskStatus status = Enum.valueOf(TaskStatus.class, split[3]);
        return new Epic(name, desc, id, status);
    }

    private Subtask toSubTask(String description) {
        String[] split = description.split(",");
        int id = Integer.parseInt(split[0]);
        String name = split[2];
        String desc = split[4];
        TaskStatus status = Enum.valueOf(TaskStatus.class, split[3]);
        String epicName = super.getEpics().get(Integer.parseInt(split[5])).getName();
        return new Subtask(name, desc, id, status, epicName);
    }
}
