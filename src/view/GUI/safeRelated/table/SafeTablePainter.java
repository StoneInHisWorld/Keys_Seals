package view.GUI.safeRelated.table;

import controller.SafeUIController;
import view.GUI.BasicMethods;
import view.GUI.TablePainter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SafeTablePainter extends TablePainter {

    private final String dep;
    private final SafeUIController safeUIController;

    /**
     * 得到保险柜表格绘制器
     * @param dep 保险柜所属部门
     * @param toBePainted 绘制的表格对象
     * @param safeUIController 保险柜UI控制器
     * @throws Exception 字体参数异常
     */
    public SafeTablePainter(String dep, JTable toBePainted,
                            SafeUIController safeUIController) throws Exception {
        super(toBePainted);
        this.dep = dep;
        this.safeUIController = safeUIController;
    }

    /**
     * 绘制表格。
     * @throws Exception 找不到保险柜异常、数据文件无效异常、列尺寸参数异常
     */
    public void drawTable() throws Exception {
        // 获取数据
        TableModel model = this.getTableModel();
        int columnCount = model.getColumnCount();
        this.toBePainted.setModel(this.getTableModel());
        // 定制表格每列的宽度
        int index = 0;
        if (this.dep.equals(SafeUIController.supportDep)) {
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.NORMAL);
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.BIG);
            this.setTableColumnSize(index++, BasicMethods.BIG);
            this.setTableColumnSize(index++, BasicMethods.ULTRA);
        }
        else {
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.NORMAL);
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.SMALL);
            this.setTableColumnSize(index++, BasicMethods.BIG);
            this.setTableColumnSize(index++, BasicMethods.BIG);
            this.setTableColumnSize(index++, BasicMethods.ULTRA);
        }
        // 设置最后一列
        this.toBePainted.getColumnModel().
                getColumn(columnCount - 1).
                setCellRenderer(new ActBtnCellRenderer(BasicMethods.NORMAL));
        this.toBePainted.getColumnModel().
                getColumn(columnCount - 1).
                setCellEditor(new ActBtnCellEditor(actions, BasicMethods.NORMAL));
        setTableColumnSize(index, BasicMethods.ULTRA);
    }

    /**
     * 获取表格模型
     * @return 默认表格模型
     * @throws Exception 找不到部门保险柜异常、数据文件读取异常
     */
    protected DefaultTableModel getTableModel() throws Exception {
        List<Object[]> membersOfSafes = safeUIController.getSafeMembers(dep);
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

    /**
     * 获取表格列名
     * @return 可用表格列名
     */
    protected List<String> getTableColumnNames() {
        String memberStr = this.safeUIController.getSafeMemberNames(this.dep);
        List<String> ret = new LinkedList<>(Arrays.asList(memberStr.split("\t")));
        // 去掉部门名称表头
        ret.remove(0);
        ret.add("操作");
        return ret;
    }
}
