package view.GUI.safeRelated.Frame;

import view.GUI.BasicFrame;
import view.GUI.BasicMethods;
import view.GUI.BasicMouseListener;
import view.GUI.safeRelated.SafeGUIController;
import view.GUI.safeRelated.table.SafeTablePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SafeOverviewFrame extends BasicFrame {

    private JPanel mainPanel;
    private JPanel add_del_Safe_Panel;
    private JScrollPane tablePanel;
    private JTable overviewTable;
    private JButton addingBtn;
    private JButton backBtn;
    private final SafeGUIController safeGUIController;
    private final String dep;
    private static final String[] actions = new String[]{"入库", "出库", "删除"};
    private final String addSafeBtnDialogTitle;
    private final String fetKeyBtnDialogTitle;
    private final String retKeyBtnDialogTitle;

    /**
     * 新建部门总览界面
     *
     * @param parentFrame 父框架
     * @param dep 所属部门
     * @throws Exception 空保险柜异常
     */
    public SafeOverviewFrame(JFrame parentFrame,
                             String dep,
                             SafeGUIController safeGUIController) throws Exception {
        super(parentFrame);
        this.dep = dep;
        this.safeGUIController = safeGUIController;
        this.addSafeBtnDialogTitle = "添加" + dep + "保险柜";
        this.fetKeyBtnDialogTitle = "取出" + dep + "保险柜的钥匙";
        this.retKeyBtnDialogTitle = "归还" + dep + "保险柜的钥匙";
        this.dealAddingBtn();
        this.dealBackBtn();
        this.drawTable();
        this.present();
    }

    /**
     * 处理“添加保险柜”按钮
     */
    private void dealAddingBtn() {
        // 添加“添加按钮”监听器
        this.addingBtn.addMouseListener(
                new AddSafeBtnMouseListener(ownerFrame)
        );
    }

    /**
     * 处理“返回”按钮
     */
    private void dealBackBtn() {
        this.backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    backToLastFrame();
                } catch (Exception exception) {
                    BasicMethods.dealException(exception);
                }
            }
        });
    }

    // 表格相关
    /**
     * 绘制表格
     * @throws Exception 表格绘制异常
     */
    public void drawTable() throws Exception {
        // 获取完整的保险柜数据
        List<Object[]> safeData = this.safeGUIController.getSafeData(this.dep);
        // 每条保险柜信息去掉部门名称并在末尾加上空字符串
        for (int i = 0; i < safeData.size(); i++) {
            List<Object> members = new LinkedList<>(Arrays.asList(safeData.get(i)));
            members.remove(0);
            // 留出操作栏空间
            members.add("null");
            safeData.set(i, members.toArray());
        }
        SafeTablePainter safeTablePainter = new SafeTablePainter(
                this.overviewTable, this.ownerFrame, this.getActBtns(),
                safeData, this.safeGUIController.getColumnSize(this.dep),
                this.getTableColumnNames()
        );
        safeTablePainter.drawTable();
    }

    /**
     * 获取操作按钮
     * @return 操作按钮数组
     * @throws Exception 字体参数异常
     */
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

    private Object[] getAddSafeToBeToInput() {
        return this.safeGUIController.getToBeInputOfAddSafe(this.dep);
    }

    private Object[] getRetKeyBtnToBeInput() {
        return this.safeGUIController.getRetKeyBtnToBeInput(this.dep);
    }

    private Object[] getFetKeyBtnToBeInput() {
        return this.safeGUIController.getFetKeyBtnToBeInput(this.dep);
    }

    /**
     * 获取表格列名
     * @return 可用表格列名
     */
    protected String[] getTableColumnNames() {
        return this.safeGUIController.getTableColumnNames(this.dep);
    }

    // 处理按钮事务
    /**
     * 处理归还钥匙事务
     * @param inputData 用户输入的数据集
     * @return 成功信息
     * @throws Exception 钥匙选择类型未知异常
     */
    public String dealReturningKey(final Object[] inputData) throws Exception {
        String selectedSafeId = this.overviewTable.getModel().getValueAt(
                this.overviewTable.getSelectedRow(), 0).toString();
        return this.safeGUIController.retKey(this.dep, inputData,
                selectedSafeId);
    }

    public String dealAddingSafe(final Object[] inputData) throws Exception {
        return this.safeGUIController.addSafe(this.dep, inputData);
    }

    public String dealDeletingSafe() throws Exception {
        int id = Integer.parseInt(
                this.overviewTable.getModel().getValueAt(
                        this.overviewTable.getSelectedRow(), 0).
                toString()
        );
        return this.safeGUIController.delSafe(this.dep, id);
    }

    public String dealFetchingKey(Object[] inputData) throws Exception {
        String selectedSafeId = this.overviewTable.getModel().getValueAt(
                this.overviewTable.getSelectedRow(), 0).toString();
        return this.safeGUIController.fetchKey(this.dep, inputData,
                selectedSafeId);
    }

    @Override
    protected int getSize() {
        return BasicMethods.BIG;
    }

    @Override
    protected String getTitle() {
        return this.dep + "保险柜信息";
    }

    @Override
    protected JPanel getMainPanel() {
        return this.mainPanel;
    }

    private class FetKeyBtnMouseListener extends BasicMouseListener {

        private final Object[] toBeInput;
        private final String inputDialogTitle;

        public FetKeyBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
            this.toBeInput = getFetKeyBtnToBeInput();
            this.inputDialogTitle = fetKeyBtnDialogTitle;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                new InputDialog(this.toBeInput, this.inputDialogTitle,
                        this, BasicMethods.BIG);
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
            String successMsg = dealFetchingKey(inputData);
            BasicMethods.prompt(successMsg);
        }
    }

    private class DelSafeBtnMouseListener extends BasicMouseListener {

        public DelSafeBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (BasicMethods.yesOrNo("确定要删除该保险柜吗？")) {
                try {
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
            String successMsg = dealDeletingSafe();
            BasicMethods.prompt(successMsg);
        }
    }

    private class AddSafeBtnMouseListener extends BasicMouseListener {

        private final Object[] toBeInput;
        private final String dialogTitle;

        public AddSafeBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
            this.toBeInput = getAddSafeToBeToInput();
            this.dialogTitle = addSafeBtnDialogTitle;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                new InputDialog(toBeInput, dialogTitle, this, BasicMethods.ULTRA);
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

        /**
         * 处理添加保险柜事务
         */
        @Override
        public void dealTransaction(Object[] inputData) throws Exception {
            String successMsg = dealAddingSafe(inputData);
            BasicMethods.prompt(successMsg);
        }
    }

    private class RetKeyBtnMouseListener extends BasicMouseListener {

        private final Object[] toBeInput;
        private final String dialogTitle;

        public RetKeyBtnMouseListener(JFrame parentFrame) {
            super(parentFrame);
            this.toBeInput = getRetKeyBtnToBeInput();
            this.dialogTitle = retKeyBtnDialogTitle;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                new InputDialog(toBeInput, dialogTitle, this, BasicMethods.BIG);
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
            String successMsg = dealReturningKey(inputData);
            BasicMethods.prompt(successMsg);
        }
    }
}