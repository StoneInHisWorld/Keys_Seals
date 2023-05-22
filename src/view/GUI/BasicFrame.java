package view.GUI;

import javax.swing.*;

abstract public class BasicFrame {

    protected final JFrame parentFrame;
    protected final JFrame ownerFrame;

    public BasicFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.ownerFrame = new JFrame();
    }

    public void present() throws Exception {
        this.ownerFrame.setContentPane(this.getMainPanel());
        this.ownerFrame.setTitle(this.getTitle());
        this.ownerFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        BasicMethods.setFrameSize(
                this.ownerFrame, this.getSize()
        );
        BasicMethods.moveToCenter(this.ownerFrame);
        this.ownerFrame.pack();
        this.ownerFrame.setVisible(true);
        if (parentFrame != null) this.parentFrame.dispose();
    }

    public void backToLastFrame() throws Exception {
        this.ownerFrame.dispose();
        if (parentFrame == null) throw new Exception("已到达最初界面！");
        this.parentFrame.pack();
        this.parentFrame.setVisible(true);
    }

    protected abstract int getSize();

    protected abstract String getTitle();

    protected abstract JPanel getMainPanel();

    public JFrame getOwnerFrame() { return this.ownerFrame; }
}
