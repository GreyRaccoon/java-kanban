package com.yandex.kanban;

import com.yandex.kanban.model.Epic;
import com.yandex.kanban.model.Subtask;
import com.yandex.kanban.model.Task;
import com.yandex.kanban.service.Managers;
import com.yandex.kanban.service.TaskManager;

public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = Managers.getDefault();

        // Создаем задачи
        Task task1 = new Task("Task 1", "Description 1");
        Task task2 = new Task("Task 2", "Description 2");
        taskManager.createTask(task1);
        taskManager.createTask(task2);

        // Создаем эпики
        Epic epic1 = new Epic("Epic 1", "Description Epic 1");
        Epic epic2 = new Epic("Epic 2", "Description Epic 2");
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);

        // Создаем подзадачи для epic1
        Subtask subtask1 = new Subtask("Subtask 1", "Description 1", epic1.getId());
        Subtask subtask2 = new Subtask("Subtask 2", "Description 2", epic1.getId());
        Subtask subtask3 = new Subtask("Subtask 3", "Description 3", epic1.getId());
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask3);

        // Запрашиваем задачи в разном порядке
        System.out.println("Запрос task1, epic1, subtask1:");
        taskManager.getTaskById(task1.getId());
        taskManager.getEpicById(epic1.getId());
        taskManager.getSubtaskById(subtask1.getId());
        printHistory(taskManager);

        System.out.println("\nЗапрос subtask2, task2, epic2:");
        taskManager.getSubtaskById(subtask2.getId());
        taskManager.getTaskById(task2.getId());
        taskManager.getEpicById(epic2.getId());
        printHistory(taskManager);

        System.out.println("\nЗапрос task1 (повторно), subtask3:");
        taskManager.getTaskById(task1.getId());
        taskManager.getSubtaskById(subtask3.getId());
        printHistory(taskManager);

        // Удаляем задачу, которая есть в истории
        System.out.println("\nУдаляем task1:");
        taskManager.deleteTaskById(task1.getId());
        printHistory(taskManager);

        // Удаляем эпик с подзадачами
        System.out.println("\nУдаляем epic1:");
        taskManager.deleteEpicById(epic1.getId());
        printHistory(taskManager);
    }

    private static void printHistory(TaskManager manager) {
        System.out.println("История просмотров:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}