package view;

import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import model.Task;
import util.TimeUtils;

public class DeadlineRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(CENTER);

        // Lấy Task từ TaskTableModel
        TaskTableModel taskModel = (TaskTableModel) table.getModel();
        Task task = taskModel.getTasks().get(row);

        // Nâng cấp: Tô màu dòng dựa trên trạng thái và Deadline
        if (task.getDeadline() != null && !task.isDone()) {
            Date now = new Date();
            // Nếu deadline đã qua (quá hạn)
            if (task.getDeadline().before(now)) {
                label.setBackground(new Color(255, 102, 102)); // Màu đỏ nhạt
                label.setForeground(Color.WHITE);
                // Nếu deadline còn 3 ngày trở lại
            } else if (TimeUtils.getDaysBetweenDates(now, task.getDeadline()) <= 3) {
                label.setBackground(new Color(255, 255, 153)); // Màu vàng nhạt
                label.setForeground(Color.BLACK);
            } else {
                // Mặc định (nền trắng, chữ đen)
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
        } else {
            // Mặc định (nền trắng, chữ đen)
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }

        if (task.isDone()) {
            // Màu xám nếu đã hoàn thành
            label.setBackground(new Color(204, 255, 204));
            label.setForeground(new Color(39, 174, 96));
        }

        // Đảm bảo màu sắc không thay đổi khi được chọn (isSelected)
        if (isSelected) {
            label.setBackground(new Color(178, 190, 195)); // Màu khi được chọn
            label.setForeground(Color.BLACK);
        }

        return label;
    }
}