package org.yandex_practicum.manager;

import org.yandex_practicum.model.Task;


public interface TaskManager<T extends Task> {
    boolean deleteAllTask();

    Task getById(int id);

    boolean createOrUpdateTask(T task);

    boolean deleteById(int id);

    void history();
}
