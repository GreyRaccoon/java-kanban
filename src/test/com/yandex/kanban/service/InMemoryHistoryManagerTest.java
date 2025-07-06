package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final HistoryManager history = new InMemoryHistoryManager();

    @Test
    void shouldPreserveTaskVersionInHistory() {
        Task task = new Task("Task", "Description");
        task.setId(1);

        history.add(task);

        task.setTitle("Updated Title");
        task.setStatus(Status.DONE);

        history.add(task);

        ArrayList<Task> historyArrayList = history.getHistory();
        assertEquals(2, historyArrayList.size(), "История должна содержать 2 записи");

        Task firstVersion = historyArrayList.get(0);
        Task secondVersion = historyArrayList.get(1);

        assertEquals("Task", firstVersion.getTitle(), "Название первой версии должно совпадать");
        assertEquals(Status.NEW, firstVersion.getStatus(), "Статус первой версии должен совпадать");
        assertEquals("Updated Title", secondVersion.getTitle(), "Название второй версии должно совпадать");
        assertEquals(Status.DONE, secondVersion.getStatus(), "Статус второй версии должен совпадать");
    }
}