package org.yandex_practicum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.yandex_practicum.util.TaskStatus;
import org.yandex_practicum.util.TypeOfTask;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Getter
@Setter
public class Task {
    private String name;
    private String description;
    private int id;
    private TaskStatus status;

    private ZonedDateTime startTime;

    private Duration duration;

    public Task(String name, String description, int id, TaskStatus status) {
        this.name = name;
        this.description = description;
        this.id = id;
        this.status = status;
        this.duration = Duration.ZERO;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, id, status, startTime, duration);
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
                && id == task.getId() && status.equals(task.getStatus())
                && startTime.equals(task.getStartTime()) && duration.equals(task.getDuration());
    }

    @Override
    public String toString() {
        return String.format("%s,%s,%s,%s,%s,", getId(), TypeOfTask.TASK, getName(), getStatus(), getDescription());
    }

    public ZonedDateTime getEndTime() {
        ZonedDateTime endTime = null;
        if (startTime != null && duration != null) {
            endTime = startTime.plus(duration);
        }
        assert endTime != null : "endTime was set to null";
        return Optional.of(endTime).get();
    }
}
