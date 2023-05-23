package view.GUI.safeRelated.table;

import javax.swing.*;
import java.awt.*;

public class ActBtnCellEditor extends DefaultCellEditor {

    private static final long serialVersionUID = -6546334664166791132L;
    private final JButton[] actionBtns;

    /**
     * 操作按钮单元格编辑器
     * @param actionBtns 直接嵌入单元格的操作按钮
     */
    public ActBtnCellEditor(Object[] actionBtns) {
        super(new JTextField());
        this.actionBtns = new JButton[actionBtns.length];
        for (int i = 0; i < this.actionBtns.length; i++) {
            this.actionBtns[i] = (JButton) actionBtns[i];
        }
        this.setClickCountToStart(0);
    }

    /**
     * 获取表格单元格编辑器组件。将所有按钮加入到匿名容器中。
     * @param table 表格对象
     * @param value 原单元格值
     * @param isSelected 是否被选中
     * @param row 行
     * @param column 列
     * @return 组件
     */
    @Override
    public Component getTableCellEditorComponent(JTable table,
                                                 Object value,
                                                 boolean isSelected,
                                                 int row,
                                                 int column) {
        JPanel actBtnPanel = new JPanel();
        for (JButton btn : actionBtns) {
            actBtnPanel.add(btn);
        }
        return actBtnPanel;
    }
}