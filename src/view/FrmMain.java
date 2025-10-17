package view;

import controller.TaskController;
import model.Task;
import model.Project;
import util.ReminderService;
import util.TimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

// Form chinh hien thi danh sach + calendar view co ban + timer panel
public class FrmMain extends JFrame {
    private TaskController controller = new TaskController();
    private ReminderService reminderService = new ReminderService(controller);

    private JTable tbl;
    private DefaultTableModel tblModel;
    private JComboBox<String> cbViewMode;

    public FrmMain() {
        setTitle("Task Manager - Simple");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initComponents();
        pack();
        setLocationRelativeTo(null);
        reminderService.start();
    }

    private void initComponents() {
        String[] cols = {"ID","Tieu de","Priority","Deadline","Project","Hoan thanh"};
        tblModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tbl = new JTable(tblModel);
        JScrollPane sp = new JScrollPane(tbl);

        JButton btnAdd = new JButton("Them");
        JButton btnEdit = new JButton("Sua");
        JButton btnDelete = new JButton("Xoa");
        JButton btnRefresh = new JButton("Refresh");
        JButton btnCalendarToday = new JButton("Xem Hom Nay");
        cbViewMode = new JComboBox<>(new String[]{"Danh sach", "Theo ngay", "Theo tuan", "Theo thang"});

        btnAdd.addActionListener(e -> {
            FrmTaskDialog d = new FrmTaskDialog(this, controller, null);
            d.setVisible(true);
            refreshTable();
        });

        btnEdit.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chon task de sua"); return; }
            int id = Integer.parseInt(tblModel.getValueAt(r,0).toString());
            List<Task> list = controller.getAllTasks();
            for (Task t : list) if (t.getId() == id) {
                FrmTaskDialog d = new FrmTaskDialog(this, controller, t);
                d.setVisible(true);
                refreshTable();
                break;
            }
        });

        btnDelete.addActionListener(e -> {
            int r = tbl.getSelectedRow();
            if (r == -1) { JOptionPane.showMessageDialog(this, "Chon task de xoa"); return; }
            int id = Integer.parseInt(tblModel.getValueAt(r,0).toString());
            if (JOptionPane.showConfirmDialog(this, "Ban muon xoa?") == JOptionPane.YES_OPTION) {
                controller.deleteTask(id);
                refreshTable();
            }
        });

        btnRefresh.addActionListener(e -> refreshTable());

        btnCalendarToday.addActionListener(e -> {
            cbViewMode.setSelectedItem("Theo ngay");
            showToday();
        });

        JPanel top = new JPanel();
        top.add(btnAdd); top.add(btnEdit); top.add(btnDelete); top.add(btnRefresh);
        top.add(cbViewMode); top.add(btnCalendarToday);

        TimerPanel timerPanel = new TimerPanel();

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, timerPanel);
        split.setResizeWeight(0.7);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(top, BorderLayout.NORTH);
        getContentPane().add(split, BorderLayout.CENTER);

        refreshTable();
    }

    private void refreshTable() {
        tblModel.setRowCount(0);
        List<Task> tasks = controller.getAllTasks();
        for (Task t : tasks) {
            tblModel.addRow(new Object[]{
                t.getId(),
                t.getTitle(),
                t.getPriority(),
                t.getDeadline() == null ? "" : TimeUtils.format(t.getDeadline()),
                t.getProject() == null ? "" : t.getProject().getName(),
                t.isCompleted() ? "Yes":"No"
            });
        }
    }

    private void showToday() {
        LocalDate today = LocalDate.now();
        LocalDateTime from = LocalDateTime.of(today, LocalTime.MIN);
        LocalDateTime to = LocalDateTime.of(today, LocalTime.MAX);
        List<Task> tasks = controller.getTasksBetween(from, to);
        tblModel.setRowCount(0);
        for (Task t : tasks) {
            tblModel.addRow(new Object[]{
                t.getId(),
                t.getTitle(),
                t.getPriority(),
                t.getDeadline() == null ? "" : TimeUtils.format(t.getDeadline()),
                t.getProject() == null ? "" : t.getProject().getName(),
                t.isCompleted() ? "Yes":"No"
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new FrmMain().setVisible(true);
        });
    }
}
