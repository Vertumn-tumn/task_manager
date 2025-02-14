package org.yandex_practicum.manager;

import org.yandex_practicum.model.Task;

import java.util.List;
import java.util.Map;


public interface TaskManager<T extends Task> {
    boolean deleteAllTask();

    Task getById(int id);

    boolean createOrUpdateTask(T task);

    boolean deleteById(int id);

    List<Integer> history();

    Map<Integer,T> getPrioritizedTasks();
}
