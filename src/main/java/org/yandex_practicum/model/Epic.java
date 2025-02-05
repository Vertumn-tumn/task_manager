package org.yandex_practicum.model;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.util.TaskStatus;
import org.yandex_practicum.util.TypeOfTask;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Epic extends Task {
    private List<String> subtaskNames;
    private ZonedDateTime endTime;

    public Epic(String name, String description, int id, TaskStatus status) {
        super(name, description, id, status);
        this.subtaskNames = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskNames, endTime);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        if (!super.equals(obj)) {
            return false;
        }
        Epic task = (Epic) obj;
        return subtaskNames.equals(task.getSubtaskNames()) && endTime.equals(task.getEndTime());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,", getId(), TypeOfTask.EPIC, getName(), getStatus(), getDescription());
    }
}
