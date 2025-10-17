package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// panel cho countdown, stopwatch, pomodoro
public class TimerPanel extends JPanel {
    private JLabel lblTime = new JLabel("00:00:00");
    private Timer swingTimer;
    private long startMillis;
    private boolean running = false;

    public TimerPanel() {
        setLayout(new BorderLayout());
        lblTime.setFont(new Font("Monospaced", Font.BOLD, 24));
        lblTime.setHorizontalAlignment(SwingConstants.CENTER);

        JButton btnStartStopwatch = new JButton("Start/Stop StopWatch");
        JButton btnReset = new JButton("Reset");
        JButton btnPomodoro = new JButton("Pomodoro 25/5");

        btnStartStopwatch.addActionListener(e -> toggleStopwatch());
        btnReset.addActionListener(e -> resetStopwatch());
        btnPomodoro.addActionListener(e -> startPomodoro());

        JPanel p = new JPanel();
        p.add(btnStartStopwatch); p.add(btnReset); p.add(btnPomodoro);

        add(lblTime, BorderLayout.CENTER);
        add(p, BorderLayout.SOUTH);

        swingTimer = new Timer(1000, e -> updateLabel());
    }

    private void toggleStopwatch() {
        if (!running) {
            startMillis = System.currentTimeMillis();
            running = true;
            swingTimer.start();
        } else {
            running = false;
            swingTimer.stop();
        }
    }

    private void resetStopwatch() {
        running = false;
        swingTimer.stop();
        lblTime.setText("00:00:00");
    }

    private void updateLabel() {
        long elapsed = System.currentTimeMillis() - startMillis;
        long s = elapsed / 1000;
        long hh = s / 3600;
        long mm = (s % 3600) / 60;
        long ss = s % 60;
        lblTime.setText(String.format("%02d:%02d:%02d", hh, mm, ss));
    }

    private void startPomodoro() {
        // Pomodoro: 25 phut lam viec, 5 phut nghi -> thong bao dua tren Swing Timer
        JOptionPane.showMessageDialog(this, "Bat dau Pomodoro: 25 phut lam viec");
        Timer pomodoro = new Timer(25 * 60 * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer)e.getSource()).stop();
                JOptionPane.showMessageDialog(TimerPanel.this, "Hoan tat 25 phut. Bat dau nghi 5 phut");
                Timer shortBreak = new Timer(5 * 60 * 1000, ev -> {
                    ((Timer)ev.getSource()).stop();
                    JOptionPane.showMessageDialog(TimerPanel.this, "Ket thuc nghi. Bat dau mot Pomodoro khac neu can");
                });
                shortBreak.setRepeats(false);
                shortBreak.start();
            }
        });
        pomodoro.setRepeats(false);
        pomodoro.start();
    }
}
