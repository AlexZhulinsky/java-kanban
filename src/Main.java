public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);

        TaskManager.saveTask(task1);
        TaskManager.saveTask(task2);

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1", Status.NEW);
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW);

        TaskManager.saveEpic(epic1);
        TaskManager.saveSubtask(subtask1);
        TaskManager.saveSubtask(subtask2);
        TaskManager.setSubtasksByEpic(epic1, subtask1);
        TaskManager.setSubtasksByEpic(epic1, subtask2);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2", Status.NEW);
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW);

        TaskManager.saveEpic(epic2);
        TaskManager.saveSubtask(subtask3);
        TaskManager.setSubtasksByEpic(epic2, subtask3);

        System.out.println(TaskManager.getTasksByType("Задачи"));
        System.out.println(TaskManager.getTasksByType("Эпики"));
        System.out.println(TaskManager.getTasksByType("Подзадачи"));

        System.out.println("Создали трекер задач");

        TaskManager.updateTask(task1, "Задача 1", "Описание 1", Status.IN_PROGRESS);
        TaskManager.updateTask(task2, "Задача 2", "Описание 2", Status.IN_PROGRESS);

        TaskManager.updateSubtask(subtask1, "Подзадача 1", "Описание 1", Status.IN_PROGRESS);
        TaskManager.updateSubtask(subtask2, "Подзадача 2", "Описание 2", Status.DONE);
        TaskManager.updateSubtask(subtask3, "Подзадача 3", "Описание 3", Status.DONE);

        System.out.println(TaskManager.getTasksByType("Задачи"));
        System.out.println(TaskManager.getTasksByType("Эпики"));
        System.out.println(TaskManager.getTasksByType("Подзадачи"));

        System.out.println("Обновили трекер задач");

        TaskManager.deleteById(task1.getId());
        TaskManager.deleteById(epic2.getId());
        TaskManager.deleteById(subtask1.getId());

        System.out.println(TaskManager.getTasksByType("Задачи"));
        System.out.println(TaskManager.getTasksByType("Эпики"));
        System.out.println(TaskManager.getTasksByType("Подзадачи"));

        System.out.println("Удалили задачи");
    }
}