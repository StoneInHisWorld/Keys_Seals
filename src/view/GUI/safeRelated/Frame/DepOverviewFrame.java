package view.GUI.safeRelated.Frame;

import controller.KeyUIController;
import view.GUI.BasicMethods;
import view.GUI.safeRelated.table.ActBtnCellEditor;
import view.GUI.safeRelated.table.ActBtnCellRenderer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DepOverviewFrame {

    private JPanel mainPanel;
    private JPanel add_del_Safe_Panel;
    private JScrollPane tablePanel;
    private JTable overviewTable;
    private JButton addBtn;
    private JButton backBtn;
    private final KeyUIController keyUIController;
    private String dep;
    private Font tableFont;
    private final int rowHeight = 30;
    private final String[] actions = new String[]{"入库", "出库", "删除"};

    /**
     * 新建部门总览界面
     * @param dep 所属部门
     * @param keyUIController 已初始化的KeyUIController
     * @throws Exception 空保险柜异常
     */
    public DepOverviewFrame(String dep, KeyUIController keyUIController) throws Exception {
        this.dep = dep;
        this.keyUIController = keyUIController;
        this.tableFont = BasicMethods.getPlainFont(15);
        this.drawTable();
    }

    private void drawTable() throws Exception {
        // 设置表头
        this.overviewTable.getTableHeader().setFont(
                BasicMethods.getPlainFont(20));
        this.overviewTable.setRowHeight(BasicMethods.
                getTableCellSize(BasicMethods.SMALL).height);
        // 获取数据
        TableModel model = this.getTableModel();
        int columnCount = model.getColumnCount();
        this.overviewTable.setModel(this.getTableModel());
        this.overviewTable.setFont(this.tableFont);
        int index = 0;
        this.setTableColumnSize(index++, BasicMethods.SMALL);
        this.setTableColumnSize(index++, BasicMethods.NORMAL);
        this.setTableColumnSize(index++, BasicMethods.SMALL);
        this.setTableColumnSize(index++, BasicMethods.SMALL);
        this.setTableColumnSize(index++, BasicMethods.SMALL);
        this.setTableColumnSize(index++, BasicMethods.BIG);
        this.setTableColumnSize(index++, BasicMethods.BIG);
        this.setTableColumnSize(index++, BasicMethods.ULTRA);
        // 设置最后一列
        this.overviewTable.getColumnModel().
                getColumn(columnCount - 1).
                setCellRenderer(new ActBtnCellRenderer());
        this.overviewTable.getColumnModel().
                getColumn(columnCount - 1).
                setCellEditor(new ActBtnCellEditor(actions));
        this.setTableColumnSize(index, BasicMethods.ULTRA);
    }

    private List<String> getTableColumnNames() {
        String memberStr = this.keyUIController.getSafeMemberNames(this.dep);
        List<String> ret = new LinkedList<>(Arrays.asList(memberStr.split("\t")));
        // 去掉部门名称表头
        ret.remove(0);
        ret.add("操作");
        return ret;
    }

    private void setTableColumnSize(int columnIndex, int size) throws Exception {
        Dimension d;
        switch (size) {
            case BasicMethods.SMALL:
            case BasicMethods.NORMAL:
            case BasicMethods.BIG:
            case BasicMethods.ULTRA:
                d = BasicMethods.getTableCellSize(size);break;
            default:throw new Exception("不支持的列大小" + size);
        }
        this.overviewTable.getColumnModel().getColumn(columnIndex).
                setPreferredWidth(d.width);
    }

    private DefaultTableModel getTableModel() throws Exception {
        List<Object[]> membersOfSafes = keyUIController.getSafeMembers(dep);
        // 每条保险柜信息去掉部门名称并在末尾加上空字符串
        for (int i = 0; i < membersOfSafes.size(); i++) {
            List<Object> members = new LinkedList<>(Arrays.asList(membersOfSafes.get(i)));
            members.remove(0);
            members.add("null");
            membersOfSafes.set(i, members.toArray());
        }
        Object[][] dataVectors = new Object[membersOfSafes.size()][];
        for (int i = 0; i < dataVectors.length; i++) {
            dataVectors[i] = membersOfSafes.get(i);
        }
        // 获取表格列名
        Object[] columnNames = this.getTableColumnNames().toArray();
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setDataVector(dataVectors, columnNames);
        return defaultTableModel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}