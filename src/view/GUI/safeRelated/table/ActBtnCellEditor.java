package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActBtnCellEditor extends DefaultCellEditor {
    private static final long serialVersionUID = -6546334664166791132L;
    private final String[] actions;
    private final Font btnFont;

    public ActBtnCellEditor(String[] actions, int textSize) throws Exception {
        super(new JTextField());
        this.setClickCountToStart(0);
        this.actions = actions;
        this.btnFont = BasicMethods.getFont(Font.PLAIN, textSize);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JPanel actBtnPanel = new JPanel();
        for (String action : actions) {
            JButton btn = BasicMethods.getVisibleBtn(action, this.btnFont, BasicMethods.NORMAL,
                    new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            JOptionPane.showMessageDialog(null, "clicked!");
                        }
                    }
            );
            actBtnPanel.add(btn);
        }
        return actBtnPanel;
    }
}