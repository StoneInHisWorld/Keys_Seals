package view.GUI;

import model.entity.Safe;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import java.util.LinkedList;
import java.util.List;

public class KeyModel extends AbstractTableModel {

    List<String[]> data;
    List<String> columnNames;

    public KeyModel(List<String> safes, List<String> columnNames) {
        this.data = new LinkedList<>();
        this.columnNames = columnNames;
        this.columnNames.add("操作");
        for (String safe : safes) {
            String[] safeMember = safe.split("\t");
            data.add(safeMember);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        // 附带上操作列
        return Safe.memberToStr().size() + 1;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return this.columnNames.get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex)[columnIndex];
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        this.data.get(rowIndex)[columnIndex] = (String)aValue;
    }
}
