package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.MouseListener.ActionBtnMouseListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class KeyModel extends AbstractTableModel {

    private List<Object[]> data;
    private List<String> columnNames;
    private Font cellFont;

    public KeyModel(List<Object[]> membersOfSafes, List<String> columnNames, Font cellFont) {
        this.data = new LinkedList<>();
        this.columnNames = columnNames;
        this.cellFont = cellFont;
        // this.columnNames.add("操作");
        for (Object[] members : membersOfSafes) {
            List<Object> cellData = new LinkedList<>(Arrays.asList(members));
            // 去掉部门名称
            cellData.remove(0);
//            cellData.add(this.getActionPanel());
//            cellData.addAll(this.getFetchAndRetKeys());
//            Object[] safeMember = safe.split("\t");
            data.add(cellData.toArray());
        }
    }

    private JPanel getActionPanel() {
        JPanel panel = new JPanel();
        for (JButton button : this.getFetchAndRetKeysBtn()) {
            panel.add(button);
        }
        panel.validate();
        panel.setVisible(true);
        return panel;
    }

    private List<JButton> getFetchAndRetKeysBtn() {
        JButton[] btns = new JButton[3];
        btns[0] = BasicMethods.getVisibleBtn("入库", this.cellFont, new ActionBtnMouseListener());
        btns[1] = BasicMethods.getVisibleBtn("出库", this.cellFont, new ActionBtnMouseListener());
        btns[2] = BasicMethods.getVisibleBtn("删除保险柜", this.cellFont, new ActionBtnMouseListener());
//        btns[1] = new JButton("出库");
//        btns[2] = new JButton("删除");
        //        for (JButton btn : btns) {
//            btn.addMouseListener(new ActionBtnMouseListener());
//            BasicMethods.getVisibleBtn(
//                    "入库",
//            )
//            btnList.add(btn);
//        }
        return new LinkedList<>(Arrays.asList(btns));
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        // 附带上操作列
        return this.columnNames.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames.get(columnIndex);
    }

//    @Override
//    public Class<?> getColumnClass(int columnIndex) {
//        return String.class;
//    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

//    @Override
//    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
//        // 排除操作列取值
//        if (columnIndex == this.columnNames.size() - 1) System.out.println("操作列无值可取！");
//        this.data.get(rowIndex)[columnIndex] = (String)aValue;
//    }
}
