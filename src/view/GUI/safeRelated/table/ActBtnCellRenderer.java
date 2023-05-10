package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class ActBtnCellRenderer implements TableCellRenderer {

    public final Font cellFont;

    public ActBtnCellRenderer() {
        this.cellFont = BasicMethods.getPlainFont(15);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//        JPanel actBtnPanel = new JPanel();
//        for (String action : actionList) {
////            o.toString();
//            JButton btn = new JButton(action);
//            btn.addMouseListener(new MouseAdapter() {
//                @Override
//                public void mouseClicked(MouseEvent e) {
//                    JOptionPane.showMessageDialog(null, "clicked",
//                            "程序错误", JOptionPane.ERROR_MESSAGE);
//                }
//            });
//            actBtnPanel.add(btn);
////                buttonList.add(new JButton(o.toString()));
//        }
//        try {
//            actionList = (Object[]) value;
////            List<JButton> buttonList = new LinkedList<>();
//
//        } catch (ClassCastException e) {

//        }
//        JButton btn = new JButton("1");
////        btn.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                JOptionPane.showMessageDialog(null, "clicked!");
////            }
////        });
//        JButton btn1 = new JButton("1");
////        btn1.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                JOptionPane.showMessageDialog(null, "clicked!");
////            }
////        });
//        JButton btn2 = new JButton("1");
////        btn2.addMouseListener(new MouseAdapter() {
////            @Override
////            public void mouseClicked(MouseEvent e) {
////                JOptionPane.showMessageDialog(null, "clicked!");
////            }
////        });
//        actBtnPanel.add(btn);
//        actBtnPanel.add(btn1);
//        actBtnPanel.add(btn2);
//        if (value.getClass() == String.class)
//            return new DefaultTableCellRenderer().
//                    getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//        return actBtnPanel;
        JLabel label = new JLabel("点击以进行进一步操作");
        label.setFont(this.cellFont);
        return label;
    }


//    @Override
//    public Component getTableCellRendererComponent(JTable table, Object value,
//                                                   boolean isSelected, boolean hasFocus,
//                                                   int row, int column) {
//        if (value.getClass() == String.class)
//            return new DefaultTableCellRenderer().
//                    getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//        else return (JButton)value;
//    }
}



