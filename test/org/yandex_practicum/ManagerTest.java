package org.yandex_practicum;

import junit.framework.TestCase;
import org.yandex_practicum.manager.InMemoryTaskManager;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

import java.util.Arrays;
import java.util.List;

public class ManagerTest extends TestCase {
    /*InMemoryTaskManager manager;
    Util util;*/

    /*public void setUp() throws Exception {
        super.setUp();
        manager = new InMemoryTaskManager();
        util = new Util();
    }


    public void testCreateTask() {
        assertTrue(manager.createOrUpdateTask(new Task("open the door"
                , "open the door in front of you", InMemoryTaskManager.incrementAndGetId(), TaskStatus.NEW)));
        assertEquals(1, manager.getTasks().size());
        assertFalse(manager.createOrUpdateTask(null));
    }

    public void testGetTaskById() {
        manager.createOrUpdateTask(new Task("open the door"
                , "open the door in front of you", InMemoryTaskManager.incrementAndGetId(), TaskStatus.NEW));
        Task actual = manager.getTaskById(1);
        assertEquals(util.getTask(), actual);
    }

    public void testGetSubtaskById() {
        manager.createOrUpdateTask(new Subtask("open the door"
                , "open the door in front of you"
                , InMemoryTaskManager.incrementAndGetId(), TaskStatus.NEW, "побег"));
        Task actual = manager.getSubtaskById(1);
        assertEquals(util.getSubtask1(), actual);
    }

    public void testGetEpicById() {
        manager.createOrUpdateTask(new Epic("побег"
                , "побег с вахт"
                , InMemoryTaskManager.incrementAndGetId(), TaskStatus.NEW, Arrays.asList(util.getSubtaskNames())));
        Task actual = manager.getEpicById(1);
        assertEquals(util.getEpic(), actual);
    }

    public void testDeleteAllTask() {
        Epic epic = util.getEpic();
        Task task = util.getTask();
        task.setId(task.getId() + 1);
        Subtask subtask = util.getSubtask1();
        subtask.setId(subtask.getId() + 2);
        manager.createOrUpdateTask(epic);
        manager.createOrUpdateTask(task);
        manager.createOrUpdateTask(subtask);
        manager.deleteAllTask();
        assertTrue(manager.getTasks().isEmpty());
        assertTrue(manager.getSubtasks().isEmpty());
        assertTrue(manager.getEpics().isEmpty());
    }

    public void testDeleteById() {
        Epic epic = util.getEpic();
        Task task = util.getTask();
        Subtask subtask = util.getSubtask1();
        epic.setId(InMemoryTaskManager.incrementAndGetId());
        task.setId(InMemoryTaskManager.incrementAndGetId());
        subtask.setId(InMemoryTaskManager.incrementAndGetId());
        manager.createOrUpdateTask(epic);
        manager.createOrUpdateTask(task);
        manager.createOrUpdateTask(subtask);
        assertTrue(manager.deleteById(2));
        assertFalse(manager.deleteById(5));
    }

    public void testGetSubtasksByEpicName() {
        Epic epic = util.getEpic();
        epic.setId(InMemoryTaskManager.incrementAndGetId());
        manager.createOrUpdateTask(epic);
        Subtask subtask1 = util.getSubtask1();
        Subtask subtask2 = util.getSubtask2();
        Subtask subtask3 = util.getSubtask3();
        subtask1.setId(InMemoryTaskManager.incrementAndGetId());
        subtask2.setId(InMemoryTaskManager.incrementAndGetId());
        subtask3.setId(InMemoryTaskManager.incrementAndGetId());
        manager.createOrUpdateTask(subtask1);
        manager.createOrUpdateTask(subtask2);
        manager.createOrUpdateTask(subtask3);
        List<Subtask> subtasksByEpicName = manager.getSubtasksByEpicName(epic.getName());
        assertEquals(util.getSubtasks(), subtasksByEpicName);
    }

    public void testCheckEpicStatus() {
        Epic epic = util.getEpic();
        epic.setId(InMemoryTaskManager.incrementAndGetId());
        manager.createOrUpdateTask(epic);
        Subtask subtask1 = util.getSubtask1();
        Subtask subtask2 = util.getSubtask2();
        Subtask subtask3 = util.getSubtask3();
        subtask1.setId(InMemoryTaskManager.incrementAndGetId());
        subtask2.setId(InMemoryTaskManager.incrementAndGetId());
        subtask3.setId(InMemoryTaskManager.incrementAndGetId());
        subtask3.setStatus(TaskStatus.DONE);
        manager.createOrUpdateTask(subtask1);
        manager.createOrUpdateTask(subtask2);
        manager.createOrUpdateTask(subtask3);
        epic = manager.getEpicById(epic.getId());
        manager.deleteById(subtask3.getId());
        epic = manager.getEpicById(epic.getId());
        assertEquals(TaskStatus.IN_PROGRESS, epic.getStatus());
    }*/
}