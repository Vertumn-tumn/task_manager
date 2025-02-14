package org.yandex_practicum.manager;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class InMemoryTaskManager implements TaskManager<Task> {
    private static int id = 0;
    private Map<Integer, Subtask> subtasks;
    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    HistoryManager historyManager;

    public InMemoryTaskManager() {
        subtasks = new HashMap<>();
        tasks = new HashMap<>();
        epics = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public int hashCode() {
        return super.hashCode();
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
        historyManager.removeAllNode();
        return true;
    }

    @Override
    public Task getById(int id) {
        if (tasks.containsKey(id)) {
            Task task = tasks.get(id);
            historyManager.add(task);
            return task;
        }
        if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            historyManager.add(subtask);
            return subtask;
        }
        if (epics.containsKey(id)) {
            Epic epic = epics.get(id);
            historyManager.add(epic);
            return epic;
        }
        return null;
    }

    @Override
    public boolean createOrUpdateTask(Task task) {
        if (task instanceof Subtask) {
            subtasks.put(task.getId(), (Subtask) task);
            //добавить метод для обновления списка имён подзадач у соответствующего эпика
            createOrUpdateTask(checkAndUpgradeEpicStatus(getEpicIdBySubtaskName(((Subtask) task).getEpicName())));
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

    @Override
    public boolean deleteById(int id) {
        if (subtasks.containsKey(id)) {
            Subtask task = subtasks.get(id);
            subtasks.remove(id);
            historyManager.removeNode(id);
            createOrUpdateTask(checkAndUpgradeEpicStatus(getEpicIdBySubtaskName(task.getEpicName())));
            //после удаления подзадачи нужно обновить список имён подзадач у эпика
            return true;
        }
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            historyManager.removeNode(id);
            return true;
        }
        if (epics.containsKey(id)) {
            //если удаляем эпик, то и все подзадачи связанные с ним тоже стираются
            Task epicById = epics.get(id);
            List<Subtask> subtasksByEpicName = getSubtasksByEpicName(epicById.getName());
            for (Subtask subtask : subtasksByEpicName) {
                deleteById(subtask.getId());
            }
            epics.remove(id);
            historyManager.removeNode(id);
            return true;
        }
        return false;
    }

    int getEpicIdBySubtaskName(String epicName) {
        int id = 0;
        for (Map.Entry<Integer, Epic> entry : epics.entrySet()) {
            if (entry.getValue().getName().equals(epicName)) id = entry.getValue().getId();
        }
        return id;
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
        Epic epicById = epics.get(id);
        List<String> names = new ArrayList<>();
        List<Subtask> subtasksByEpicName = getSubtasksByEpicName(epicById.getName());
//        subtasksByEpicName.sort(Comparator.nullsLast(Comparator.comparing(Task::getStartTime).thenComparing(Task::getId)));

        //если хотя бы одна или все подзадачи в прогрессе или хотя бы одна подзадачана сделана - эпик в прогрессе,
        // если все подзадачи сделаны - эпик сделан.
        int progressStatusCount = 0;
        int doneStatusCount = 0;

        for (Subtask subtask : subtasksByEpicName) {
            if (subtask.getStatus().equals(TaskStatus.IN_PROGRESS)) progressStatusCount++;
            if (subtask.getStatus().equals(TaskStatus.DONE)) doneStatusCount++;
            names.add(subtask.getName());
        }
        if (progressStatusCount > 0
                || doneStatusCount > 0 && doneStatusCount < subtasksByEpicName.size())
            epicById.setStatus(TaskStatus.IN_PROGRESS);
        else if (doneStatusCount == subtasksByEpicName.size())
            epicById.setStatus(TaskStatus.DONE);

        epicById.setSubtaskNames(names);
        setWorkingPeriod(epicById, subtasksByEpicName);
        return epicById;
    }

    private void setWorkingPeriod(Epic epicById, List<Subtask> subtasksByEpicName) {
        if (!subtasksByEpicName.isEmpty()) {
            Duration total = epicById.getDuration();
            if (subtasksByEpicName.get(0).getStartTime() != null) {
                ZonedDateTime startTime = subtasksByEpicName.get(0).getStartTime();
                epicById.setStartTime(startTime);
            }
            Duration lastDuration = subtasksByEpicName.get(subtasksByEpicName.size() - 1).getDuration();
            if (total == null && lastDuration != null) total = Duration.ZERO.plus(lastDuration);
            else if(total == null && lastDuration == null) total=Duration.ZERO;
            else if(total != null && lastDuration == null) {
            }
            else total = total.plus(lastDuration);
            epicById.setDuration(total);
            if (epicById.getStartTime() != null) epicById.setEndTime(epicById.getStartTime().plus(total));
        }
    }

    @Override
    public List<Integer> history() {
        List<Integer> history = historyManager.getHistory();
        for (int i = 0; i < history.size(); i++) {
            if (i < history.size() - 1) System.out.print(history.get(i) + "-->");
            else System.out.print(history.get(i));
        }
        return history;
    }

    @Override
    public Map<Integer, Task> getPrioritizedTasks() {
        Map<Integer, Task> prioritizedMap = new HashMap<>();
        if (!tasks.isEmpty()) {
            prioritizedMap.putAll(tasks);
        }
        if (!epics.isEmpty()) {
            prioritizedMap.putAll(epics);
        }
        if (!subtasks.isEmpty()) {
            prioritizedMap.putAll(subtasks);
        }

        return prioritizedMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.nullsLast(
                        Comparator.comparing(Task::getStartTime, Comparator.nullsLast(Comparator.naturalOrder())))
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
