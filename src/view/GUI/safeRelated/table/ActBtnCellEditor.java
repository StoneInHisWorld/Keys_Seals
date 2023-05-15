package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.MouseListener.RetKeyBtnMouseListener;

import javax.swing.*;
import java.awt.*;

public class ActBtnCellEditor extends DefaultCellEditor {

    private static final long serialVersionUID = -6546334664166791132L;
    private final JButton[] actionBtns;

    public ActBtnCellEditor(Object[] actionBtns) {
        super(new JTextField());
        this.actionBtns = new JButton[actionBtns.length];
        for (int i = 0; i < this.actionBtns.length; i++) {
            this.actionBtns[i] = (JButton) actionBtns[i];
        }
        this.setClickCountToStart(0);
    }

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