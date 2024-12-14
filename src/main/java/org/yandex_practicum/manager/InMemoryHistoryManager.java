package org.yandex_practicum.manager;

import org.yandex_practicum.model.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> historyList;
    private int maxHistorySize = 10;

    public InMemoryHistoryManager() {
        this.historyList = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyList.size() < maxHistorySize) historyList.add(task);
        if (historyList.size() == maxHistorySize) {
            historyList.remove(0);
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
