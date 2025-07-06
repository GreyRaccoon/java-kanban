package com.yandex.kanban.service;

import com.yandex.kanban.model.Epic;
import com.yandex.kanban.model.Subtask;
import com.yandex.kanban.model.Task;

import java.util.ArrayList;

public interface TaskManager {
    // Tasks
    ArrayList<Task> getAllTasks();

    void createTask(Task task);

    void deleteAllTasks();

    Task getTaskById(int id);

    void updateTask(Task updatedTask);

    void deleteTaskById(int id);

    // Epics
    ArrayList<Epic> getAllEpics();

    void createEpic(Epic epic);

    void deleteAllEpics();

    Epic getEpicById(int id);

    void updateEpic(Epic updatedEpic);

    void deleteEpicById(int id);

    // Subtasks
    ArrayList<Subtask> getAllSubtasks();

    void createSubtask(Subtask subtask);

    void deleteAllSubtasks();

    Subtask getSubtaskById(int id);

    void updateSubtask(Subtask updatedSubtask);

    void deleteSubtaskById(int id);

    ArrayList<Subtask> getSubtasksByEpicId(int epicId);

    // Update Epic
    void updateEpicStatus(int epicId);

    // History
    ArrayList<Task> getHistory();
}
