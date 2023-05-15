package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.Frame.DepOverviewFrame;
import view.GUI.safeRelated.table.InputDialog;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class AddSafeBtnMouseListener extends BasicMouseListener {

    private final DepOverviewFrame depOverviewFrame;
    private final Object[] toBeInput;
    private final String dialogTitle;

    public AddSafeBtnMouseListener(JFrame parentFrame, DepOverviewFrame depOverviewFrame,
                                   Object[] toBeInput, String dialogTitle) {
        super(parentFrame);
        this.depOverviewFrame = depOverviewFrame;
        this.toBeInput = toBeInput;
        this.dialogTitle = dialogTitle;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
//            List<String> toBeInput = new LinkedList<>(
//                    Arrays.asList(safeUIController.getSafeMemberNames(dep).split("\t")));
//            toBeInput.remove(0);
//            toBeInput.remove(0);
//            // 添加必填项
//            int index = 0;
//            if (dep.equals(SafeUIController.supportDep)) {
//                toBeInput.set(index, "*" + toBeInput.get(index));
//            }
//            else {
//                toBeInput.set(index, "*" + toBeInput.get(index));
//                index++;
//                toBeInput.set(index, "*" + toBeInput.get(index));
//            }
            new InputDialog(this.toBeInput, dialogTitle, this);
        } catch (Exception exception) {
            BasicMethods.dealException(exception);
        }
    }

    @Override
    public void refreshParentFrame() {
        try {
            depOverviewFrame.drawTable();
        } catch (Exception e) {
            BasicMethods.dealException(e);
        }
    }

    /**
     * 处理添加保险柜事务
     */
    @Override
    public void dealTransaction(Object[] inputData) throws Exception {
        String successMsg = this.depOverviewFrame.dealAddingSafe(inputData);
//        try {
//
//        } catch (Exception e) {
//            BasicMethods.dealException(e);
//            e.printStackTrace();
//        }
        BasicMethods.prompt(successMsg);
    }
}
