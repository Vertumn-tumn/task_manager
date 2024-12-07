package org.yandex_practicum;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class Manager {
    private static int id = 0;
    private Map<Integer, Subtask> subtasks;
    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;

    public Manager() {
        subtasks = new HashMap<>();
        tasks = new HashMap<>();
        epics = new HashMap<>();
    }

    public static int incrementAndGetId() {
        return ++id;
    }

    public boolean deleteAllTask() {
        return false;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Task getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Task getEpicById(int id) {
        return epics.get(id);
    }

    public boolean createTask(Task task) {
        if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
            return true;
        } else if (task instanceof Epic) {
            epics.put(task.getId(), (Epic) task);
            return true;
        } else if (task != null) {
            tasks.put(task.getId(), task);
            return true;
        }
        return false;
    }

    public boolean updateTask(Task task) {
        return false;
    }

    public boolean deleteById(int id) {
        return false;
    }

    public List<Subtask> getSubtasksByEpicName(String name) {
        return new ArrayList<>();
    }

    public boolean checkEpicStatus() {
        return false;
    }
}
