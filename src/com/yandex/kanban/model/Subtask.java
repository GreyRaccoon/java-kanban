package com.yandex.kanban.model;

public class Subtask extends Task {
    private final int epicId;

    public Subtask(String title, String description, int epicId) {
        super(title, description);
        this.epicId = epicId;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        int descriptionLength;
        if (getDescription() != null) {
            descriptionLength = getDescription().length();
        } else {
            descriptionLength = 0;
        }

        return "Subtask{id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description=" + descriptionLength +
                ", status=" + getStatus() +
                ", epicId=" + epicId + "}";
    }
}
