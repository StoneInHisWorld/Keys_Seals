package view.GUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public abstract class TablePainter {

    protected final JTable toBePainted;
    protected final Font tableFont;
    protected final JFrame ownerFrame;
    protected final List<Object[]> data;
    protected final String[] columnNames;

    /**
     * 获得表格绘图器对象
     * @param toBePainted 绘制的表格对象
     * @param ownerFrame 表格所属界面
     * @throws Exception 字体参数异常
     */
    public TablePainter(JTable toBePainted, JFrame ownerFrame,
                        List<Object[]> data, String[] columnNames) throws Exception {
        this.ownerFrame = ownerFrame;
        this.data = data;
        this.columnNames = columnNames;
        this.tableFont = BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL);
        this.toBePainted = toBePainted;
        this.toBePainted.setFont(this.tableFont);
        // 设置表头
        this.toBePainted.getTableHeader().setFont(
                BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
        this.toBePainted.setRowHeight(BasicMethods.
                getTableCellSize(BasicMethods.SMALL).height);
    }

    /**
     * 设置表格某列的尺寸
     * @param columnIndex 列下标
     * @param size 预设尺寸
     * @throws Exception 尺寸参数错误
     */
    protected void setTableColumnSize(int columnIndex, int size) throws Exception {
        Dimension d;
        switch (size) {
            case BasicMethods.SMALL:
            case BasicMethods.NORMAL:
            case BasicMethods.BIG:
            case BasicMethods.ULTRA:
                d = BasicMethods.getTableCellSize(size);break;
            default:throw new Exception("不支持的列大小" + size);
        }
        this.toBePainted.getColumnModel().getColumn(columnIndex).
                setPreferredWidth(d.width);
    }

    abstract protected void drawTable() throws Exception;
    abstract protected DefaultTableModel getTableModel() throws Exception;
}
