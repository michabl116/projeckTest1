package model;
import dao.TaskDao;
import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final TaskDao taskDao;
    private final List<Task> tasks;

    public TaskList(TaskDao taskDao) {
        this.taskDao = taskDao;
        this.tasks = new ArrayList<>(taskDao.findAll());

    }
    public void addTask(Task task) {
        taskDao.persist(task);
        tasks.add(task);
    }

    public void updateTask(Task task) {
        taskDao.update(task);
    }
    public void deleteTask(Task task) {
        taskDao.delete(task);
        tasks.remove(task);
    }

    public Task findTaskById(int id) {
        return taskDao.find(id);
    }
    public List<Task> getAllTasks() {

        return new ArrayList<>(tasks);
    }

}
