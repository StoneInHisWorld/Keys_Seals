package view.GUI.safeRelated.MouseListener;

import view.GUI.BasicMethods;
import view.GUI.BasicMouseListener;
import view.GUI.safeRelated.Frame.DepOverviewFrame;
import view.GUI.safeRelated.SafeGUIController;
import view.GUI.safeRelated.table.InputDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.Set;

public class KeyBtnMouseListener extends MouseAdapter {

    private final JFrame mainFrame;
    private final SafeGUIController guiController;

    public KeyBtnMouseListener(JFrame mainFrame, String supDep) throws Exception {
        this.mainFrame = mainFrame;
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
                int choice = JOptionPane.showOptionDialog(mainFrame, msgLabel, "部门选择",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                        options, null);
                this.dealChoice(departments, choice, options.length);
                break;
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
        depStrs.add("添加部门");
        return depStrs.toArray();
    }

    private void dealChoice(Set<String> departments, int choice, int optionCount) throws Exception {
        if (choice == optionCount - 1) {
            // 选择添加部门
            String new_dep = JOptionPane.showInputDialog("请输入要添加的部门：");
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
                    new AddDepMouseListener(this.mainFrame, new_dep)
            );
        }
        else if (choice >= 0) {
            // 选择了要查看的部门
            mainFrame.dispose();
            this.showDepOverviewFrame(this.getSelectDepBtn(departments, choice));
        }
    }

    private void showDepOverviewFrame(String dep) throws Exception {
        JFrame frame = new JFrame();
        new DepOverviewFrame(
                mainFrame, frame, dep, this.guiController
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

