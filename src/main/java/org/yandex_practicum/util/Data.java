package org.yandex_practicum.util;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.model.Epic;
import org.yandex_practicum.model.Subtask;
import org.yandex_practicum.model.Task;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class Data {
    private String[] subtaskNames = {"open the door", "put on your shoes", "leave the field"};

    private static ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.systemDefault());

    public static int durationInMinutes = 120;


    private Task task = new Task("open the door"
            , "open the door in front of you", 1, TaskStatus.NEW);

    private Subtask subtask1 = new Subtask("open the door"
            , "open the door in front of you", 1, TaskStatus.NEW, "побег");
    private Subtask subtask2 = new Subtask("put on your shoes"
            , "put on your shoes quickly", 1, TaskStatus.NEW, "побег");
    private Subtask subtask3 = new Subtask("leave the field"
            , "leave the field Chayanda", 1, TaskStatus.NEW, "побег");

    private List<Subtask> subtasks = new ArrayList<>(Arrays.asList(subtask1, subtask2, subtask3));

    private Epic epic = new Epic("побег", "побег с вахт", 1, TaskStatus.NEW);
}
