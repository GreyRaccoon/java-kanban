package com.yandex.kanban;

public class Main {
    public static void main(String[] args) {
        TaskManager manager = new TaskManager();

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

        // Вывод всех объектов
        System.out.println("=== Исходные данные ===");
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
    }
}