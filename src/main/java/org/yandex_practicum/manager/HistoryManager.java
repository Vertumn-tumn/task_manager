package org.yandex_practicum.manager;

import org.yandex_practicum.model.Task;

import java.util.List;

public interface HistoryManager {
    void add(Task task);

    void removeNode(int id);

    void removeAllNode();

    List<Task> getHistory();
}
