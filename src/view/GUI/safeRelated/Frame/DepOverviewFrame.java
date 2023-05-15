package view.GUI.safeRelated.Frame;

import view.GUI.BasicMethods;
import view.GUI.safeRelated.MouseListener.AddSafeBtnMouseListener;
import view.GUI.safeRelated.MouseListener.DelSafeBtnMouseListener;
import view.GUI.safeRelated.MouseListener.FetKeyBtnMouseListener;
import view.GUI.safeRelated.MouseListener.RetKeyBtnMouseListener;
import view.GUI.safeRelated.SafeGUIController;
import view.GUI.safeRelated.table.SafeTablePainter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class DepOverviewFrame {

    private JPanel mainPanel;
    private JPanel add_del_Safe_Panel;
    private JScrollPane tablePanel;
    private JTable overviewTable;
    private JButton addingBtn;
    private JButton backBtn;
    private final JFrame parentFrame;
    private final JFrame ownerFrame;
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
    public DepOverviewFrame(JFrame parentFrame, JFrame ownerFrame,
                            String dep, SafeGUIController safeGUIController) throws Exception {
        this.parentFrame = parentFrame;
        this.dep = dep;
        this.safeGUIController = safeGUIController;
        this.ownerFrame = ownerFrame;
        this.addSafeBtnDialogTitle = "添加" + dep + "保险柜";
        this.fetKeyBtnDialogTitle = "取出" + dep + "保险柜的钥匙";
        this.retKeyBtnDialogTitle = "归还" + dep + "保险柜的钥匙";
        this.dealAddingBtn();
        this.dealBackBtn();
        this.drawTable();
        this.present();
    }

    // 界面相关
    /**
     * 展示界面
     */
    public void present() {
        this.ownerFrame.setContentPane(this.mainPanel);
        this.ownerFrame.setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );
        this.ownerFrame.setTitle(dep + "保险柜信息");
        this.ownerFrame.setPreferredSize(
                new Dimension(1500, 750)
        );
        this.ownerFrame.pack();
        this.ownerFrame.setVisible(true);
        BasicMethods.moveToCenter(this.ownerFrame);
    }

    /**
     * 处理“添加保险柜”按钮
     */
    private void dealAddingBtn() {
        // 添加“添加按钮”监听器
        this.addingBtn.addMouseListener(
                new AddSafeBtnMouseListener(
                        ownerFrame, this,
                        this.safeGUIController.getAddingSafeToBeInput(this.dep),
                        this.addSafeBtnDialogTitle));
    }

    /**
     * 处理“返回”按钮
     */
    private void dealBackBtn() {
        this.backBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ownerFrame.dispose();
                parentFrame.setVisible(true);
            }
        });
    }

    // 表格相关
    /**
     * 绘制表格
     * @throws Exception 表格绘制异常
     */
    public void drawTable() throws Exception {
        SafeTablePainter safeTablePainter = new SafeTablePainter(
                this.overviewTable, this.ownerFrame, this.getActBtns(),
                this.safeGUIController.getSafeData(this.dep),
                this.safeGUIController.getColumnSize(this.dep),
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
                BasicMethods.getVisibleBtn(actions[0],
                BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                BasicMethods.NORMAL, new RetKeyBtnMouseListener(
                        this.ownerFrame, this,
                        this.getRetKeyBtnToBeInput(),
                        this.retKeyBtnDialogTitle))
        );
        btns.add(
                BasicMethods.getVisibleBtn(actions[1],
                BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                BasicMethods.NORMAL, new FetKeyBtnMouseListener(
                        this.ownerFrame, this.getFetKeyBtnToBeInput(),
                        this.fetKeyBtnDialogTitle, this
                ))
        );
        btns.add(
                BasicMethods.getVisibleBtn(actions[2],
                BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL),
                BasicMethods.NORMAL, new DelSafeBtnMouseListener(
                        this.ownerFrame, this
                ))
        );
        return btns.toArray();
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
//        StringBuilder input = new StringBuilder(selectedSafeId + "\t");
//        for (Object inputDatum : inputData) {
//            input.append(inputDatum);
//            input.append("\t");
//        }
//        char keySelected = inputData[0].toString().charAt(0);
        return this.safeGUIController.retKey(this.dep, inputData,
                selectedSafeId);
    }

    public String dealAddingSafe(final Object[] inputData) throws Exception {
//        StringBuilder safeStr = new StringBuilder();
//        for (Object inputDatum : inputData) {
//            safeStr.append(inputDatum.toString());
//            safeStr.append("\t");
//        }
//        this.safeUIController.addSafe(dep, safeStr.toString());
//        JOptionPane.showMessageDialog(null,
//                "添加" + dep + "保险柜成功！", "提示",
//                JOptionPane.INFORMATION_MESSAGE);
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
}