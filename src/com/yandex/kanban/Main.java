package com.yandex.kanban;

import com.yandex.kanban.service.Managers;
import com.yandex.kanban.service.TaskManager;
import com.yandex.kanban.service.Status;
import com.yandex.kanban.model.*;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = Managers.getDefault();

        // Создание задач
        Task task1 = new Task("Помыть посуду", "Помыть посуду после ужина");
        Task task2 = new Task("Сделать зарядку", "Утренняя зарядка 15 минут");
        manager.createTask(task1);
        manager.createTask(task2);

        // Создание эпиков с подзадачами
        Epic epic1 = new Epic("Подготовка к поездке", "Собрать вещи для отпуска");
        manager.createEpic(epic1);

        Subtask subtask1 = new Subtask("Купить билеты", "Забронировать авиабилеты", epic1.getId());
        Subtask subtask2 = new Subtask("Собрать чемодан", "Упаковать одежду и косметику", epic1.getId());
        manager.createSubtask(subtask1);
        manager.createSubtask(subtask2);

        Epic epic2 = new Epic("Ремонт в комнате", "Обновить интерьер");
        manager.createEpic(epic2);

        Subtask subtask3 = new Subtask("Покрасить стены", "Выбрать цвет краски", epic2.getId());
        manager.createSubtask(subtask3);

        // Просмотр задач для заполнения истории
        System.out.println("Просмотр задач для истории...");
        manager.getTaskById(1);
        manager.getTaskById(2);
        manager.getEpicById(3);
        manager.getSubtaskById(4);
        manager.getSubtaskById(5);
        manager.getEpicById(6);
        manager.getSubtaskById(7);

        // Вывод всех объектов
        System.out.println("\n=== Исходные данные ===");
        printAllTasks(manager);

        // Изменение статусов
        task1.setStatus(Status.DONE);
        manager.updateTask(task1);

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.IN_PROGRESS);
        manager.updateSubtask(subtask1);
        manager.updateSubtask(subtask2);

        subtask3.setStatus(Status.DONE);
        manager.updateSubtask(subtask3);

        // Просмотр измененных задач
        System.out.println("\nПросмотр измененных задач...");
        manager.getTaskById(1);
        manager.getEpicById(3);
        manager.getEpicById(6);

        // Вывод после изменения статусов
        System.out.println("\n=== После изменения статусов ===");
        printAllTasks(manager);

        // Удаление объектов
        manager.deleteTaskById(task1.getId());
        manager.deleteEpicById(epic1.getId());

        // Финальный вывод
        System.out.println("\n=== После удаления ===");
        printAllTasks(manager);
    }

    private static void printAllTasks(TaskManager manager) {
        System.out.println("Задачи: " + manager.getAllTasks());
        System.out.println("Эпики: " + manager.getAllEpics());
        System.out.println("Подзадачи: " + manager.getAllSubtasks());
        System.out.println("История: " + manager.getHistory());
    }
}