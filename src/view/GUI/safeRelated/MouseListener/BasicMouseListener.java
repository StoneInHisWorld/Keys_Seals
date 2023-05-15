package view.GUI.safeRelated.MouseListener;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract public class BasicMouseListener extends MouseAdapter {

    protected final JFrame parentFrame;

    protected BasicMouseListener(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void disposeParentFrame() {
        parentFrame.dispose();
    }

    public void showParentFrame() {
        parentFrame.validate();
        parentFrame.setVisible(true);
    }

    @Override
    public abstract void mouseClicked(MouseEvent e);

    public abstract void refreshParentFrame();
    public abstract void dealTransaction(Object[] inputData) throws Exception;
}
