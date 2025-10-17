package view;

import java.awt.Color;
import java.awt.Font;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimerPanel extends JPanel {

    private JLabel timeLabel;
    private Timer timer;
    private long startTime;
    private long totalTime;

    public TimerPanel() {
        initComponents();
        decoratePanel();
    }

    private void initComponents() {
        timeLabel = new JLabel("00:00:00");
        this.add(timeLabel);
    }

    private void decoratePanel() {
        // Nâng cấp: Thiết kế Panel Timer
        this.setBackground(new Color(236, 240, 241)); // Nền màu xám nhạt
        timeLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Font rõ ràng, cỡ lớn
        timeLabel.setForeground(new Color(39, 174, 96)); // Màu xanh lá cây nổi bật
    }

    // Giữ nguyên các phương thức updateTime, startTimer, stopTimer
    public void startTimer() {
        // [Logic startTimer]
        if (timer != null) {
            timer.cancel();
        }
        startTime = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTime();
            }
        }, 0, 1000); // Cập nhật mỗi giây
    }

    public void stopTimer() {
        // [Logic stopTimer]
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        // Có thể lưu totalTime vào DB tại đây nếu cần
    }

    private void updateTime() {
        // [Logic updateTime]
        if (timer != null) {
            totalTime = (System.currentTimeMillis() - startTime) / 1000;
            long hours = totalTime / 3600;
            long minutes = (totalTime % 3600) / 60;
            long seconds = totalTime % 60;
            timeLabel.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
        }
    }
}