package com.yandex.kanban.service;

import com.yandex.kanban.model.Epic;
import com.yandex.kanban.model.Subtask;
import com.yandex.kanban.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {
    private int nextId = 1;
    private final HashMap<Integer, Task> tasks = new HashMap<>();
    private final HashMap<Integer, Epic> epics = new HashMap<>();
    private final HashMap<Integer, Subtask> subtasks = new HashMap<>();
    private final HistoryManager historyManager = Managers.getDefaultHistory();

    // Tasks
    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void createTask(Task task) {
        task.setId(nextId++);
        tasks.put(task.getId(), task);
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        if (task != null) {
            historyManager.add(task);
        }
        return task;
    }

    @Override
    public void updateTask(Task updatedTask) {
        if (tasks.containsKey(updatedTask.getId())) {
            tasks.put(updatedTask.getId(), updatedTask);
        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    // Epics
    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(nextId++);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic = epics.get(id);
        if (epic != null) {
            historyManager.add(epic);
        }
        return epic;
    }

    @Override
    public void updateEpic(Epic updatedEpic) {
        Epic existingEpic = epics.get(updatedEpic.getId());
        if (existingEpic != null) {
            existingEpic.setTitle(updatedEpic.getTitle());
            existingEpic.setDescription(updatedEpic.getDescription());
        }
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                subtasks.remove(subtaskId);
                historyManager.remove(subtaskId);
            }
            historyManager.remove(id);
        }
    }

    // Subtasks
    @Override
    public ArrayList<Subtask> getAllSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public void createSubtask(Subtask subtask) {
        int epicId = subtask.getEpicId();
        Epic epic = epics.get(epicId);

        if (epic == null || subtask.getId() == epicId) {
            return;
        }

        if (subtask.getId() == 0) {
            subtask.setId(nextId++);
        }

        int subtaskId = subtask.getId();

        if (subtaskId == epicId) {
            return;
        }

        if (tasks.containsKey(subtaskId) || epics.containsKey(subtaskId) || subtasks.containsKey(subtaskId)) {
            return;
        }

        subtasks.put(subtaskId, subtask);
        epic.addSubtaskId(subtaskId);
        updateEpicStatus(epicId);
    }

    @Override
    public void deleteAllSubtasks() {
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            updateEpicStatus(epic.getId());
        }
        subtasks.clear();
    }

    @Override
    public Subtask getSubtaskById(int id) {
        Subtask subtask = subtasks.get(id);
        if (subtask != null) {
            historyManager.add(subtask);
        }
        return subtask;
    }

    @Override
    public void updateSubtask(Subtask updatedSubtask) {
        int id = updatedSubtask.getId();
        if (subtasks.containsKey(id)) {
            subtasks.put(id, updatedSubtask);
            updateEpicStatus(updatedSubtask.getEpicId());
        }
    }

    @Override
    public void deleteSubtaskById(int id) {
        Subtask subtask = subtasks.remove(id);
        if (subtask != null) {
            Epic epic = epics.get(subtask.getEpicId());
            if (epic != null) {
                epic.removeSubtaskId(id);
                updateEpicStatus(epic.getId());
            }
            historyManager.remove(id);
        }
    }


    @Override
    public ArrayList<Subtask> getSubtasksByEpicId(int epicId) {
        ArrayList<Subtask> result = new ArrayList<>();
        Epic epic = epics.get(epicId);
        if (epic != null) {
            for (int subtaskId : epic.getSubtaskIds()) {
                Subtask subtask = subtasks.get(subtaskId);
                if (subtask != null) {
                    result.add(subtask);
                }
            }
        }
        return result;
    }

    // Update Epic
    @Override
    public void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        if (epic == null) return;

        ArrayList<Subtask> epicSubtasks = getSubtasksByEpicId(epicId);
        if (epicSubtasks.isEmpty()) {
            epic.setStatus(Status.NEW);
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (Subtask subtask : epicSubtasks) {
            if (subtask.getStatus() == Status.IN_PROGRESS) {
                epic.setStatus(Status.IN_PROGRESS);
                return;
            }
            if (subtask.getStatus() != Status.NEW) allNew = false;
            if (subtask.getStatus() != Status.DONE) allDone = false;
        }

        if (allDone) epic.setStatus(Status.DONE);
        if (allNew) epic.setStatus(Status.NEW);
    }

    // History
    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }
}
