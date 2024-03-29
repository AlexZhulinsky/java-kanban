import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int countId;
    private final HashMap<Integer, Task> tasks;
    private final HashMap<Integer, Subtask> subtasks;
    private final HashMap<Integer, Epic> epics;

    public TaskManager() {
        tasks = new HashMap<>();
        subtasks = new HashMap<>();
        epics = new HashMap<>();
        countId = 0;
    }

    public void saveTask(Task task) {
        if (!tasks.containsValue(task)) {
            countId++;
            task.setId(countId);
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Такая задача уже создана");
        }
    }

    public void saveSubtask(Subtask subtask) {
        Epic epic = epics.get(subtask.getEpicIdForThisSubtask());
        if (epic == null) {
            return;
        }
        if (!subtasks.containsValue(subtask)) {
            countId++;
            subtask.setId(countId);
            epic.addSubtasksForEpic(subtask);
            changeEpicStatus(epic);
            subtasks.put(subtask.getId(), subtask);
        } else {
            System.out.println("Такая подзадача уже создана");
        }
    }

    public void saveEpic(Epic epic) {
        if (!epics.containsValue(epic)) {
            countId++;
            epic.setId(countId);
            epics.put(epic.getId(), epic);
        } else {
            System.out.println("Такой эпик уже создан");
        }
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Subtask> getSubtasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Subtask getSubtaskById(int id) {
        return subtasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void deleteById(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
        } else if (epics.containsKey(id)) {
            HashMap<Integer, Subtask> epicSubtasks = epics.get(id).getSubtasksForThisEpic();
            for (Integer subtasksId : epicSubtasks.keySet()) {
                subtasks.remove(subtasksId);
            }
            epics.remove(id);
        } else if (subtasks.containsKey(id)) {
            Subtask subtask = subtasks.get(id);
            Epic epic = epics.get(subtask.getEpicIdForThisSubtask());
            epic.deleteSubtaskForThisEpic(id);
            changeEpicStatus(epic);
            subtasks.remove(id);
        }
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }


    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            Subtask subtaskLink = subtasks.get(subtask.getId());
            if (subtaskLink.getEpicIdForThisSubtask() == subtask.getEpicIdForThisSubtask()) {
            subtasks.put(subtask.getId(), subtask);
            Epic epic = epics.get(subtask.getEpicIdForThisSubtask());
                if (epic != null) {
                    epic.getSubtasksForThisEpic().put(subtaskLink.getId(), subtaskLink);
                    changeEpicStatus(epic);
                }
            }
        }
    }

    public void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            Epic epicLink = epics.get(epic.getId());
            epicLink.setName(epic.getName());
            epicLink.setDescription(epic.getDescription());
        }
    }

    public void deleteTasks() {
        tasks.clear();
    }

    public void deleteSubtasks() {
        for (Epic epic : epics.values()) {
            epic.getSubtasksForThisEpic().clear();
            changeEpicStatus(epic);
        }
        subtasks.clear();
    }

    public void deleteEpics() {
        epics.clear();
        subtasks.clear();
    }

    public ArrayList<Subtask> getAllSubtasksByEpic(int id) {
        HashMap<Integer, Subtask> subtasksByEpic = epics.get(id).getSubtasksForThisEpic();
        return new ArrayList<>(subtasksByEpic.values());
    }

    private void changeEpicStatus(Epic epic) {
        if (epic.getSubtasksForThisEpic().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            int countNew = 0;
            int countDone = 0;
            HashMap<Integer, Subtask> forStatus = epic.getSubtasksForThisEpic();
            for (Subtask subtaskCopy : forStatus.values()) {
                if (subtaskCopy.getStatus() == Status.IN_PROGRESS) {
                    epic.setStatus(Status.IN_PROGRESS);
                    return;
                } else if (subtaskCopy.getStatus() == Status.NEW) {
                    countNew++;
                } else if (subtaskCopy.getStatus() == Status.DONE) {
                    countDone++;
                }
            }
            if (countNew == forStatus.size()) {
                epic.setStatus(Status.NEW);
            } else if (countDone == forStatus.size()) {
                epic.setStatus(Status.DONE);
            } else {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }
}
