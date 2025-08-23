package com.yandex.kanban.service;

import com.yandex.kanban.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager {
    private static class Node {
        Task data;
        Node next;
        Node prev;

        Node(Node prev, Task data, Node next) {
            this.prev = prev;
            this.data = data;
            this.next = next;
        }
    }

    private final Map<Integer, Node> historyMap = new HashMap<>();
    private Node head;
    private Node tail;

    @Override
    public void add(Task task) {
        if (task == null) return;
        int id = task.getId();

        // Удаляем предыдущий просмотр задачи
        remove(id);

        // Создаем копию задачи для истории
        Task taskCopy = createTaskCopy(task);

        // Добавляем задачу в конец списка
        linkLast(taskCopy);

        // Сохраняем узел в мапу
        historyMap.put(id, tail);
    }

    @Override
    public void remove(int id) {
        Node node = historyMap.remove(id);
        if (node != null) {
            removeNode(node);
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node current = head;
        while (current != null) {
            tasks.add(current.data);
            current = current.next;
        }
        return tasks;
    }

    private void linkLast(Task task) {
        final Node newNode = new Node(tail, task, null);
        if (tail == null) {
            head = newNode;
        } else {
            tail.next = newNode;
        }
        tail = newNode;
    }

    private void removeNode(Node node) {
        if (node == null) return;

        if (node == head) {
            head = node.next;
            if (head != null) {
                head.prev = null;
            }
        } else {
            node.prev.next = node.next;
        }

        if (node == tail) {
            tail = node.prev;
            if (tail != null) {
                tail.next = null;
            }
        } else {
            node.next.prev = node.prev;
        }
    }

    private Task createTaskCopy(Task original) {
        Task copy = new Task(original.getTitle(), original.getDescription());
        copy.setId(original.getId());
        copy.setStatus(original.getStatus());
        return copy;
    }
}