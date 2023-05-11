package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActBtnCellRenderer implements TableCellRenderer {

    public final Font cellFont;

    public ActBtnCellRenderer(int textSize) throws Exception {
        this.cellFont = BasicMethods.getFont(Font.PLAIN, textSize);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = new JLabel("点击以进行进一步操作");
        label.setFont(this.cellFont);
        return label;
    }

}



