package view.GUI.safeRelated.MouseListener;

import controller.SafeUIController;
import view.GUI.BasicMethods;
import view.GUI.safeRelated.Frame.DepOverviewFrame;
import view.GUI.safeRelated.SafeGUIController;

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
//            if (departments.size() != 0) {
            // 弹出选择框，按钮为部门选择按钮
            msgLabel.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
            msgLabel.setText("请选择要查看保险柜的部门：");
            Object[] options = this.getDepOptions();
            int choice = JOptionPane.showOptionDialog(mainFrame, msgLabel, "部门选择",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    options, null);
            this.dealChoice(departments, choice, options.length);
        } catch (Exception exception) {
            BasicMethods.dealException(exception);
        }
    }

    private String getSelectDepBtn(Set<String> departments, int choice) {
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
        depStrs.add("添加部门");
        return depStrs.toArray();
    }

    private void dealChoice(Set<String> departments, int choice, int optionCount) throws Exception {
        if (choice == optionCount - 1) {
            // 选择添加部门
            BasicMethods.dealException(new Exception("该功能暂未开放！"));

        }
        else if (choice >= 0) {
            // 选择了要查看的部门
            mainFrame.dispose();
            this.showDepOverviewFrame(this.getSelectDepBtn(departments, choice));
        }
    }

    private void showDepOverviewFrame(String dep) throws Exception {
        JFrame frame = new JFrame();
        DepOverviewFrame depOverviewFrame = new DepOverviewFrame(
                mainFrame, frame, dep,
                new SafeGUIController(this.safeUIController)
        );
//        frame.setContentPane(depOverviewFrame.getMainPanel());
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setTitle(dep + "保险柜信息");
//        frame.setPreferredSize(new Dimension(1500, 750));
//        frame.pack();
//        frame.setVisible(true);
//        BasicMethods.moveToCenter(frame);
    }
}

