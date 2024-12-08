package org.yandex_practicum;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

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
        if (!subtasks.isEmpty()) {
            subtasks.clear();
        }
        if (!tasks.isEmpty()) {
            tasks.clear();
        }
        if (!epics.isEmpty()) {
            epics.clear();
        }
        return true;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public boolean createOrUpdateEpic(Task task) {
        if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
            //добавить метод для обновления списка имён подзадач у соответствующего эпика
            createOrUpdateEpic(checkAndUpgradeEpicStatus(getEpicIdByName(((Subtask) task).getEpicName())));
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

    private int getEpicIdByName(String epicName) {
        int id = 0;
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            if (entry.getValue().getName().equals(epicName)) id = entry.getValue().getId();
        }
        return id;
    }

    public boolean deleteById(int id) {
        if (subtasks.containsKey(id)) {
            createOrUpdateEpic(checkAndUpgradeEpicStatus(getEpicIdByName(getSubtaskById(id).getEpicName())));
            subtasks.remove(id);
            //после удаления подзадачи нужно обновить список имён подзадач у эпика
            return true;
        }
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            return true;
        }
        if (epics.containsKey(id)) {
            Task epicById = getEpicById(id);
            List<Subtask> subtasksByEpicName = getSubtasksByEpicName(epicById.getName());
            epics.remove(id);
            for (Subtask subtask : subtasksByEpicName) {
                subtasks.remove(subtask.getId());
            }
            //если удаляем эпик, то и все подзадачи связанные с ним тоже стираются
            return true;
        }
        return false;
    }

    public List<Subtask> getSubtasksByEpicName(String name) {
        List<Subtask> subtaskList = new ArrayList<>();
        for (Integer id : subtasks.keySet()) {
            if (subtasks.get(id).getEpicName().equals(name)) {
                subtaskList.add(subtasks.get(id));
            }
        }
        return subtaskList;
    }

    public Epic checkAndUpgradeEpicStatus(int id) {
        Epic epicById = getEpicById(id);
        List<Subtask> subtasksByEpicName = getSubtasksByEpicName(epicById.getName());
        //если хотя бы одна или все подзадачи в прогрессе или хотя бы одна подзадачана сделана - эпик в прогрессе,
        // если все подзадачи сделаны - эпик сделан.
        int progressStatusCount = 0;
        int doneStatusCount = 0;

        for (Subtask subtask : subtasksByEpicName) {
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) progressStatusCount++;
            if (subtask.getStatus().equals(TaskStatus.DONE)) doneStatusCount++;
        }
        if (progressStatusCount > 0 && progressStatusCount == subtasksByEpicName.size() || doneStatusCount > 0
                && doneStatusCount < subtasksByEpicName.size()) epicById.setStatus(TaskStatus.IN_PROGRESS);
        else if (doneStatusCount > 0 && doneStatusCount == subtasksByEpicName.size())
            epicById.setStatus(TaskStatus.DONE);

        return epicById;
    }
}
