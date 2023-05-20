package view.GUI.safeRelated.Frame;

import view.GUI.BasicMethods;
import view.GUI.BasicMouseListener;
import view.GUI.safeRelated.SafeGUIController;
import view.GUI.safeRelated.table.SafeTablePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class AllDepOverviewFrame {
    private JScrollPane depSafeTablePane;
    private JScrollPane supDepSafeTablePane;
    private JTable depSafeTable;
    private JTable supDepSafeTable;
    private JPanel buttonPanel;
    private JButton addSafeBtn;
    private JButton backBtn;
    private JPanel mainPanel;

    private final JFrame ownerFrame;
    private final JFrame parentFrame;
    private final SafeGUIController controller;
    private static final String[] actions = new String[]{"入库", "出库", "删除"};


    public AllDepOverviewFrame(JFrame parentFrame, SafeGUIController controller) throws Exception {
        this.parentFrame = parentFrame;
        this.controller = controller;
        this.ownerFrame = new JFrame("保险柜总览");
        this.dealAddingBtn();
        this.dealBackBtn();
        this.drawTable();
    }

    /**
     * 处理“添加保险柜”按钮
     */
    private void dealAddingBtn() {
        // 添加“添加按钮”监听器
        this.addSafeBtn.addMouseListener(
                new AddSafeBtnMouseListener(ownerFrame)
        );
    }

    private void dealBackBtn() {
        this.backBtn.addMouseListener(new BasicMouseListener(this.parentFrame) {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    ownerFrame.dispose();
                    this.showParentFrame();
                    controller.refreshDB();
                } catch (Exception exception) {
                    BasicMethods.dealException(exception);
                }
            }

            @Override
            public void refreshParentFrame() {

            }

            @Override
            public void dealTransaction(Object[] inputData) throws Exception {

            }
        });
    }

    public void present() {
        this.ownerFrame.setContentPane(this.mainPanel);
        this.ownerFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        this.ownerFrame.setPreferredSize(
                new Dimension(1500, 800)
        );
        this.ownerFrame.pack();
        this.parentFrame.dispose();
        this.ownerFrame.setVisible(true);
        BasicMethods.moveToCenter(this.ownerFrame);
    }

    private void drawTable() throws Exception {
        // 绘制部门保险柜表格
        Set<String> departments = this.controller.collectDepartments();
        this.drawDepTable(departments);
        this.drawSupDepTable(departments);
    }

    private void drawSupDepTable(Set<String> departments) throws Exception {
        for (String department : departments) {
            // 有后勤部则绘制表格
            if (department.equals(this.controller.getSupDep())) {
                SafeTablePainter safeTablePainter = new SafeTablePainter(
                        this.supDepSafeTable, this.ownerFrame, this.getActBtns(),
                        controller.getSafeData(department),
                        controller.getAllSafeColumnSize(department),
                        controller.getAllSafeTableColumnNames(department)
                );
                safeTablePainter.drawTable();
                return;
            }
        }
        this.supDepSafeTable.setVisible(false);
    }

    private void drawDepTable(Set<String> departments) throws Exception {
        String supportDep = this.controller.getSupDep();
        // 检查是否有其他部门
        for (String department : departments) {
            // 有除后勤部外的其他部门则绘制表格
            if (!department.equals(supportDep)) {
                // 收集除后勤部以外的保险柜的数据
                List<Object[]> data = new LinkedList<>();
                for (String s : departments) {
                    if (s.equals(supportDep)) break;
                    data.addAll(this.controller.getSafeData(s));
                }
                SafeTablePainter safeTablePainter = new SafeTablePainter(
                        this.depSafeTable, this.ownerFrame, this.getActBtns(),
                        data, controller.getAllSafeColumnSize(department),
                        controller.getAllSafeTableColumnNames(department)
                );
                safeTablePainter.drawTable();
                return;
            }
        }
        this.depSafeTablePane.setVisible(false);
    }

    private Object[] getActBtns() throws Exception {
        List<JButton> btns = new LinkedList<>();
        btns.add(
                BasicMethods.getVisibleBtn(
                        actions[0], BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                        BasicMethods.NORMAL, new RetKeyBtnMouseListener(this.ownerFrame)
                )
        );
        btns.add(
                BasicMethods.getVisibleBtn(
                        actions[1], BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                        BasicMethods.NORMAL, new FetKeyBtnMouseListener(this.ownerFrame)
                )
        );
        btns.add(
                BasicMethods.getVisibleBtn(
                        actions[2], BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                        BasicMethods.NORMAL, new DelSafeBtnMouseListener(this.ownerFrame)
                )
        );
        return btns.toArray();
    }

    private String getSelectedDep(Container clicked) throws Exception {
        // 获取点击的表格
        if (clicked.getName().equals(depSafeTable.getName())) {
            return depSafeTable.getValueAt(
                    depSafeTable.getSelectedRow(), 0
            ).toString();
        }
        else if (clicked.getName().equals(supDepSafeTable.getName())) {
            return supDepSafeTable.getValueAt(
                    supDepSafeTable.getSelectedRow(), 0
            ).toString();
        }
        else {
                throw new Exception("表格没有被选中！");
        }
    }

    private String getSelectedId(Container clicked) throws Exception {
        // 获取点击的表格
        if (clicked.getName().equals(depSafeTable.getName())) {
            return depSafeTable.getValueAt(
                    depSafeTable.getSelectedRow(), 1
            ).toString();
        }
        else if (clicked.getName().equals(supDepSafeTable.getName())) {
            return supDepSafeTable.getValueAt(
                    supDepSafeTable.getSelectedRow(), 1
            ).toString();
        }
        else {
            throw new Exception("表格没有被选中！");
        }
    }

    private Object[] getRetKeyBtnToBeInput(String dep) {
        return controller.getRetKeyBtnToBeInput(dep);
    }

    private Object[] getFetKeyBtnToBeInput(String dep) {
        return controller.getFetKeyBtnToBeInput(dep);
    }

    private class AddSafeBtnMouseListener extends BasicMouseListener {

        private String dep;

        public AddSafeBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            Set<String> departments = controller.collectDepartments();
            while (true) {
                try {
                    // 选择添加部门
                    String dep = BasicMethods.getSinLineInput(
                            "请输入要添加的部门：", "部门名不能为空！"
                    );
                    if (dep == null) break;
                    if (!dep.equals(controller.getSupDep()) && dep.contains("后勤")) {
                        throw new Exception("有关后勤部门的信息，部门栏请填“" +
                                controller.getSupDep() + "”！");
                    }
                    else if (!departments.contains(dep)) {
                        if (!BasicMethods.yesOrNo("将创建一个新部门：" + dep + "\n" +
                                "是否继续？")) {
                            continue;
                        }
                    }
                    this.dep = dep;
                    new InputDialog(
                            controller.getToBeInputOfAddSafe(dep),
                            "添加" + dep + "的新保险柜",
                            this
                    );
                    break;
                } catch (Exception exception) {
                    BasicMethods.dealException(exception);
                }
            }
        }

        @Override
        public void refreshParentFrame() {
            try {
                drawTable();
            } catch (Exception e) {
                BasicMethods.dealException(e);
            }
        }

        /**
         * 处理添加保险柜事务
         */
        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            String successMsg = controller.addSafe(this.dep, inputData);
            BasicMethods.prompt(successMsg);
        }
    }

    private class RetKeyBtnMouseListener extends BasicMouseListener {

        private Container belongsTo;

        public RetKeyBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                this.belongsTo = e.getComponent().getParent().getParent();
                String dep = getSelectedDep(belongsTo);
                new InputDialog(
                        getRetKeyBtnToBeInput(dep),
                        "归还" + dep + "的钥匙",
                        this
                );
            } catch (Exception exception) {
                BasicMethods.dealException(exception);
            }
        }

        @Override
        public void refreshParentFrame() {
            try {
                drawTable();
            } catch (Exception e) {
                BasicMethods.dealException(e);
            }
        }

        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            String selectedSafeId = getSelectedId(this.belongsTo);
            String successMsg = controller.retKey(
                    getSelectedDep(belongsTo), inputData, selectedSafeId
            );
            BasicMethods.prompt(successMsg);
        }
    }

    private class FetKeyBtnMouseListener extends BasicMouseListener {

        private Container belongsTo;

        public FetKeyBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                // 获取表格对象
                this.belongsTo = e.getComponent().getParent().getParent();
                String dep = getSelectedDep(this.belongsTo);
                new InputDialog(
                        getFetKeyBtnToBeInput(dep),
                        "取走" + dep + "的钥匙",
                        this
                );
            } catch (Exception exception) {
                BasicMethods.dealException(exception);
            }
        }

        @Override
        public void refreshParentFrame() {
            try {
                drawTable();
            } catch (Exception e) {
                BasicMethods.dealException(e);
            }
        }

        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            String successMsg = controller.fetchKey(
                    getSelectedDep(this.belongsTo),
                    inputData,
                    getSelectedId(this.belongsTo)
            );
            BasicMethods.prompt(successMsg);
        }
    }

    private class DelSafeBtnMouseListener extends BasicMouseListener {

        private Container belongsTo;

        public DelSafeBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (BasicMethods.yesOrNo("确定要删除该保险柜吗？")) {
                try {
                    this.belongsTo = e.getComponent().getParent().getParent();
                    this.dealTransaction(null);
                    this.refreshParentFrame();
                } catch (Exception exception) {
                    BasicMethods.dealException(exception);
                }
            }
        }

        @Override
        public void refreshParentFrame() {
            try {
                drawTable();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            String successMsg = controller.delSafe(
                    getSelectedDep(this.belongsTo),
                    Integer.parseInt(getSelectedId(this.belongsTo))
            );
            BasicMethods.prompt(successMsg);
        }
    }
}
