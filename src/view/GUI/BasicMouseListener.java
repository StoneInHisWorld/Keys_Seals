package view.GUI;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

abstract public class BasicMouseListener extends MouseAdapter {

    protected final JFrame parentFrame;

    /**
     * 基础鼠标监听器。
     * @param parentFrame 上级界面。用于展示和返回。
     */
    protected BasicMouseListener(JFrame parentFrame) {
        this.parentFrame = parentFrame;
    }

    public void disposeParentFrame() {
        parentFrame.dispose();
    }

    @Override
    public abstract void mouseClicked(MouseEvent e);

    public abstract void refreshParentFrame();
    public abstract void dealTransaction(Object[] inputData) throws Exception;
}
