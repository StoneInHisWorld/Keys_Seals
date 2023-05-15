package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.Frame.DepOverviewFrame;
import view.GUI.safeRelated.table.InputDialog;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class FetKeyBtnMouseListener extends BasicMouseListener{

    private final Object[] toBeInput;
    private final String inputDialogTitle;
    private final DepOverviewFrame depOverviewFrame;

    public FetKeyBtnMouseListener(JFrame parentFrame,
                                  Object[] toBeInput,
                                  String inputDialogTitle,
                                  DepOverviewFrame depOverviewFrame) {
        super(parentFrame);
        this.toBeInput = toBeInput;
        this.inputDialogTitle = inputDialogTitle;
        this.depOverviewFrame = depOverviewFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
//            String[] toBeInput;
//            if (dep.equals(SafeUIController.supportDep)) {
//                toBeInput = new String[]{"*出库人", "备注"};
//            }
//            else {
//                toBeInput = new String[]{"*取备用或应急钥匙（b或j）", "*出库人", "备注"};
//            }
//            new InputDialog(toBeInput, "取出" +
//                    dep + "保险柜的钥匙", this);
            new InputDialog(toBeInput, this.inputDialogTitle,
                    this);
        } catch (Exception exception) {
            BasicMethods.dealException(exception);
        }
    }

    @Override
    public void refreshParentFrame() {
        try {
            this.depOverviewFrame.drawTable();
        } catch (Exception e) {
            BasicMethods.dealException(e);
        }
    }

    @Override
    public void dealTransaction(Object[] inputData) throws Exception {
        String successMsg = this.depOverviewFrame.dealFetchingKey(inputData);
        BasicMethods.prompt(successMsg);
    }
}