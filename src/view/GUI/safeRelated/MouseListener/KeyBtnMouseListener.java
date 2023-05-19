package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;
import view.GUI.BasicMouseListener;
import view.GUI.safeRelated.Frame.AllDepOverviewFrame;
import view.GUI.safeRelated.Frame.SafeOverviewFrame;
import view.GUI.safeRelated.SafeGUIController;
import view.GUI.safeRelated.Frame.InputDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Set;

public class KeyBtnMouseListener extends MouseAdapter {

    private final JFrame ownerFrame;
    private final SafeGUIController guiController;

    public KeyBtnMouseListener(JFrame ownerFrame, String supDep) throws Exception {
        this.ownerFrame = ownerFrame;
        this.guiController = new SafeGUIController(supDep);
    }

    /**
     * 设置钥匙按钮点击事件，并初始化数据库
     * @param e 鼠标事件
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // 点击则激活KeyUIController
        while (true) {
            try {
                Set<String> departments = this.guiController.collectDepartments();
                JLabel msgLabel = new JLabel();
                // 弹出选择框，按钮为部门选择按钮
                msgLabel.setFont(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG));
                msgLabel.setText("请选择要查看保险柜的部门：");
                Object[] options = this.getDepOptions();
                int choice = JOptionPane.showOptionDialog(ownerFrame, msgLabel, "部门选择",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, null);
                if (this.dealChoice(departments, choice, options.length)) {
                    break;
                }
            } catch (Exception exception) {
                BasicMethods.dealException(exception);
            }
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
        Set<String> depStrs = this.guiController.collectDepartments();
        depStrs.add("总览");
        depStrs.add("添加部门");
        return depStrs.toArray();
    }

    private boolean dealChoice(Set<String> departments, int choice, int optionCount) throws Exception {
        if (choice == optionCount - 2) {
            // 选择部门总览
            new AllDepOverviewFrame(
                    this.ownerFrame, this.guiController
            ).present();
        }
        else if (choice == optionCount - 1) {
            // 选择添加部门
            String new_dep = JOptionPane.showInputDialog("请输入要添加的部门：");
            // 检查部门名输入
            if (new_dep == null) return false;
            if (new_dep.equals("")) throw new Exception("部门名不能为空！");
            if (departments.contains(new_dep)) {
                throw new Exception(new_dep + "已存在，请重新输入新的部门名，或查看"
                        + new_dep + "钥匙以添加新的保险柜！");
            }
            else if (!new_dep.equals(this.guiController.getSupDep()) && new_dep.contains("后勤")) {
                throw new Exception("有关后勤部门的信息，部门栏请填“" +
                        this.guiController.getSupDep() + "”！");
            }
            new InputDialog(
                    this.guiController.getAddingSafeToBeInput(new_dep), "添加" + new_dep + "的新保险柜",
                    new AddDepMouseListener(this.ownerFrame, new_dep)
            );
        }
        else if (choice >= 0) {
            // 选择了要查看的部门
            ownerFrame.dispose();
            this.showDepOverviewFrame(this.getSelectDepBtn(departments, choice));
        }
        return true;
    }

    private void showDepOverviewFrame(String dep) throws Exception {
        JFrame frame = new JFrame();
        new SafeOverviewFrame(
                ownerFrame, frame, dep, this.guiController
        );
    }

    private class AddDepMouseListener extends BasicMouseListener {

        private final String dep;

        protected AddDepMouseListener(JFrame parentFrame, String dep) {
            super(parentFrame);
            this.dep = dep;
        }

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void refreshParentFrame() {
            try {
                showDepOverviewFrame(this.dep);
                this.disposeParentFrame();
            } catch (Exception e) {
                BasicMethods.dealException(e);
            }
        }

        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            guiController.addSafe(this.dep, inputData);
        }
    }
}

