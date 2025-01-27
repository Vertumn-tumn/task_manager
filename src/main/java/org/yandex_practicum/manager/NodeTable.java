package org.yandex_practicum.manager;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class NodeTable {
    private Map<Integer, Integer> lastSpaceOfTaskMap;

    public NodeTable() {
        this.lastSpaceOfTaskMap = new HashMap<>();
    }

    public void updateTable(List<Integer> historyList) {
        if (historyList.size() != 0) {
            lastSpaceOfTaskMap = new HashMap<>(historyList.size());
            for (int i = 0; i < historyList.size(); i++) {
                lastSpaceOfTaskMap.put(historyList.get(i), i);
            }
        }
    }
}
