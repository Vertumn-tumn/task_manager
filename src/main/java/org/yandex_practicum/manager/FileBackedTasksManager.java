package org.yandex_practicum.manager;

import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class FileBackedTasksManager extends InMemoryTaskManager {
    private String title = "id,type,name,status,description,epic";
    private Path file;

    public FileBackedTasksManager(String path) {
        this.file = Path.of(path);
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
        List<Task> history = super.historyManager.getHistory();

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
                    throw new RuntimeException(e);
                }
            });
            writer.newLine();
            history.forEach(task -> {
                try {
                    writer.write(task.getId() + ",");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
