package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;
    private final LinkedList<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (task == null) return;

        Task taskCopy = createTaskCopy(task);

        if (history.size() >= MAX_HISTORY_SIZE) {
            history.removeFirst();
        }
        history.addLast(taskCopy);
    }

    @Override
    public void remove(int id) {
        history.removeIf(task -> task.getId() == id);
    }

    @Override
    public ArrayList<Task> getHistory() {
        return new ArrayList<>(history);
    }

    private Task createTaskCopy(Task original) {
        Task copy = new Task(original.getTitle(), original.getDescription());
        copy.setId(original.getId());
        copy.setStatus(original.getStatus());
        return copy;
    }

}