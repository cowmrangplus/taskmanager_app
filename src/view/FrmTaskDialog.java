package view;

import model.Project;
import model.Task;
import controller.TaskController;
import util.TimeUtils;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;

// dialog them / sua task
public class FrmTaskDialog extends JDialog {
    private JTextField txtTitle = new JTextField(30);
    private JTextArea txtDesc = new JTextArea(5,30);
    private JComboBox<String> cbPriority = new JComboBox<>(new String[]{"CAO","TRUNG_BINH","THAP"});
    private JTextField txtDeadline = new JTextField(20); // yyyy-MM-dd HH:mm
    private JComboBox<Project> cbProject;
    private JCheckBox chkCompleted = new JCheckBox("Da hoan thanh");

    private Task task;
    private TaskController controller;

    public FrmTaskDialog(Frame owner, TaskController controller, Task task) {
        super(owner, true);
        this.controller = controller;
        this.task = task == null ? new Task() : task;
        setTitle(task == null ? "Them Task" : "Sua Task");
        init();
        pack();
        setLocationRelativeTo(owner);
    }

    private void init() {
        List<Project> projects = controller.getAllProjects();
        cbProject = new JComboBox<>();
        cbProject.addItem(null);
        for (Project p : projects) cbProject.addItem(p);

        if (task.getTitle() != null) {
            txtTitle.setText(task.getTitle());
            txtDesc.setText(task.getDescription());
            cbPriority.setSelectedItem(task.getPriority() == null ? "TRUNG_BINH" : task.getPriority());
            if (task.getDeadline() != null) txtDeadline.setText(TimeUtils.format(task.getDeadline()));
            if (task.getProject() != null) cbProject.setSelectedItem(task.getProject());
            chkCompleted.setSelected(task.isCompleted());
        }

        JPanel p = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4,4,4,4);
        c.gridx=0; c.gridy=0; c.anchor = GridBagConstraints.WEST; form.add(new JLabel("Tieu de:"), c);
        c.gridx=1; form.add(txtTitle, c);
        c.gridx=0; c.gridy++; form.add(new JLabel("Mota:"), c);
        c.gridx=1; form.add(new JScrollPane(txtDesc), c);
        c.gridx=0; c.gridy++; form.add(new JLabel("Priority:"), c);
        c.gridx=1; form.add(cbPriority, c);
        c.gridx=0; c.gridy++; form.add(new JLabel("Deadline (yyyy-MM-dd HH:mm):"), c);
        c.gridx=1; form.add(txtDeadline, c);
        c.gridx=0; c.gridy++; form.add(new JLabel("Project:"), c);
        c.gridx=1; form.add(cbProject, c);
        c.gridx=0; c.gridy++; form.add(new JLabel("Trang thai:"), c);
        c.gridx=1; form.add(chkCompleted, c);

        JPanel buttons = new JPanel();
        JButton btnSave = new JButton("Luu");
        JButton btnCancel = new JButton("Huy");
        btnSave.addActionListener(e -> onSave());
        btnCancel.addActionListener(e -> dispose());
        buttons.add(btnSave); buttons.add(btnCancel);

        p.add(form, BorderLayout.CENTER);
        p.add(buttons, BorderLayout.SOUTH);
        setContentPane(p);
    }

    private void onSave() {
        task.setTitle(txtTitle.getText().trim());
        task.setDescription(txtDesc.getText().trim());
        task.setPriority((String)cbPriority.getSelectedItem());
        String d = txtDeadline.getText().trim();
        if (!d.isEmpty()) {
            try {
                task.setDeadline(LocalDateTime.parse(d, TimeUtils.DTF));
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Deadline khong hop le. Dung dinh dang yyyy-MM-dd HH:mm");
                return;
            }
        } else task.setDeadline(null);
        Project p = (Project) cbProject.getSelectedItem();
        task.setProject(p);
        task.setCompleted(chkCompleted.isSelected());

        if (controller.saveTask(task)) {
            JOptionPane.showMessageDialog(this, "Luu thanh cong");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Luu that bai");
        }
    }
}
