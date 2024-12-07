package org.yandex_practicum;

import junit.framework.TestCase;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

import java.util.Arrays;

public class ManagerTest extends TestCase {
    Manager manager;
    String[] strings = {"открой дверь", "одень ботинки", "уехжай с месторождения"};

    public void setUp() throws Exception {
        super.setUp();
        manager = new Manager();
    }

    public void testCreateTask() {
        assertTrue(manager.createTask(new Task("open the door"
                , "open the door in front of you", Manager.incrementAndGetId(), TaskStatus.NEW)));
        assertEquals(1, manager.getTasks().size());
        assertFalse(manager.createTask(null));
    }

    public void testGetById() {
        Task expected = new Task("open the door"
                , "open the door in front of you", 1, TaskStatus.NEW);
        manager.createTask(new Task("open the door"
                , "open the door in front of you", Manager.incrementAndGetId(), TaskStatus.NEW));
        Task actual = manager.getTaskById(1);
        assertEquals(expected, actual);
        expected.setId(23);
        assertNotSame(expected, actual);
    }

    public void testGetSubtaskById() {
        Subtask expected = new Subtask("open the door"
                , "open the door in front of you", 1, TaskStatus.NEW, "побег");
        manager.createTask(new Subtask("open the door"
                , "open the door in front of you"
                , Manager.incrementAndGetId(), TaskStatus.NEW, "побег"));
        Task actual = manager.getSubtaskById(1);
        assertEquals(expected, actual);
        expected.setId(23);
        assertNotSame(expected, actual);
    }

    public void testGetEpicById() {
        Epic expected = new Epic("побег"
                , "побег с вахт", 1, TaskStatus.NEW, Arrays.asList(strings));
        manager.createTask(new Epic("побег"
                , "побег с вахт"
                , Manager.incrementAndGetId(), TaskStatus.NEW, Arrays.asList(strings)));
        Task actual = manager.getEpicById(1);
        assertEquals(expected, actual);
        expected.setId(23);
        assertNotSame(expected, actual);
    }
}