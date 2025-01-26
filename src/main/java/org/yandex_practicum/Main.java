package org.yandex_practicum;

import org.yandex_practicum.manager.InMemoryTaskManager;
import org.yandex_practicum.manager.Managers;
import org.yandex_practicum.manager.TaskManager;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.Data;
import org.yandex_practicum.util.TaskStatus;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();
        Data data = new Data();
        int id = InMemoryTaskManager.incrementAndGetId();
        manager.createOrUpdateTask(new Task("Сделать раз", "И раз"
                , id, TaskStatus.NEW));
        manager.getById(id);
        manager.history();
        System.out.println("\r\n");
        id = InMemoryTaskManager.incrementAndGetId();
        Epic epic = data.getEpic();
        epic.setId(id);
        manager.createOrUpdateTask(epic);
        manager.getById(id);
        manager.history();
        System.out.println("\r\n");
        Subtask subtask1 = data.getSubtask1();
        id = InMemoryTaskManager.incrementAndGetId();
        subtask1.setId(id);
        manager.createOrUpdateTask(subtask1);
        Subtask subtask2 = data.getSubtask2();
        id = InMemoryTaskManager.incrementAndGetId();
        subtask2.setId(id);
        manager.createOrUpdateTask(subtask2);
        Subtask subtask3 = data.getSubtask3();
        id = InMemoryTaskManager.incrementAndGetId();
        subtask3.setId(id);
        manager.createOrUpdateTask(subtask3);
        subtask1.setStatus(TaskStatus.IN_PROGRESS);
        manager.createOrUpdateTask(subtask1);
        subtask1.setStatus(TaskStatus.DONE);
        manager.createOrUpdateTask(subtask1);
        subtask2.setStatus(TaskStatus.DONE);
        manager.createOrUpdateTask(subtask2);
        subtask3.setStatus(TaskStatus.DONE);
        manager.createOrUpdateTask(subtask3);
        manager.getById(3);
        manager.getById(5);
        manager.getById(4);
        manager.history();
        System.out.println("\r\n");
        manager.getById(1);
        manager.getById(5);
        manager.history();
        System.out.println("\r\n");
        manager.getById(2);
        manager.getById(1);
        manager.history();
        System.out.println("\r\n");
        manager.deleteById(3);
        manager.history();
        System.out.println("\r\n");
        manager.deleteById(2);
        manager.history();
        System.out.println("\r\n");
        manager.deleteAllTask();
        manager.history();
    }
}