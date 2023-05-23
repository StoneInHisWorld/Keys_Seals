package view.GUI;

import javax.swing.*;

abstract public class BasicFrame {

    protected final JFrame parentFrame;
    protected final JFrame ownerFrame;

    /**
     * 基本界面类。包括界面类处理的基本方法。
     * @param parentFrame 上级界面
     */
    public BasicFrame(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.ownerFrame = new JFrame();
    }

    /**
     * 设置界面的基本属性并展示本界面
     * @throws Exception 窗体展示异常
     */
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

    /**
     * 返回上级界面
     * @throws Exception 上级窗体不存在异常
     */
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
