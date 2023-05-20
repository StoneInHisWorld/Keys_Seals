package view.GUI;

import view.GUI.safeRelated.MouseListener.KeyBtnMouseListener;
import view.GUI.safeRelated.MouseListener.SealBtnMouseListener;
import view.UI;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class MainFrame extends UI {

    /**
     * 界面组件
     */
    private JPanel topPanel;
    private JLabel welcomeLabel;
    private JLabel warningLabel;
    private JPanel btPanel;
    private JButton keyBtn;
    private JButton sealBtn;
    private JPanel mainFramePanel;
    private JPanel keyBtnPanel;
    private JPanel sealBtnPanel;

    public MainFrame(JFrame frame) throws Exception {
        this.welcome();
        keyBtn.addMouseListener(
                new KeyBtnMouseListener(frame, super.getSupportDep())
        );
        sealBtn.addMouseListener(new SealBtnMouseListener());
    }

    public static void main(String[] args) throws Exception {
        // 设置按钮显示效果
        UIManager.put("OptionPane.buttonFont", new FontUIResource(BasicMethods.getFont(Font.PLAIN, BasicMethods.NORMAL)));
        // 设置文本显示效果
        UIManager.put("OptionPane.messageFont", new FontUIResource(BasicMethods.getFont(Font.PLAIN, BasicMethods.BIG)));
        try {
            JFrame frame = new JFrame();
            MainFrame mainFrame = new MainFrame(frame);
            frame.setContentPane(mainFrame.mainFramePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(mainFrame.getCompanyName() + "钥匙印章管理系统v" + mainFrame.getVersion());
            frame.pack();
            frame.setVisible(true);
            BasicMethods.moveToCenter(frame);
        } catch (Exception e) {
            BasicMethods.dealException(e);
        }
    }

    @Override
    public void welcome() {
        this.welcomeLabel.setText("欢迎来到" + super.companyName + "钥匙印章管理系统！");
        this.warningLabel.setText("注意：" + this.warning);
    }
}
