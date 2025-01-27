package org.yandex_practicum.manager;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class InMemoryHistoryManager implements HistoryManager {
    private NonDuplicateLinkedList historyList;
    private int maxHistorySize;
    private NodeTable nodeTable;

    public InMemoryHistoryManager() {
        this.historyList = new NonDuplicateLinkedList();
        this.nodeTable = new NodeTable();
        maxHistorySize = 10;
    }

    class NonDuplicateLinkedList extends LinkedList<Integer> {
        public void linkLast(Task task) {
            if (nodeTable.getLastSpaceOfTaskMap().containsKey(task.getId())) {
                int last = nodeTable.getLastSpaceOfTaskMap().get(task.getId());
                historyList.remove(last);
            }
            addLast(task.getId());
            nodeTable.updateTable(historyList);
        }

        public ArrayList<Integer> getTasks() {
            return new ArrayList<>(historyList);
        }
    }

    @Override
    public void add(Task task) {
        if (historyList.size() < maxHistorySize) historyList.linkLast(task);
        if (historyList.size() == maxHistorySize) {
            historyList.remove(0);
            historyList.linkLast(task);
        }
    }

    @Override
    public void removeNode(int id) {
        if (nodeTable.getLastSpaceOfTaskMap().containsKey(id)) {
            int last = nodeTable.getLastSpaceOfTaskMap().get(id);
            historyList.remove(last);
            nodeTable.updateTable(historyList);
        }
    }

    @Override
    public void removeAllNode() {
        if (!historyList.isEmpty()) historyList.clear();
        if (!nodeTable.getLastSpaceOfTaskMap().isEmpty()) nodeTable.updateTable(historyList);
    }

    @Override
    public List<Integer> getHistory() {
        return historyList.getTasks();
    }
}
