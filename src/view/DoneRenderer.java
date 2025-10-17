package view;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DoneRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(CENTER);

        // Đảm bảo background khớp với DeadlineRenderer
        Color defaultBg = Color.WHITE;
        TaskTableModel taskModel = (TaskTableModel) table.getModel();

        // Cần đảm bảo logic tô màu khớp với DeadlineRenderer để không bị lệch màu
        // Dùng DeadlineRenderer để lấy màu nền chính xác
        DeadlineRenderer deadlineRenderer = new DeadlineRenderer();
        Component colorComponent = deadlineRenderer.getTableCellRendererComponent(table, null, isSelected, hasFocus, row, 3); // Lấy màu từ cột Deadline

        label.setBackground(colorComponent.getBackground());
        label.setForeground(colorComponent.getForeground());


        if (value instanceof Boolean) {
            Boolean done = (Boolean) value;

            if (column == 5) { // Cột Done
                if (done.booleanValue() == true) {
                    label.setIcon(new ImageIcon(getClass().getResource("/check.png")));
                    label.setText("");
                } else {
                    label.setIcon(new ImageIcon(getClass().getResource("/close.png")));
                    label.setText("");
                }
            } else if (column == 4) { // Cột Edit
                label.setIcon(new ImageIcon(getClass().getResource("/edit.png")));
                label.setText("");
            } else if (column == 6) { // Cột Delete
                label.setIcon(new ImageIcon(getClass().getResource("/delete.png")));
                label.setText("");
            } else {
                label.setIcon(null);
            }
        }

        if (isSelected) {
            label.setBackground(new Color(178, 190, 195));
            label.setForeground(Color.BLACK);
        }


        return label;
    }
}