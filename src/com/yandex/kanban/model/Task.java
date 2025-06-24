package com.yandex.kanban.model;

import java.util.Objects;
import com.yandex.kanban.service.Status;

public class Task {
    private int id;
    private   String title;
    private String description;
    private Status status;


    public Task(String title, String description) {
        this.title = title;
        this.description = description;
        status = Status.NEW;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        int descriptionLength;
        if (description != null) {
            descriptionLength = description.length();
        } else {
            descriptionLength = 0;
        }

        return "com.yandex.kanban.model.Task{id=" + id +
                ", title='" + title + '\'' +
                ", description=" + descriptionLength +
                ", status=" + status + "}";
    }
}
