package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.table.InputDialog;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AddingBtnMouseListener extends MouseAdapter {

    private final JFrame frame;
    private final Object[] toBeInput;
    private final String dialogTitle;

    public AddingBtnMouseListener(JFrame frame,
                                  Object[] toBeInput, String dialogTitle) {
        this.frame = frame;
        this.toBeInput = toBeInput;
        this.dialogTitle = dialogTitle;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        InputDialog inputDialog;
        try {
            inputDialog = new InputDialog(this.toBeInput);
            BasicMethods.moveToCenter(inputDialog);
            inputDialog.setTitle(this.dialogTitle);
            inputDialog.pack();
            inputDialog.setVisible(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
