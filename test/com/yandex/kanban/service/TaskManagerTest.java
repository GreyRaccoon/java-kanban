package com.yandex.kanban.service;

import com.yandex.kanban.model.Epic;
import com.yandex.kanban.model.Subtask;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private final TaskManager manager = new InMemoryTaskManager();

    @Test
    void shouldNotAddEpicAsOwnSubtask() {
        Epic epic = new Epic("Epic", "Description");
        manager.createEpic(epic);
        int epicId = epic.getId();

        Subtask subtask = new Subtask("Invalid", "Subtask", epicId);
        subtask.setId(epicId);

        int subtaskCountBefore = manager.getAllSubtasks().size();
        manager.createSubtask(subtask);
        int subtaskCountAfter = manager.getAllSubtasks().size();

        assertEquals(subtaskCountBefore, subtaskCountAfter,
                "Эпик не должен быть добавлен в качестве своей подзадачи");
    }

    @Test
    void shouldNotCreateSubtaskWithItsOwnIdAsEpic() {
        Subtask subtask = new Subtask("Invalid", "Subtask", 999);
        subtask.setId(999); // Устанавливаем ID = epicId

        int subtaskCountBefore = manager.getAllSubtasks().size();
        manager.createSubtask(subtask);
        int subtaskCountAfter = manager.getAllSubtasks().size();

        assertEquals(subtaskCountBefore, subtaskCountAfter,
                "Подзадача не должна быть своим собственным эпиком");
    }
}