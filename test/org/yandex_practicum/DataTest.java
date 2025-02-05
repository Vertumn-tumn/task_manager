package org.yandex_practicum;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;
import org.yandex_practicum.util.TaskStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Setter
@Getter
public class DataTest {
    private String[] subtaskNames = {"open the door", "put on your shoes", "leave the field"};


    private Task task = new Task("open the door"
            , "open the door in front of you", 1, TaskStatus.NEW);

    private Subtask subtask1 = new Subtask("open bla bla"
            , "open", 3, TaskStatus.NEW, "побег");
    private Subtask subtask2 = new Subtask("put on your shoes"
            , "put on your shoes quickly", 4, TaskStatus.NEW, "побег");
    private Subtask subtask3 = new Subtask("leave the field"
            , "leave the field Chayanda", 5, TaskStatus.NEW, "побег");

    private List<Subtask> subtasks = new ArrayList<>(Arrays.asList(subtask1, subtask2, subtask3));

    private Epic epic = new Epic("побег"
            , "побег с вахт", 2, TaskStatus.NEW);
}
