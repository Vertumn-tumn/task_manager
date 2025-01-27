package org.yandex_practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.util.TypeOfTask;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class Task {
    private String name;
    private String description;
    private int id;
    private Enum status;

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Task task = (Task) obj;
        return name.equals(task.getName()) && description.equals(task.getDescription())
                && id == task.getId() && status.equals(task.getStatus());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,",getId(), TypeOfTask.TASK,getName(),getStatus(),getDescription());
    }
}
