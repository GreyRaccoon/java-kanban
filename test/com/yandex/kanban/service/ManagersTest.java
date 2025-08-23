package com.yandex.kanban.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    void shouldReturnInitializedTaskManager() {
        TaskManager manager = Managers.getDefault();
        assertNotNull(manager, "Менеджер задач должен быть проинициализирован");
        assertTrue(manager.getAllTasks().isEmpty(), "Новый менеджер должен быть пустым");
    }

    @Test
    void shouldReturnInitializedHistoryManager() {
        HistoryManager historyManager = Managers.getDefaultHistory();
        assertNotNull(historyManager, "Менеджер истории должен быть проинициализирован");
        assertTrue(historyManager.getHistory().isEmpty(), "Новая история должна быть пустой");
    }
}