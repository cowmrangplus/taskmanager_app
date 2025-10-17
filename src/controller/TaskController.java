package controller;

import dao.TaskDAO;
import dao.ProjectDAO;
import model.Task;
import model.Project;

import java.time.LocalDateTime;
import java.util.List;

// controller giua view va dao
public class TaskController {
    private TaskDAO taskDAO = new TaskDAO();
    private ProjectDAO projectDAO = new ProjectDAO();

    public List<Task> getAllTasks() { return taskDAO.getAll(); }
    public List<Project> getAllProjects() { return projectDAO.getAll(); }

    public boolean saveTask(Task t) {
        if (t.getId() == 0) return taskDAO.insert(t);
        else return taskDAO.update(t);
    }

    public boolean deleteTask(int id) { return taskDAO.delete(id); }

    public List<Task> getTasksBetween(LocalDateTime from, LocalDateTime to) {
        return taskDAO.getTasksByDate(from, to);
    }
}
