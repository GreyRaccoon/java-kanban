package com.yandex.kanban.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    void tasksWithSameIdShouldBeEqual() {
        Task task1 = new Task("Task 1", "Description");
        task1.setId(1);

        Task task2 = new Task("Task 2", "Different Description");
        task2.setId(1);

        assertEquals(task1, task2, "Задачи с одинаковым ID должны быть равны");
    }

    @Test
    void subtasksWithSameIdShouldBeEqual() {
        Subtask subtask1 = new Subtask("Sub 1", "Desc", 1);
        subtask1.setId(2);

        Subtask subtask2 = new Subtask("Sub 2", "Different", 3);
        subtask2.setId(2);

        assertEquals(subtask1, subtask2, "Подзадачи с одинаковым ID должны быть равны");
    }

    @Test
    void epicsWithSameIdShouldBeEqual() {
        Epic epic1 = new Epic("Epic 1", "Desc");
        epic1.setId(3);

        Epic epic2 = new Epic("Epic 2", "Different");
        epic2.setId(3);

        assertEquals(epic1, epic2, "Эпики с одинаковым ID должны быть равны");
    }
}