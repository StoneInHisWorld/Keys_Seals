package view.GUI.safeRelated.Frame;

import controller.SafeUIController;
import view.GUI.safeRelated.MouseListener.AddingBtnMouseListener;
import view.GUI.safeRelated.table.SafeTablePainter;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class DepOverviewFrame {

    private final JFrame parentFrame;
    private final JFrame ownerFrame;
    private JPanel mainPanel;
    private JPanel add_del_Safe_Panel;
    private JScrollPane tablePanel;
    private JTable overviewTable;
    private JButton addingBtn;
    private JButton backBtn;
    private final SafeUIController safeUIController;
    private final String dep;

    /**
     * 新建部门总览界面
     *
     * @param parentFrame 父框架
     * @param dep 所属部门
     * @param safeUIController 已初始化的KeyUIController
     * @throws Exception 空保险柜异常
     */
    public DepOverviewFrame(JFrame parentFrame, JFrame ownerFrame, String dep, SafeUIController safeUIController) throws Exception {
        this.parentFrame = parentFrame;
        this.dep = dep;
        this.safeUIController = safeUIController;
        this.ownerFrame = ownerFrame;
        this.drawTable();
        this.dealAddingBtn();
        this.dealBackBtn();
    }

    /**
     * 处理“添加保险柜”按钮
     */
    private void dealAddingBtn() {
        List<String> toBeInput = new LinkedList<>(Arrays.asList(safeUIController.getSafeMemberNames(dep).split("\t")));
        toBeInput.remove(0);
        toBeInput.remove(0);
        // 添加必填项
        int index = 0;
        if (dep.equals(SafeUIController.supportDep)) {
            toBeInput.set(index, "*" + toBeInput.get(index));
        }
        else {
            toBeInput.set(index, "*" + toBeInput.get(index));
            index++;
            toBeInput.set(index, "*" + toBeInput.get(index));
        }
        // 添加“添加按钮”监听器
        this.addingBtn.addMouseListener(new AddingBtnMouseListener(
                this.ownerFrame, toBeInput.toArray(), "添加" + dep + "保险柜"));
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

    /**
     * 绘制表格
     * @throws Exception 表格绘制异常
     */
    private void drawTable() throws Exception {
        SafeTablePainter safeTablePainter =
                new SafeTablePainter(dep, this.overviewTable,
                        safeUIController);
        safeTablePainter.drawTable();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}