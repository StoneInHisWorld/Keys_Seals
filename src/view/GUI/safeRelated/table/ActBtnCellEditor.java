package view.GUI.safeRelated.table;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActBtnCellEditor extends DefaultCellEditor {
    private static final long serialVersionUID = -6546334664166791132L;
    private final String[] actions;

    public ActBtnCellEditor(String[] actions) {
        super(new JTextField());
        this.setClickCountToStart(1);
        this.actions = actions;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        JPanel actBtnPanel = new JPanel();
        for (String action : actions) {
            JButton btn = new JButton(action);
            btn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    JOptionPane.showMessageDialog(null, "clicked!");
                }
            });
            actBtnPanel.add(btn);
        }
//        JButton btn = new JButton("1");
//        btn.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JOptionPane.showMessageDialog(null, "clicked!");
//            }
//        });
//        JButton btn1 = new JButton("1");
//        btn1.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JOptionPane.showMessageDialog(null, "clicked!");
//            }
//        });
//        JButton btn2 = new JButton("1");
//        btn2.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                JOptionPane.showMessageDialog(null, "clicked!");
//            }
//        });
//        actBtnPanel.add(btn);
//        actBtnPanel.add(btn1);
//        actBtnPanel.add(btn2);
//        if (value.getClass() == String.class)
//            return new DefaultTableCellRenderer().
//                    getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        return actBtnPanel;
    }
}