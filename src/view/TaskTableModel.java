package view;

import model.Task;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public class TaskTableModel extends AbstractTableModel {

    private String[] columns = {"Tên", "Mô Tả", "Ghi Chú", "Deadline", "Sửa", "Hoàn thành", "Xóa"};
    private List<Task> tasks = new ArrayList<>();
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public TaskTableModel() {
    }

    @Override
    public int getRowCount() {
        return tasks.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columns[columnIndex];
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5; // Chỉ cho phép chỉnh sửa cột "Hoàn thành"
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (tasks.isEmpty()) {
            return Object.class;
        }

        switch (columnIndex) {
            case 5: // Cột Hoàn thành
                return Boolean.class;
            default:
                return String.class;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return tasks.get(rowIndex).getName();
            case 1:
                return tasks.get(rowIndex).getDescription();
            case 2:
                return tasks.get(rowIndex).getNotes();
            case 3:
                return dateFormat.format(tasks.get(rowIndex).getDeadline());
            case 4: // Cột Sửa (Edit)
                return ""; // Sẽ hiển thị icon
            case 5: // Cột Hoàn thành (Done)
                return tasks.get(rowIndex).isDone();
            case 6: // Cột Xóa (Delete)
                return ""; // Sẽ hiển thị icon
            default:
                return "N/A";
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // Cần thực hiện logic update nếu đây là ô chỉnh sửa được (chỉ cột 5)
        if (columnIndex == 5) {
            Task task = tasks.get(rowIndex);
            task.setDone((Boolean) aValue);
            // Lưu ý: Việc cập nhật DB sẽ được xử lý trong FrmMain, đây chỉ là Model
        }
    }

    public String[] getColumns() {
        return columns;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        fireTableDataChanged(); // Báo cho JTable rằng dữ liệu đã thay đổi
    }
}