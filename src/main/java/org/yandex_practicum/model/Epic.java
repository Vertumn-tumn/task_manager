package org.yandex_practicum.model;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Epic extends Task {
    private List<String> subtaskNames;

    public Epic(String name, String description, int id, Enum status) {
        super(name, description, id, status);
        this.subtaskNames = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subtaskNames);
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
        return subtaskNames.equals(task.getSubtaskNames());
    }
}
