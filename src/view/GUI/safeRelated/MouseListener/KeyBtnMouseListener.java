package view.GUI.safeRelated.MouseListener;

import controller.SafeUIController;
import view.GUI.BasicMethods;
import view.GUI.safeRelated.Frame.DepOverviewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Set;

public class KeyBtnMouseListener extends MouseAdapter {

    private final JFrame mainFrame;
    private final SafeUIController safeUIController;

    public KeyBtnMouseListener(JFrame mainFrame, String supDep) {
        this.mainFrame = mainFrame;
        this.safeUIController = new SafeUIController(supDep);
    }

    /**
     * 设置钥匙按钮点击事件，并初始化数据库
     * @param e 鼠标事件
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // 点击则激活KeyUIController
        try {
            this.safeUIController.initKeyUI();
            Set<String> departments = this.safeUIController.getDepartments();
            JLabel msgLabel = new JLabel();
            if (departments.size() != 0) {
                // 弹出选择框，按钮为部门选择按钮
                msgLabel.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
                msgLabel.setText("请选择要查看保险柜的部门：");
                Object[] options = this.getDepOptions();
                int choice = JOptionPane.showOptionDialog(mainFrame, msgLabel, "部门选择",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, null);
                if (choice == JOptionPane.CLOSED_OPTION) return;
                mainFrame.dispose();
                this.showDepOverviewFrame(this.getSelectDep(departments, choice));
            } else {
                // 弹出提示，不存在部门
                BasicMethods.showStandardMessageDialog("部门选择", "数据库内暂无部门！", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception exception) {
            BasicMethods.showStandardMessageDialog("出错", exception.getMessage(), JOptionPane.ERROR_MESSAGE);
            exception.printStackTrace();
        }
    }

    private String getSelectDep(Set<String> departments, int choice) {
        // 获取用户选择的部门名
        Iterator<String> it = departments.iterator();
        for (int i = 0; i < choice; i++) {
            it.next();
        }
        return it.next();
    }

    private Object[] getDepOptions() {
        // 为每一个部门创建一个按钮
        Set<String> depStrs = this.safeUIController.getDepartments();
        return depStrs.toArray();
    }

    private void showDepOverviewFrame(String dep) throws Exception {
        JFrame frame = new JFrame("DepOverviewFrame");
        DepOverviewFrame depOverviewFrame = new DepOverviewFrame(mainFrame, frame, dep, this.safeUIController);
        frame.setContentPane(depOverviewFrame.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle(dep + "保险柜信息");
        frame.setPreferredSize(new Dimension(1500, 750));
        frame.pack();
        frame.setVisible(true);
        BasicMethods.moveToCenter(frame);
    }
}

