package view.GUI.Frame;

import controller.KeyUIController;
import view.CmdUI.KeyUI;
import view.GUI.KeyModel;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DepOverviewFrame {

    private JPanel mainPanel;
    private JPanel add_del_Safe_Panel;
    private JScrollPane tablePanel;
    private JTable overviewTable;
    private final KeyUIController keyUIController;
    private String dep;

    public DepOverviewFrame(String dep, KeyUIController keyUIController) throws Exception {
        this.keyUIController = keyUIController;
        KeyModel model = new KeyModel(
                keyUIController.displaySafe(dep),
                this.getTableColumnNames());
        this.overviewTable.setModel(model);
    }

    private List<String> getTableColumnNames() {
        String memberStr = this.keyUIController.getSafeMemberNames(this.dep);
        List<String> ret = new LinkedList<>(Arrays.asList(memberStr.split("\t")));
        ret.add("操作");
        return ret;
    }
}