package view.GUI.safeRelated.table;

import view.GUI.BasicMethods;
import view.GUI.TablePainter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SafeTablePainter extends TablePainter {

    private final Object[] actionBtns;
    private final List<Object[]> safeData;
    private final int[] columnSize;

    /**
     * 得到保险柜表格绘制器
     * @param toBePainted 绘制的表格对象
     * @param actionBtns 操作按钮
     * @param safeData 保险柜数据
     * @param columnSize 列大小序列
     * @param columnName 列名
     * @throws Exception 字体参数异常
     */
    public SafeTablePainter(JTable toBePainted, JFrame ownerFrame,
                            Object[] actionBtns, List<Object[]> safeData,
                            int[] columnSize, String[] columnName) throws Exception {
        super(toBePainted, ownerFrame, safeData, columnName);
        this.actionBtns = actionBtns;
        this.safeData = safeData;
        this.columnSize = columnSize;
    }

    /**
     * 绘制表格。
     * @throws Exception 找不到保险柜异常、数据文件无效异常、列尺寸参数异常
     */
    public void drawTable() throws Exception {
        // 获取数据
        TableModel model = this.getTableModel();
        int columnCount = model.getColumnCount();
        this.toBePainted.setModel(model);
        // 定制表格每列的宽度
        int index;
        for (index = 0; index < this.columnSize.length; index++) {
            this.setTableColumnSize(index, this.columnSize[index]);
        }
        // 设置最后一列
        this.toBePainted.getColumnModel().getColumn(columnCount - 1).
                setCellRenderer(new ActBtnCellRenderer(BasicMethods.NORMAL));
        this.toBePainted.getColumnModel().getColumn(columnCount - 1).
                setCellEditor(new ActBtnCellEditor(this.actionBtns
                ));
        setTableColumnSize(index, BasicMethods.ULTRA);
    }

    /**
     * 获取表格模型
     * @return 默认表格模型
     */
    protected DefaultTableModel getTableModel() {
        // 每条保险柜信息去掉部门名称并在末尾加上空字符串
        for (int i = 0; i < safeData.size(); i++) {
            List<Object> members = new LinkedList<>(Arrays.asList(safeData.get(i)));
            members.remove(0);
            members.add("null");
            safeData.set(i, members.toArray());
        }
        Object[][] dataVectors = new Object[safeData.size()][];
        for (int i = 0; i < dataVectors.length; i++) {
            dataVectors[i] = safeData.get(i);
        }
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setDataVector(dataVectors, this.columnNames);
        return defaultTableModel;
    }
}
