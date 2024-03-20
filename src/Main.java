public class Main {
    public static void main(String[] args) {

        TaskManager taskManager = new TaskManager();
        Task task1 = new Task("Задача 1", "Описание задачи 1", Status.NEW);
        Task task2 = new Task("Задача 2", "Описание задачи 2", Status.NEW);

        taskManager.saveTask(task1);
        taskManager.saveTask(task2);

        System.out.println(taskManager.getTasks());
        System.out.println();

        Epic epic1 = new Epic("Эпик 1", "Описание эпика 1");
        Subtask subtask1 = new Subtask("Подзадача 1", "Описание подзадачи 1", Status.NEW);
        Subtask subtask2 = new Subtask("Подзадача 2", "Описание подзадачи 2", Status.NEW);

        Epic epic2 = new Epic("Эпик 2", "Описание эпика 2");
        Subtask subtask3 = new Subtask("Подзадача 3", "Описание подзадачи 3", Status.NEW);

        taskManager.saveEpic(epic1);
        taskManager.saveEpic(epic2);
        subtask1.setEpicIdForThisSubtask(epic1.getId());
        subtask2.setEpicIdForThisSubtask(epic1.getId());
        subtask3.setEpicIdForThisSubtask(epic2.getId());

        taskManager.saveSubtask(subtask1);
        taskManager.saveSubtask(subtask2);
        taskManager.saveSubtask(subtask3);

        System.out.println(taskManager.getEpics());
        System.out.println();

        subtask1.setStatus(Status.DONE);
        subtask2.setStatus(Status.NEW);
        subtask3.setStatus(Status.IN_PROGRESS);

        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask2);
        taskManager.updateSubtask(subtask3);

        System.out.println(taskManager.getEpics());
        System.out.println();

        taskManager.deleteSubtasks();
        System.out.println(taskManager.getSubtasks());
        System.out.println(taskManager.getEpics());
    }
}
