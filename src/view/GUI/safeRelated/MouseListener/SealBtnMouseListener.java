package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SealBtnMouseListener extends MouseAdapter {

    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            BasicMethods.showStandardMessageDialog("提示", "该功能尚未开放！", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
