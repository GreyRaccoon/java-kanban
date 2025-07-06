package com.yandex.kanban.service;

import com.yandex.kanban.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager manager;

    @BeforeEach
    void setUp() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void shouldAddAndFindDifferentTaskTypes() {
        Task task = new Task("Task", "Description");
        manager.createTask(task);

        Epic epic = new Epic("Epic", "Description");
        manager.createEpic(epic);

        Subtask subtask = new Subtask("Subtask", "Description", epic.getId());
        manager.createSubtask(subtask);

        assertNotNull(manager.getTaskById(task.getId()), "Задача должна быть найдена");
        assertNotNull(manager.getEpicById(epic.getId()), "Эпик должен быть найден");
        assertNotNull(manager.getSubtaskById(subtask.getId()), "Подзадача должна быть найдена");
    }

    @Test
    void generatedAndManualIdsShouldNotConflict() {
        Task task1 = new Task("Task 1", "Description");
        manager.createTask(task1); // Генерируемый ID

        Task task2 = new Task("Task 2", "Description");
        task2.setId(100); // Ручной ID
        manager.createTask(task2);

        assertNotEquals(task1.getId(), task2.getId(),
                "Автоматические и ручные ID не должны конфликтовать");

        assertEquals(2, manager.getAllTasks().size(),
                "Обе задачи должны быть добавлены");
    }

    @Test
    void taskShouldRemainUnchangedAfterAdding() {
        Task original = new Task("Original", "Description");
        manager.createTask(original);

        Task saved = manager.getTaskById(original.getId());

        assertEquals(original.getTitle(), saved.getTitle(), "Название должно совпадать");
        assertEquals(original.getDescription(), saved.getDescription(), "Описание должно совпадать");
        assertEquals(original.getStatus(), saved.getStatus(), "Статус должен совпадать");
    }
}