package view;

import controller.ProjectController;
import controller.TaskController;
import model.Project;
import model.Task;
import java.awt.Color;
import java.awt.Font;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import util.TimeUtils;

public class FrmMain extends javax.swing.JFrame {

    ProjectController projectController;
    TaskController taskController;
    DefaultListModel listModel;

    public FrmMain() {
        initComponents();
        initControllers();
        initComponentsModel();
        decorateJList();
    }

    private void initControllers() {
        projectController = new ProjectController();
        taskController = new TaskController();
    }

    private void initComponentsModel() {
        listModel = new DefaultListModel();
        listProjects.setModel(listModel);
        loadProjects();
    }

    public void decorateJList() {
        // Nâng cấp: Tùy chỉnh danh sách Project
        listProjects.setFont(new Font("Segoe UI", Font.BOLD, 14));
        listProjects.setBackground(new Color(245, 245, 245)); // Nền nhạt
        listProjects.setFixedCellHeight(30);
        listProjects.setSelectionBackground(new Color(178, 190, 195)); // Màu khi chọn

        // Nâng cấp: Tùy chỉnh Header và Dòng của bảng Task
        jTableTasks.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        jTableTasks.getTableHeader().setBackground(new Color(65, 131, 215)); // Màu header xanh da trời
        jTableTasks.getTableHeader().setForeground(Color.WHITE);
        jTableTasks.setRowHeight(25);
        jTableTasks.setSelectionBackground(new Color(178, 190, 195));

        // Cài đặt Render cho bảng Task để thay đổi màu sắc dòng
        TaskTableModel model = new TaskTableModel();
        jTableTasks.setModel(model);
        jTableTasks.setDefaultRenderer(Date.class, new DeadlineRenderer());
        jTableTasks.setDefaultRenderer(Boolean.class, new DoneRenderer());
    }

    // --- KHU VỰC CỦA CÁC PHƯƠNG THỨC XỬ LÝ DỮ LIỆU ---

    private void loadProjects() {
        // [Giữ nguyên logic loadProjects cũ của bạn]
        List<Project> projects = projectController.getAll();
        listModel.clear();

        for (int i = 0; i < projects.size(); i++) {
            Project project = projects.get(i);
            listModel.addElement(project);
        }

        if (!projects.isEmpty()) {
            listProjects.setSelectedIndex(0);
            loadTasks(projects.get(0).getId());
        }
    }

    private void loadTasks(int id) {
        // [Giữ nguyên logic loadTasks cũ của bạn]
        List<Task> tasks = taskController.getAll(id);
        TaskTableModel model = (TaskTableModel) jTableTasks.getModel();
        model.setTasks(tasks);
    }

    private void jLabelProjectsAddMouseClicked(java.awt.event.MouseEvent evt) {
        FrmProjectDialog projectDialog = new FrmProjectDialog(this, true);
        projectDialog.setVisible(true);
        projectDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadProjects();
            }
        });
    }

    private void listProjectsMouseClicked(java.awt.event.MouseEvent evt) {
        int projectIndex = listProjects.getSelectedIndex();
        Project project = (Project) listModel.get(projectIndex);
        loadTasks(project.getId());
    }

    private void jLabelTasksAddMouseClicked(java.awt.event.MouseEvent evt) {
        int projectIndex = listProjects.getSelectedIndex();
        if (projectIndex == -1) {
            JOptionPane.showMessageDialog(rootPane,
                    "Vui lòng chọn một dự án để thêm Task!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Project project = (Project) listModel.get(projectIndex);
        FrmTaskDialog taskDialog = new FrmTaskDialog(this, true);
        taskDialog.setProject(project);
        taskDialog.setVisible(true);
        taskDialog.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadTasks(project.getId());
            }
        });
    }

    private void jTableTasksMouseClicked(java.awt.event.MouseEvent evt) {
        int rowIndex = jTableTasks.rowAtPoint(evt.getPoint());
        int columnIndex = jTableTasks.columnAtPoint(evt.getPoint());
        TaskTableModel model = (TaskTableModel) jTableTasks.getModel();
        Task task = model.getTasks().get(rowIndex);

        switch (columnIndex) {
            case 3: // Cột Deadline - không làm gì
                break;
            case 4: // Cột Edit
                FrmTaskDialog taskDialog = new FrmTaskDialog(this, true);
                taskDialog.setTask(task);
                taskDialog.setProject((Project) listModel.get(listProjects.getSelectedIndex()));
                taskDialog.setVisible(true);
                taskDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        loadTasks(task.getProject_id());
                    }
                });
                break;
            case 5: // Cột Done
                task.setDone(!task.isDone());
                taskController.update(task);
                loadTasks(task.getProject_id());
                break;
            case 6: // Cột Delete
                if (JOptionPane.showConfirmDialog(rootPane,
                        "Bạn có chắc muốn xóa Task này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    taskController.delete(task.getId());
                    loadTasks(task.getProject_id());
                }
                break;
        }
    }

    // --- KHU VỰC CỦA CODE initComponents() ĐƯỢC IDE TẠO ---
    // (Đoạn này đã được chỉnh sửa Font và Màu sắc)

    private void initComponents() {

        jPanelMain = new javax.swing.JPanel();
        jPanelHeader = new javax.swing.JPanel();
        jLabelHeaderTitle = new javax.swing.JLabel();
        jLabelHeaderSubtitle = new javax.swing.JLabel();
        jPanelProjects = new javax.swing.JPanel();
        jLabelProjectsTitle = new javax.swing.JLabel();
        jLabelProjectsAdd = new javax.swing.JLabel();
        jPanelProjectsList = new javax.swing.JPanel();
        jScrollPaneProjects = new javax.swing.JScrollPane();
        listProjects = new javax.swing.JList<>();
        jPanelTasks = new javax.swing.JPanel();
        jLabelTasksTitle = new javax.swing.JLabel();
        jLabelTasksAdd = new javax.swing.JLabel();
        jPanelTasksList = new javax.swing.JPanel();
        jScrollPaneTasks = new javax.swing.JScrollPane();
        jTableTasks = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Task Manager App - UI Nâng Cấp");
        setMinimumSize(new java.awt.Dimension(800, 600));

        jPanelMain.setBackground(new java.awt.Color(255, 255, 255));

        // Nâng cấp: Header Panel
        jPanelHeader.setBackground(new java.awt.Color(34, 47, 62)); // Xanh đậm

        jLabelHeaderTitle.setFont(new java.awt.Font("Segoe UI", 1, 30));
        jLabelHeaderTitle.setForeground(new java.awt.Color(255, 255, 255));
        jLabelHeaderTitle.setText("TASK MANAGER");

        jLabelHeaderSubtitle.setFont(new java.awt.Font("Segoe UI", 0, 14));
        jLabelHeaderSubtitle.setForeground(new java.awt.Color(189, 195, 199));
        jLabelHeaderSubtitle.setText("Quản lý mọi thứ không bao giờ dễ dàng đến thế");

        javax.swing.GroupLayout jPanelHeaderLayout = new javax.swing.GroupLayout(jPanelHeader);
        jPanelHeader.setLayout(jPanelHeaderLayout);
        jPanelHeaderLayout.setHorizontalGroup(
                jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelHeaderLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelHeaderSubtitle)
                                        .addComponent(jLabelHeaderTitle))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelHeaderLayout.setVerticalGroup(
                jPanelHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelHeaderLayout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabelHeaderTitle)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelHeaderSubtitle)
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        // Nâng cấp: Projects Panel
        jPanelProjects.setBackground(new java.awt.Color(255, 255, 255));
        jPanelProjects.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelProjectsTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabelProjectsTitle.setForeground(new java.awt.Color(65, 131, 215)); // Xanh da trời
        jLabelProjectsTitle.setText("Dự Án");

        jLabelProjectsAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // Icon ADD
        jLabelProjectsAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelProjectsAddMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelProjectsLayout = new javax.swing.GroupLayout(jPanelProjects);
        jPanelProjects.setLayout(jPanelProjectsLayout);
        jPanelProjectsLayout.setHorizontalGroup(
                jPanelProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelProjectsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelProjectsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelProjectsAdd)
                                .addContainerGap())
        );
        jPanelProjectsLayout.setVerticalGroup(
                jPanelProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelProjectsLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelProjectsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelProjectsTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelProjectsAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        // Nâng cấp: Tasks Panel
        jPanelTasks.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTasks.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabelTasksTitle.setFont(new java.awt.Font("Segoe UI", 1, 16));
        jLabelTasksTitle.setForeground(new java.awt.Color(65, 131, 215)); // Xanh da trời
        jLabelTasksTitle.setText("Công Việc");

        jLabelTasksAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/add.png"))); // Icon ADD
        jLabelTasksAdd.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelTasksAddMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanelTasksLayout = new javax.swing.GroupLayout(jPanelTasks);
        jPanelTasks.setLayout(jPanelTasksLayout);
        jPanelTasksLayout.setHorizontalGroup(
                jPanelTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTasksLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabelTasksTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabelTasksAdd)
                                .addContainerGap())
        );
        jPanelTasksLayout.setVerticalGroup(
                jPanelTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelTasksLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelTasksLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelTasksTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabelTasksAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        // Nâng cấp: Projects List
        jPanelProjectsList.setBackground(new java.awt.Color(255, 255, 255));
        jPanelProjectsList.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listProjects.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listProjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listProjectsMouseClicked(evt);
            }
        });
        jScrollPaneProjects.setViewportView(listProjects);

        javax.swing.GroupLayout jPanelProjectsListLayout = new javax.swing.GroupLayout(jPanelProjectsList);
        jPanelProjectsList.setLayout(jPanelProjectsListLayout);
        jPanelProjectsListLayout.setHorizontalGroup(
                jPanelProjectsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPaneProjects, javax.swing.GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
        );
        jPanelProjectsListLayout.setVerticalGroup(
                jPanelProjectsListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPaneProjects)
        );

        // Nâng cấp: Tasks List (Table)
        jPanelTasksList.setBackground(new java.awt.Color(255, 255, 255));
        jPanelTasksList.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jTableTasks.setModel(new TaskTableModel());
        jTableTasks.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableTasks.setShowGrid(true);
        jTableTasks.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableTasksMouseClicked(evt);
            }
        });
        jScrollPaneTasks.setViewportView(jTableTasks);

        javax.swing.GroupLayout jPanelTasksListLayout = new javax.swing.GroupLayout(jPanelTasksList);
        jPanelTasksList.setLayout(jPanelTasksListLayout);
        jPanelTasksListLayout.setHorizontalGroup(
                jPanelTasksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPaneTasks, javax.swing.GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
        );
        jPanelTasksListLayout.setVerticalGroup(
                jPanelTasksListLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPaneTasks, javax.swing.GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
        );

        // Layout chung của Main Panel
        javax.swing.GroupLayout jPanelMainLayout = new javax.swing.GroupLayout(jPanelMain);
        jPanelMain.setLayout(jPanelMainLayout);
        jPanelMainLayout.setHorizontalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelHeader, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanelProjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanelProjectsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanelTasks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanelTasksList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanelMainLayout.setVerticalGroup(
                jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanelMainLayout.createSequentialGroup()
                                .addComponent(jPanelHeader, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jPanelTasks, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanelProjects, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanelMainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanelProjectsList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jPanelTasksList, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanelMain, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }
}
// VÀ HÃY ĐẢM BẢO CÁC LỚP HỖ TRỢ TaskTableModel, DeadlineRenderer, DoneRenderer CŨ CỦA BẠN VẪN ĐƯỢC GIỮ NGUYÊN