package view;

import controller.TaskController;
import model.Project;
import model.Task;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import util.TimeUtils;

public class FrmTaskDialog extends javax.swing.JDialog {

    TaskController controller;
    Project project;
    Task taskToEdit; // Biến mới để lưu Task cần chỉnh sửa

    public FrmTaskDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        controller = new TaskController();
        setLocationRelativeTo(parent);
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public void setTask(Task task) {
        this.taskToEdit = task;
        // Tải dữ liệu Task vào form khi chỉnh sửa
        if (task != null) {
            jLabelToolbarTitle.setText("Chỉnh Sửa Task");
            jTextFieldName.setText(task.getName());
            jTextAreaDescription.setText(task.getDescription());
            jFormattedTextFieldDeadline.setText(TimeUtils.dateToString(task.getDeadline()));
            jCheckBoxDone.setSelected(task.isDone());
        } else {
            jLabelToolbarTitle.setText("Tạo Task Mới");
        }
    }

    private void jLabelToolbarSaveMouseClicked(java.awt.event.MouseEvent evt) {
        try {
            if (jTextFieldName.getText().isEmpty() || jFormattedTextFieldDeadline.getText().isEmpty()) {
                JOptionPane.showMessageDialog(rootPane, "Tên Task và Deadline là bắt buộc!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Task task;
            if (taskToEdit == null) {
                // Tạo mới
                task = new Task();
                task.setProject_id(project.getId());
            } else {
                // Chỉnh sửa
                task = taskToEdit;
            }

            task.setName(jTextFieldName.getText());
            task.setDescription(jTextAreaDescription.getText());
            task.setNotes(jTextAreaNotes.getText());
            task.setDone(jCheckBoxDone.isSelected());

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date deadline = dateFormat.parse(jFormattedTextFieldDeadline.getText());
            task.setDeadline(deadline);

            if (taskToEdit == null) {
                controller.save(task);
            } else {
                controller.update(task);
            }

            JOptionPane.showMessageDialog(rootPane, "Task đã được lưu thành công!");
            this.dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Lỗi khi lưu Task", JOptionPane.ERROR_MESSAGE);
        }
    }

    // --- KHU VỰC CỦA CODE initComponents() ĐƯỢC IDE TẠO ---
    // (Đoạn này đã được chỉnh sửa Font và Màu sắc)

    private void initComponents() {

        jPanelToolbar = new javax.swing.JPanel();
        jLabelToolbarTitle = new javax.swing.JLabel();
        jLabelToolbarSave = new javax.swing.JLabel();
        jPanelTask = new javax.swing.JPanel();
        jLabelName = new javax.swing.JLabel();
        jTextFieldName = new javax.swing.JTextField();
        jLabelDescription = new javax.swing.JLabel();
        jScrollPaneDescription = new javax.swing.JScrollPane();
        jTextAreaDescription = new javax.swing.JTextArea();
        jLabelDeadline = new javax.swing.JLabel();
        jFormattedTextFieldDeadline = new javax.swing.JFormattedTextField();
        jLabelNotes = new javax.swing.JLabel();
        jScrollPaneNotes = new javax.swing.JScrollPane();
        jTextAreaNotes = new javax.swing.JTextArea();
        jCheckBoxDone = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // Nâng cấp: Toolbar
        jPanelToolbar.setBackground(new java.awt.Color(65, 131, 215)); // Xanh da trời

        jLabelToolbarTitle.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabelToolbarTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelToolbarTitle.setText("Tạo Task Mới");

        jLabelToolbarSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/check.png"))); // Icon SAVE
        jLabelToolbarSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelToolbarSaveMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelToolbarLayout = new javax.swing.GroupLayout(jPanelToolbar);
        jPanelToolbar.setLayout(jPanelToolbarLayout);
        jPanelToolbarLayout.setHorizontalGroup(
                jPanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelToolbarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelToolbarTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelToolbarSave)
                                .addContainerGap())
        );
        jPanelToolbarLayout.setVerticalGroup(
                jPanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelToolbarLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelToolbarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelToolbarSave, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelToolbarTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        // Nâng cấp: Panel Task
        jPanelTask.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTask.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelName.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelName.setText("Tên Task *");

        jTextFieldName.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabelDescription.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDescription.setText("Mô tả");

        jTextAreaDescription.setColumns(20);
        jTextAreaDescription.setRows(5);
        jTextAreaDescription.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))); // Viền nhẹ
        jScrollPaneDescription.setViewportView(jTextAreaDescription);

        jLabelDeadline.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelDeadline.setText("Deadline * (dd/MM/yyyy)");

        try {
            jFormattedTextFieldDeadline.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jFormattedTextFieldDeadline.setFont(new java.awt.Font("Segoe UI", 0, 14));

        jLabelNotes.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelNotes.setText("Ghi chú");

        jTextAreaNotes.setColumns(20);
        jTextAreaNotes.setRows(5);
        jTextAreaNotes.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204))); // Viền nhẹ
        jScrollPaneNotes.setViewportView(jTextAreaNotes);

        jCheckBoxDone.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jCheckBoxDone.setText("Hoàn thành");

        javax.swing.GroupLayout jPanelTaskLayout = new javax.swing.GroupLayout(jPanelTask);
        jPanelTask.setLayout(jPanelTaskLayout);
        jPanelTaskLayout.setHorizontalGroup(
                jPanelTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTaskLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldName)
                                        .addComponent(jScrollPaneDescription, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                                        .addComponent(jFormattedTextFieldDeadline)
                                        .addComponent(jScrollPaneNotes)
                                        .addGroup(jPanelTaskLayout.createSequentialGroup()
                                                .addGroup(jPanelTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabelName)
                                                        .addComponent(jLabelDescription)
                                                        .addComponent(jLabelDeadline)
                                                        .addComponent(jLabelNotes)
                                                        .addComponent(jCheckBoxDone))
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanelTaskLayout.setVerticalGroup(
                jPanelTaskLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTaskLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelDescription)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelDeadline)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jFormattedTextFieldDeadline, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabelNotes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPaneNotes, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCheckBoxDone)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelToolbar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jPanelTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanelToolbar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanelTask, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        pack();
    }
}