package org.yandex_practicum.manager;

import org.junit.Before;
import org.junit.Test;
import org.yandex_practicum.DataTest;
import org.yandex_practicum.model.Task;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static org.junit.Assert.*;

public class FileBackedTasksManagerTest {
    Path file;
    FileBackedTasksManager manager;
    DataTest dataTest = new DataTest();

    @Before
    public void initialize() {
        file = Path.of("C:/Users/admin/Documents/FileForManager", String.valueOf(StandardCharsets.UTF_8));
        manager = (FileBackedTasksManager) Managers.getDefault();
        manager.createOrUpdateTask(dataTest.getTask());
        manager.createOrUpdateTask(dataTest.getEpic());
        manager.createOrUpdateTask(dataTest.getSubtask1());
        manager.createOrUpdateTask(dataTest.getSubtask2());
        manager.createOrUpdateTask(dataTest.getSubtask3());
    }

    @Test
    public void testDeleteAllTask() {
        assertTrue(manager.deleteAllTask());
        assertTrue(manager.getTasks().isEmpty());
    }

    @Test
    public void testGetById() {
        Task actual = manager.getById(1);
        Task expected = dataTest.getTask();
        assertEquals(expected, actual);

        actual = manager.getById(5);
        expected = dataTest.getSubtask3();
        assertEquals(expected, actual);

        actual = manager.getById(0);
        assertNull(actual);

        actual = manager.getById(6);
        assertNull(actual);
    }

    @Test
    public void testCreateOrUpdateTask() {
        Task task = dataTest.getTask();

        assertFalse(manager.createOrUpdateTask(null));
        assertTrue(manager.createOrUpdateTask(task));

        assertNotEquals(task.getId(), manager.getById(4).getId());
        assertEquals(task.getId(), manager.getById(task.getId()).getId());
    }

    @Test
    public void testDeleteById() {
        int id = dataTest.getTask().getId();

        assertTrue(manager.deleteById(id));
        assertFalse(manager.deleteById(id));

        id = dataTest.getEpic().getId();

        assertTrue(manager.deleteById(id));
        assertNull(manager.getById(dataTest.getSubtask1().getId()));
        assertNull(manager.getById(dataTest.getSubtask2().getId()));
        assertNull(manager.getById(dataTest.getSubtask3().getId()));
    }
}