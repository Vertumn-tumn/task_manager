package org.yandex_practicum.manager;

import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Task;


public interface TaskManager<T extends Task> {
    boolean deleteAllTask();

    Task getById(int id);

    boolean createOrUpdateTask(T task);

    boolean deleteById(int id);

    Epic checkAndUpgradeEpicStatus(int id);

    void history();
}
