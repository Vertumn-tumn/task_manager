package org.yandex_practicum;

import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.createTask(new Task("open the door"
                ,"open the door in front of you",Manager.incrementAndGetId(), TaskStatus.NEW));
    }
}