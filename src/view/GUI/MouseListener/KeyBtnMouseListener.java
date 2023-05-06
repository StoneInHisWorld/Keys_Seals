package view.GUI.MouseListener;

import controller.KeyUIController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class KeyBtnMouseListener extends MouseAdapter {

    private final JPanel topPanel;
    private final JButton keyBt;
    private final JButton sealBt;
    private final JPanel sealBtPanel;
    private List<JButton> depBtnList;
    private final KeyUIController keyUIController;
    private final String keyState = "钥匙";
    private final String selectState = "返回";

    public KeyBtnMouseListener(JButton keyBt, JButton sealBt, JPanel topPanel, JPanel sealBtPanel,
                               KeyUIController keyUIController) {
        this.keyBt = keyBt;
        this.sealBt = sealBt;
        this.topPanel = topPanel;
        this.keyUIController = keyUIController;
        this.sealBtPanel = sealBtPanel;
    }

    /**
     * 设置钥匙按钮点击事件
     *
     * @param e 鼠标事件
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        // 转换为部门选择状态
        if (keyBt.getText().equals(this.keyState)) {
            this.toSelectState();
        } else if (keyBt.getText().equals(this.selectState)) {
            this.toKeyState();
        }
    }

    private void toSelectState() {
        try {
            // 获取部门按钮列表
            List<JButton> btnList = this.getDepBt();
            List<JPanel> panels = this.getDepPanelList(btnList);
            // 将提示标签和按钮全部塞入sealPanel
            GridLayout newLayout = new GridLayout(panels.size()+1, 1);
            this.sealBtPanel.setLayout(newLayout);
            this.sealBtPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            this.sealBtPanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
            // 提示标签
            int index = 0;
            for (JPanel panel : panels) {
                this.sealBtPanel.add(panel, index++);
            }
            // 调整组件的可见性
            this.topPanel.setVisible(false);
            this.keyBt.setText(this.selectState);
            this.keyBt.setVerticalAlignment(SwingConstants.CENTER);
            this.sealBt.setVisible(false);
            this.sealBtPanel.validate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.keyBt, e.getMessage(), "出错", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<JPanel> getDepPanelList(List<JButton> depBtnList) {
        List<JPanel> panels = new LinkedList<>();
        // 添加选择提示语
        JLabel promptLabel = new JLabel("请选择需要查看的部门：");
        promptLabel.setFont(this.getNormalFont());
        JPanel panel = new JPanel();
        panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        panel.add(promptLabel, BorderLayout.CENTER);
        panels.add(panel);
        // 添加部门选择按钮
        for (JButton button : depBtnList) {
            panel = new JPanel();
            panel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            panel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
            panel.add(button, BorderLayout.CENTER);
            panels.add(panel);
        }
        return panels;
    }

    private void toKeyState() {
        this.sealBtPanel.removeAll();
        JPanel tempPanel = new JPanel();
        tempPanel.add(this.sealBt);
//        tempPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
//        tempPanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        this.sealBtPanel.setLayout(new GridLayout());
        this.sealBtPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        this.sealBtPanel.setAlignmentY(JPanel.CENTER_ALIGNMENT);
        this.setShowingBtnProper(this.sealBt, this.getBoldFont(), null);
        this.sealBtPanel.add(tempPanel);
//        this.sealBtPanel.add(this.sealBt);
        // 调整组件的可见性
        this.topPanel.setVisible(true);
        this.keyBt.setText(this.keyState);
        this.sealBt.setVisible(true);
        this.sealBtPanel.validate();
    }

    private List<JButton> getDepBt() throws Exception {
        // 为每一个部门创建一个按钮
        Set<String> safes = this.keyUIController.initKeyUI();
        List<JButton> btnList = new LinkedList<>();
        for (String safe : safes) {
            // 设置部门按钮属性
            JButton btn = new JButton(safe);
            this.setShowingBtnProper(btn, null, null);
            btnList.add(btn);
        }
        return btnList;
    }

    private void setShowingBtnProper(JButton btn, Font font, MouseListener listener) {
        btn.setVisible(true);
        Dimension dimension = new Dimension(200, 50);
        btn.setPreferredSize(dimension);
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setVerticalAlignment(SwingConstants.CENTER);
        if (font == null) btn.setFont(this.getNormalFont());
        else btn.setFont(font);
        // 设置点击事件
        if (listener == null) return;
        btn.addMouseListener(listener);
    }

    private Font getNormalFont() {
        return new Font("微软雅黑", Font.PLAIN, 20);
    }

    private Font getBoldFont() {
        return new Font("微软雅黑", Font.BOLD, 20);
    }
}

