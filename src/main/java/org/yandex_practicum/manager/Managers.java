package org.yandex_practicum.manager;

public class Managers {
    public static TaskManager getDefault() {
        return new FileBackedTasksManager("C:/Users/admin/Documents/FileForManager");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
