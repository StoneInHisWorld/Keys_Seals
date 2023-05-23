package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActBtnCellRenderer implements TableCellRenderer {

    public final Font cellFont;

    /**
     * 表格单元格渲染器。将表格渲染为标签，提示用户点击。
     * @param textSize 文本大小
     * @throws Exception 字体参数异常
     */
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



