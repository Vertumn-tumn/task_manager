package org.yandex_practicum.manager;

import org.junit.Before;
import org.junit.Test;
import org.yandex_practicum.DataTest;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InMemoryTaskManagerTest {
    InMemoryTaskManager manager;
    DataTest dataTest = new DataTest();

    List<Task> expected = new ArrayList<>();

    @Before
    public void initialize() {
        manager = new InMemoryTaskManager();
        ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());
        Duration duration1 = Duration.ofMinutes(30);
        Duration duration2 = Duration.ofMinutes(60);
        Duration duration3 = Duration.ofMinutes(90);
        Duration duration4 = Duration.ofMinutes(120);

        Task task = dataTest.getTask();

        task.setStartTime(dateTime);
        task.setDuration(duration1);
        manager.createOrUpdateTask(task);

        Epic epic = dataTest.getEpic();
        manager.createOrUpdateTask(epic);

        Subtask subtask1 = dataTest.getSubtask1();
        subtask1.setStartTime(dateTime.plus(duration1));
        subtask1.setDuration(duration4);
        manager.createOrUpdateTask(subtask1);

        Subtask subtask2 = dataTest.getSubtask2();
        subtask2.setStartTime(dateTime.plus(duration2));
//        subtask2.setDuration(duration3);
        manager.createOrUpdateTask(subtask2);

        Subtask subtask3 = dataTest.getSubtask3();
        subtask3.setStartTime(dateTime.plus(duration3));
        subtask3.setDuration(duration2);
        manager.createOrUpdateTask(subtask3);

        expected.add(task);
        expected.add(epic);
        expected.add(subtask1);
        expected.add(subtask2);
        expected.add(subtask3);
    }

    @Test
    public void testGetPrioritizedTasks() {
        List<Task> prioritizedTasks = manager.getPrioritizedTasks().values().stream().toList();
        assertEquals(expected, prioritizedTasks);
    }
}