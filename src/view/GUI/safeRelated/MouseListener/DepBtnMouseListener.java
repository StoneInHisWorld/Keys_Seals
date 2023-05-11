package view.GUI.safeRelated.MouseListener;

import controller.SafeUIController;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DepBtnMouseListener extends MouseAdapter {

    private String dep;
    private SafeUIController safeUIController;
    private JFrame parentFrame;
    private JButton parentBtn;

    /**
     * 初始化部门按钮监听器
     * @param dep 部门按钮所属部门
     * @param safeUIController 钥匙管理系统控制器
     */
    public DepBtnMouseListener(String dep, SafeUIController safeUIController, JFrame parentFrame) {
        this.dep = dep;
        this.safeUIController = safeUIController;
        this.parentFrame = parentFrame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
//        this.parentFrame.setVisible(false);
//        try {
//            Object o = e.getSource();
//            // 调出保险柜总览界面
//            JFrame frame = new JFrame("DepOverviewFrame");
//            DepOverviewFrame depOverviewFrame = null;
//            depOverviewFrame = new DepOverviewFrame(dep, safeUIController);
//            frame.setContentPane(depOverviewFrame.getMainPanel());
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            frame.setTitle(dep + "保险柜信息");
//            frame.pack();
//            frame.setVisible(true);
//            // 设置框架位置为屏幕中央
//            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//            int x = (int) screenSize.getWidth() / 2 - frame.getWidth() / 2;
//            int y = (int) screenSize.getHeight() / 2 - frame.getHeight() / 2;
//            frame.setLocation(x, y);
//        } catch (Exception exception) {
//            BasicMethods.showStandardMessageDialog("异常", exception.getMessage(), JOptionPane.ERROR_MESSAGE);
//            exception.printStackTrace();
//        }
    }
}
