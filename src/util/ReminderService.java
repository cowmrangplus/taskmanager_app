package util;

import controller.TaskController;
import model.Task;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

// service don gian kiem tra deadline va thong bao
public class ReminderService {
    private Timer timer;
    private TaskController controller;

    public ReminderService(TaskController controller) {
        this.controller = controller;
        // kiem tra moi 60s
        timer = new Timer(60 * 1000, e -> checkDeadlines());
    }

    public void start() { timer.start(); }
    public void stop() { timer.stop(); }

    private void checkDeadlines() {
        List<Task> tasks = controller.getAllTasks();
        LocalDateTime now = LocalDateTime.now();
        for (Task t : tasks) {
            if (t.getDeadline() != null && !t.isCompleted()) {
                long minutes = java.time.Duration.between(now, t.getDeadline()).toMinutes();
                if (minutes >= 0 && minutes <= 15) { // thong bao neu trong 15 phut
                    SwingUtilities.invokeLater(() ->
                        JOptionPane.showMessageDialog(null,
                            "Task sap den han: " + t.getTitle() + "\nDeadline: " + TimeUtils.format(t.getDeadline()),
                            "Reminder", JOptionPane.INFORMATION_MESSAGE)
                    );
                }
            }
        }
    }
}
