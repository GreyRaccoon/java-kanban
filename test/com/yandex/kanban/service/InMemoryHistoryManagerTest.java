package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private final HistoryManager history = new InMemoryHistoryManager();

    @Test
    void shouldRemoveNodeFromAnyPosition() {
        Task task1 = new Task("Task1", "Desc");
        task1.setId(1);
        Task task2 = new Task("Task2", "Desc");
        task2.setId(2);
        Task task3 = new Task("Task3", "Desc");
        task3.setId(3);

        history.add(task1);
        history.add(task2);
        history.add(task3);

        // Удаление из середины
        history.remove(2);
        assertEquals(2, history.getHistory().size());
        assertNull(findTaskById(history.getHistory(), 2));

        // Удаление из начала
        history.remove(1);
        assertEquals(1, history.getHistory().size());
        assertNull(findTaskById(history.getHistory(), 1));

        // Удаление из конца
        history.remove(3);
        assertTrue(history.getHistory().isEmpty());
    }

    @Test
    void shouldKeepLastVersionOnly() {
        Task task = new Task("Task", "Desc");
        task.setId(1);

        history.add(task);
        task.setTitle("Updated");
        task.setStatus(Status.DONE);
        history.add(task); // Дублирование

        ArrayList<Task> historyList = history.getHistory();
        assertEquals(1, historyList.size());
        assertEquals("Updated", historyList.get(0).getTitle());
        assertEquals(Status.DONE, historyList.get(0).getStatus());
    }

    @Test
    void shouldPreserveOrder() {
        Task task1 = new Task("Task1", "Desc");
        task1.setId(1);
        Task task2 = new Task("Task2", "Desc");
        task2.setId(2);

        history.add(task1);
        history.add(task2);
        history.add(task1); // Повторное добавление

        ArrayList<Task> historyList = history.getHistory();
        assertEquals(2, historyList.size());
        assertEquals(task2, historyList.get(0));
        assertEquals(task1, historyList.get(1));
    }

    @Test
    void shouldIsolateHistoryFromExternalChanges() {
        Task task = new Task("Original", "Desc");
        task.setId(1);
        history.add(task);

        task.setTitle("Modified");
        Task historyTask = history.getHistory().get(0);

        assertEquals("Original", historyTask.getTitle());
    }

    private Task findTaskById(ArrayList<Task> tasks, int id) {
        return tasks.stream().filter(t -> t.getId() == id).findFirst().orElse(null);
    }

    @Test
    void shouldKeepOnlyLastVersionInHistory() {
        Task task = new Task("Task", "Desc");
        task.setId(1);

        history.add(task);
        task.setTitle("Updated");
        task.setStatus(Status.DONE);
        history.add(task); // Заменяет первую версию

        ArrayList<Task> historyList = history.getHistory();
        assertEquals(1, historyList.size());
        assertEquals("Updated", historyList.get(0).getTitle());
    }
}