package com.yandex.kanban.service;

import com.yandex.kanban.model.Epic;
import com.yandex.kanban.model.Subtask;
import com.yandex.kanban.model.Task;
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
    void shouldRemoveSubtaskFromEpic() {
        Epic epic = new Epic("Epic", "");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Sub", "", epic.getId());
        manager.createSubtask(subtask);

        manager.deleteSubtaskById(subtask.getId());

        assertFalse(epic.getSubtaskIds().contains(subtask.getId()));
    }

    @Test
    void shouldRemoveSubtasksAndHistoryWhenDeletingEpic() {
        Epic epic = new Epic("Epic", "");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Sub", "", epic.getId());
        manager.createSubtask(subtask);

        manager.getEpicById(epic.getId());
        manager.getSubtaskById(subtask.getId());
        manager.deleteEpicById(epic.getId());

        assertTrue(manager.getHistory().isEmpty());
        assertNull(manager.getSubtaskById(subtask.getId()));
    }

    @Test
    void shouldUpdateEpicStatusWhenSubtaskChanged() {
        Epic epic = new Epic("Epic", "");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Sub", "", epic.getId());
        manager.createSubtask(subtask);

        subtask.setStatus(Status.DONE);
        manager.updateSubtask(subtask);

        assertEquals(Status.DONE, epic.getStatus());
    }

    @Test
    void shouldIgnoreUpdateWithNonExistingId() {
        Task task = new Task("Task", "Desc");
        manager.createTask(task);
        int originalId = task.getId();

        task.setId(999); // Несуществующий ID
        manager.updateTask(task);

        Task savedTask = manager.getTaskById(originalId);
        assertEquals("Task", savedTask.getTitle());
    }

    @Test
    void shouldNotUpdateEpicIfSubtaskChangedWithoutManager() {
        Epic epic = new Epic("Epic", "");
        manager.createEpic(epic);
        Subtask subtask = new Subtask("Sub", "", epic.getId());
        manager.createSubtask(subtask);

        // Изменение без вызова updateSubtask()
        subtask.setStatus(Status.DONE);

        assertEquals(Status.NEW, manager.getEpicById(epic.getId()).getStatus());
    }
}