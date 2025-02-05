package org.yandex_practicum.model;

import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.util.TaskStatus;
import org.yandex_practicum.util.TypeOfTask;

import java.util.Objects;

@Setter
@Getter
public class Subtask extends Task {
    private String epicName;

    public Subtask(String name, String description, int id, TaskStatus status, String epicName) {
        super(name, description, id, status);
        this.epicName = epicName;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), epicName);
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
        Subtask task = (Subtask) obj;
        return epicName.equals(task.getEpicName());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,",getId(), TypeOfTask.SUBTASK,getName(),getStatus(),getDescription());
    }
}
