package com.yandex.kanban.model;

import java.util.ArrayList;

public class Epic extends Task {
    private final ArrayList<Integer> subtaskIds = new ArrayList<>();

    public Epic(String title, String description) {
        super(title, description);
    }

    public ArrayList<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int id) {
        subtaskIds.add(id);
    }

    public void removeSubtaskId(int id) {
        subtaskIds.remove(Integer.valueOf(id));
    }

    public void clearSubtaskIds() {
        subtaskIds.clear();
    }

    @Override
    public String toString() {
        int descriptionLength;
        if (getDescription() != null) {
            descriptionLength = getDescription().length();
        } else {
            descriptionLength = 0;
        }

        return "Epic{id=" + getId() +
                ", title='" + getTitle() + '\'' +
                ", description=" + descriptionLength +
                ", status=" + getStatus() +
                ", subtasks=" + subtaskIds.size() + "}";
    }
}
