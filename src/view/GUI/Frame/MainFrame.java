package view.GUI.Frame;

import controller.KeyUIController;
import view.GUI.MouseListener.KeyBtnMouseListener;
import view.UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends UI {

    /**
     * 界面组件
     */
    private JPanel topPanel;
    private JLabel welcomeLabel;
    private JLabel warningLabel;
    private JPanel btPanel;
    private JButton keyBt;
    private JButton sealBt;
    private JPanel mainFramePanel;
    private JPanel keyBtPanel;
    private JPanel sealBtPanel;
    /**
     * 数据生成对象
     */
    private final KeyUIController keyUIController;

    public MainFrame() throws Exception {
        this.welcome();
        keyUIController = new KeyUIController(super.getSupportDep());
        keyBt.addMouseListener(new KeyBtnMouseListener(
                keyBt, sealBt, topPanel, sealBtPanel, this.keyUIController));
    }

    public static void main(String[] args) {
        try {
            JFrame frame = new JFrame("MainFrame");
            MainFrame mainFrame = new MainFrame();
            frame.setContentPane(mainFrame.mainFramePanel);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle(mainFrame.getCompanyName() + "钥匙印章管理系统v" + mainFrame.getVersion());
            frame.pack();
            frame.setVisible(true);
            // 设置框架位置为屏幕中央
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) screenSize.getWidth() / 2 - frame.getWidth() / 2;
            int y = (int) screenSize.getHeight() / 2 - frame.getHeight() / 2;
            frame.setLocation(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void welcome() {
        this.welcomeLabel.setText("欢迎来到" + super.companyName + "钥匙印章管理系统！");
        this.warningLabel.setText("注意：" + this.warning);
    }
}
